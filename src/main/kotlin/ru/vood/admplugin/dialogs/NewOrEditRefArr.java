package ru.vood.admplugin.dialogs;

import ru.vood.admplugin.dialogs.ExtSwing.DBTreeCellRenderer;
import ru.vood.admplugin.dialogs.ExtSwing.EnglishFilter;
import ru.vood.admplugin.dialogs.ExtSwing.JAddDialog;
import ru.vood.admplugin.dialogs.ExtSwing.JDBTree;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectTypeEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.spring.referenceBook.RootObjects;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NewOrEditRefArr extends JAddDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTree tree1;

    private JTextField codeField;
    private JTextField nameField;
    private VBdObjectEntity object;
    private boolean isRef;

    public NewOrEditRefArr(VBdObjectEntity object, boolean isRef) {
        this.object = object;
        this.isRef = isRef;

        if (isRef) {
            if (this.object == null) {
                this.setTitle("Создание ссылки");
            } else {
                this.setTitle("Редактирование ссылки");
            }
        } else {
            if (this.object == null) {
                this.setTitle("Создание массива");
            } else {
                this.setTitle("Редактирование массива");
            }
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

        tree1.addTreeSelectionListener(e -> {
            if (tree1.getLastSelectedPathComponent() != null) {
                VBdObjectEntity bdTable = ((VBdObjectEntity) ((DefaultMutableTreeNode) tree1.getLastSelectedPathComponent()).getUserObject());
                if (bdTable.getTypeObject().getCode().equals("TABLE")) {
                    String postfix;
                    if (isRef) {
                        postfix = "REF";
                        nameField.setText("Ссылна на <" + bdTable.getName() + ">");
                    } else {
                        postfix = "ARR";
                        nameField.setText("Массив <" + bdTable.getName() + ">");
                    }
                    codeField.setText(bdTable.getCode() + postfix);
                }
            }
        });
    }

    private void onOK() {
        if (tree1.getLastSelectedPathComponent() == null) {
            new ErrWin(this, "Не выбран тип.", true, "Не выбран тип.", null);
        } else {
            VBdObjectEntity bdTable = ((VBdObjectEntity) ((DefaultMutableTreeNode) tree1.getLastSelectedPathComponent()).getUserObject());
            VBdObjectEntity parent;

            if (bdTable.getTypeObject().equals(ObjectTypes.getTABLE())) {
                VBdTableEntity table = new VBdTableEntity();
                table.setCode(codeField.getText().toUpperCase());
                table.setName(nameField.getText());
                VBdObjectTypeEntity bdObjType;
                if (isRef) {
                    bdObjType = ObjectTypes.getREFERENCE();
                    parent = RootObjects.getREFERENCE();
                } else {
                    bdObjType = ObjectTypes.getARRAY();
                    parent = RootObjects.getARRAY();
                }
                table.setTypeObject(bdObjType);
                table.setJavaClass(table.getClass().toString());
                table.setParent(parent);
                table.setToType((VBdTableEntity) bdTable);

                VBdObjectEntityService columnsEntityService = LoadedCTX.getService(VBdObjectEntityService.class);
                try {
                    VBdTableEntity newTableEntity = (VBdTableEntity) columnsEntityService.save(table);
                    this.setAddedObj(newTableEntity);
                    dispose();
                } catch (Exception e) {
                    new MessageWin("Объект " + bdTable + " не удалось сохранить.", e);
                }


            } else {
                new MessageWin("Тип объекта " + bdTable.getTypeObject().getName() + " не предполагает создание ссылки или массива.");
            }
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        tree1 = new JDBTree();//JDBTree.getInstance();
        ((JDBTree) tree1).loadTree(true);
        tree1.setCellRenderer(new DBTreeCellRenderer());
    }

    @Override
    protected void extension() {
        PlainDocument doc = (PlainDocument) codeField.getDocument();
        doc.setDocumentFilter(new EnglishFilter());

        this.setSize(new Dimension(500, 500));
    }

}
