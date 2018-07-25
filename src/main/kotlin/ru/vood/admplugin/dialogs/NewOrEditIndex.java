package ru.vood.admplugin.dialogs;

import ru.vood.admplugin.dialogs.ExtSwing.JAddDialog;
import ru.vood.admplugin.dialogs.ExtSwing.JDBTableIndexColomnsModel;
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

    private List<VBdObjectEntity> indexedColomnsEntities;

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

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        MouseAdapter tableMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable obj = (JTable) e.getSource();
                    JDBTableIndexColomnsModel tableModel = (JDBTableIndexColomnsModel) obj.getModel();
                    VBdColumnsEntity clickedColomnsEntity = tableModel.getSelectedTypeObject(obj.getSelectedRow());
                    if (clickedColomnsEntity != null) {
                        if (ExcludeTable.equals(obj)) {
                            ((JDBTableIndexColomnsModel) includeTable.getModel()).addColomn(clickedColomnsEntity);
                            ((JDBTableIndexColomnsModel) ExcludeTable.getModel()).deleteColomn(clickedColomnsEntity);
                        } else {
                            ((JDBTableIndexColomnsModel) includeTable.getModel()).deleteColomn(clickedColomnsEntity);
                            ((JDBTableIndexColomnsModel) ExcludeTable.getModel()).addColomn(clickedColomnsEntity);
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
        List<VBdObjectEntity> listColomn = ((JDBTableIndexColomnsModel) includeTable.getModel()).getRows();
        String nameIdx = listColomn.stream()
                .map(q -> q.getCode())
                .reduce((s1, s2) -> s1 + "_" + s2).orElse(" ");
        indexEntity.setName(INDEX_PREFIX + parentObject.getCode() + "_" + nameIdx);
        indexEntity.setCode(INDEX_PREFIX + parentObject.getCode() + "_" + nameIdx);
        indexEntity.setGlobalI(false);
        indexEntity.setUniqueI(false);
        indexEntity.setColomnsEntities(null);
        for (VBdObjectEntity ic : listColomn) {
            indexEntity.addColomn((VBdColumnsEntity) ic);
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
        List<VBdObjectEntity> vBdObjectEntityList = service.findByParentAndTypeObject(parentObject, ObjectTypes.getCOLOMN());
        if (indexEntity != null) {
            VBdIndexedColumnsEntityService indexedColomnsEntityService = LoadedCTX.getService(VBdIndexedColumnsEntityService.class);
            indexedColomnsEntities = indexedColomnsEntityService.findByCollectionId(indexEntity.getColumns())
                    .stream().map(q -> q.getColomnRef())
                    .collect(Collectors.toList());
        } else {
            indexedColomnsEntities = new ArrayList<>();
        }
        List<VBdObjectEntity> vBdColomnListNotInIndex = vBdObjectEntityList.stream()
                .filter(colom -> !indexedColomnsEntities.contains(colom))
                .collect(Collectors.toList());
        JDBTableIndexColomnsModel excludeTableModel = new JDBTableIndexColomnsModel((ArrayList<VBdObjectEntity>) vBdColomnListNotInIndex, "Не включены в индекс");
        JDBTableIndexColomnsModel includeTableModel = new JDBTableIndexColomnsModel((ArrayList<VBdObjectEntity>) indexedColomnsEntities, "В индексе");
        ExcludeTable = new JTable(excludeTableModel);
        includeTable = new JTable(includeTableModel);

        idxPrefixLabel = new JLabel();
        idxPrefixLabel.setText(INDEX_PREFIX);
    }
}
