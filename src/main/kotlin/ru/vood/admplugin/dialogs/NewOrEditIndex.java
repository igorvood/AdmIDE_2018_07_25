package ru.vood.admplugin.dialogs;

import ru.vood.admplugin.dialogs.ExtSwing.JAddDialog;
import ru.vood.admplugin.dialogs.ExtSwing.JDBTableIndexColumnsModel;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption;
import ru.vood.admplugin.infrastructure.spring.intf.VBdIndexEntityService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdIndexedColumnsEntityService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.vood.admplugin.infrastructure.applicationConst.Const.INDEX_PREFIX;


public class NewOrEditIndex extends JAddDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField indexField;
    private JTable ExcludeTable;
    private JTable includeTable;
    private JLabel idxPrefixLabel;

    private VBdObjectEntity parentObject;
    private VBdIndexEntity indexEntity;

    private List<VBdObjectEntity> indexedColumnsEntities;

    public NewOrEditIndex(VBdIndexEntity indexEntity, VBdObjectEntity parent) {

        this.parentObject = parent;
        this.indexEntity = indexEntity;
        if (this.indexEntity == null) {
            this.setTitle("Создание индекса");
        } else {
            this.setTitle("Редактирование индекса");
        }


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        MouseAdapter tableMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable obj = (JTable) e.getSource();
                    JDBTableIndexColumnsModel tableModel = (JDBTableIndexColumnsModel) obj.getModel();
                    VBdColumnsEntity clickedColumnsEntity = tableModel.getSelectedTypeObject(obj.getSelectedRow());
                    if (clickedColumnsEntity != null) {
                        if (ExcludeTable.equals(obj)) {
                            ((JDBTableIndexColumnsModel) includeTable.getModel()).addColumn(clickedColumnsEntity);
                            ((JDBTableIndexColumnsModel) ExcludeTable.getModel()).deleteColumn(clickedColumnsEntity);
                        } else {
                            ((JDBTableIndexColumnsModel) includeTable.getModel()).deleteColumn(clickedColumnsEntity);
                            ((JDBTableIndexColumnsModel) ExcludeTable.getModel()).addColumn(clickedColumnsEntity);
                        }
                        includeTable.updateUI();
                        ExcludeTable.updateUI();
                    }
                }
                super.mouseClicked(e);
            }
        };
        ExcludeTable.addMouseListener(tableMouseAdapter);
        includeTable.addMouseListener(tableMouseAdapter);
    }

    private void onOK() {
        if (indexEntity == null) {
            indexEntity = new VBdIndexEntity();
            indexEntity.setParent(parentObject);
            indexEntity.setJavaClass(indexEntity.getClass().toString());
            indexEntity.setTypeObject(ObjectTypes.getINDEX());
        }
        List<VBdObjectEntity> listColumn = ((JDBTableIndexColumnsModel) includeTable.getModel()).getRows();
        String nameIdx = listColumn.stream()
                .map(q -> q.getCode())
                .reduce((s1, s2) -> s1 + "_" + s2).orElse(" ");
        indexEntity.setName(INDEX_PREFIX + parentObject.getCode() + "_" + nameIdx);
        indexEntity.setCode(INDEX_PREFIX + parentObject.getCode() + "_" + nameIdx);
        indexEntity.setGlobalI(false);
        indexEntity.setUniqueI(false);
        indexEntity.setColumnsEntities(null);
        for (VBdObjectEntity ic : listColumn) {
            indexEntity.addColumn((VBdColumnsEntity) ic);
        }
        VBdIndexEntityService indexEntityService = LoadedCTX.getService(VBdIndexEntityService.class);
        try {
            indexEntityService.findByCode(indexEntity.getCode());
            new MessageWin("Такой индекс уже существует");
        } catch (CoreExeption e) {
            indexEntity = indexEntityService.save(indexEntity);
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Дополнительные действия, запускается при вызове setVisible
     */
    @Override
    protected void extension() {
        //VBdTableEntityRepository bdTableEntityRepository= LoadedCTX.getService(VBdTableEntityRepository.class);
        //bdTableEntityRepository.

        this.setSize(new Dimension(1000, 500));
    }

    private void createUIComponents() {
        VBdObjectEntityService service = LoadedCTX.getService(VBdObjectEntityService.class);
        List<VBdObjectEntity> vBdObjectEntityList = service.findByParentAndTypeObject(parentObject, ObjectTypes.getCOLUMN());
        if (indexEntity != null) {
            VBdIndexedColumnsEntityService indexedColumnsEntityService = LoadedCTX.getService(VBdIndexedColumnsEntityService.class);
            indexedColumnsEntities = indexedColumnsEntityService.findByCollectionId(indexEntity.getColumns())
                    .stream().map(q -> q.getColumnRef())
                    .collect(Collectors.toList());
        } else {
            indexedColumnsEntities = new ArrayList<>();
        }
        List<VBdObjectEntity> vBdColumnListNotInIndex = vBdObjectEntityList.stream()
                .filter(colom -> !indexedColumnsEntities.contains(colom))
                .collect(Collectors.toList());
        JDBTableIndexColumnsModel excludeTableModel = new JDBTableIndexColumnsModel((ArrayList<VBdObjectEntity>) vBdColumnListNotInIndex, "Не включены в индекс");
        JDBTableIndexColumnsModel includeTableModel = new JDBTableIndexColumnsModel((ArrayList<VBdObjectEntity>) indexedColumnsEntities, "В индексе");
        ExcludeTable = new JTable(excludeTableModel);
        includeTable = new JTable(includeTableModel);

        idxPrefixLabel = new JLabel();
        idxPrefixLabel.setText(INDEX_PREFIX);
    }
}
