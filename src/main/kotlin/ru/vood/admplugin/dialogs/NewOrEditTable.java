package ru.vood.admplugin.dialogs;

import ru.vood.admplugin.dialogs.ExtSwing.EnglishFilter;
import ru.vood.admplugin.dialogs.ExtSwing.JAddDialog;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectTypeEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdTableEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NewOrEditTable extends JAddDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField parentField;
    private VBdObjectEntity object;
    private VBdObjectEntity parent;
    private boolean isEdit;

    public NewOrEditTable(VBdObjectEntity object, VBdObjectEntity parent) {
        this.object = object;
        this.parent = parent;
        isEdit = this.object != null;
        if (isEdit) {
            this.setTitle("Создание таблицы");
        } else {
            this.setTitle("Редактирование таблицы");
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
    }

    private void onOK() {
        if (checkText(codeField) && checkText(nameField)) {
            PluginTunes pluginTunes = LoadedCTX.getService(PluginTunes.class);
            VBdTableEntity newBDTable = isEdit ? (VBdTableEntity) object : new VBdTableEntity();
            VBdObjectTypeEntity parentObject =
                    this.parent.getTypeObject().equals(ObjectTypes.getOBJECT()) ? ObjectTypes.getTABLE() : this.parent.getTypeObject();


            newBDTable.setParent(this.parent);
            newBDTable.setTypeObject(parentObject);
            newBDTable.setCode(this.codeField.getText().toUpperCase().trim());
            newBDTable.setName(this.nameField.getText().trim());
            newBDTable.setJavaClass(newBDTable.getClass().toString());

            newBDTable.setTableSpace(pluginTunes.getTableSpaceUserTable());
            newBDTable.setStorage(pluginTunes.getStorageTable());
            VBdTableEntityService tableEntityService = LoadedCTX.getService(VBdTableEntityService.class);
            tableEntityService.save(newBDTable);
            this.setAddedObj(newBDTable);
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        NewOrEditTable dialog = new NewOrEditTable();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/

    @Override
    protected void extension() {
        PlainDocument doc = (PlainDocument) codeField.getDocument();
        doc.setDocumentFilter(new EnglishFilter());
        parentField.setText(parent.getName());
        if (isEdit) {
            this.codeField.setText(object.getCode());
            this.nameField.setText(object.getName());
        }

    }

}
