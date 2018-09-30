package ru.vood.admplugin.dialogs;

import ru.vood.admplugin.dialogs.ExtSwing.DigitIntFilter;
import ru.vood.admplugin.dialogs.ExtSwing.EnglishFilter;
import ru.vood.admplugin.dialogs.ExtSwing.JAddDialog;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdTableEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.spring.referenceBook.RootObjects;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NewOrEditString extends JAddDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField lengthField;
    private VBdObjectEntity object;

    public NewOrEditString(VBdObjectEntity object) {
        this.object = object;
        if (this.object == null) {
            this.setTitle("Создание строки");
        } else {
            this.setTitle("Редактирование строки");
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
        if (checkText(codeField) && checkText(nameField) && checkText(lengthField)) {
            VBdObjectEntity num = RootObjects.getSTRING();

            VBdTableEntity newBDTable = new VBdTableEntity();
            newBDTable.setParent(num);
            newBDTable.setTypeObject(ObjectTypes.getSTRING());
            newBDTable.setCode(this.codeField.getText().toUpperCase().trim());
            newBDTable.setName(this.nameField.getText().trim());
            newBDTable.setJavaClass(newBDTable.getClass().toString());
            newBDTable.setLength(Long.getLong(lengthField.getText()));

            VBdTableEntityService tableEntityService = LoadedCTX.getService(VBdTableEntityService.class);
            VBdTableEntity newNum = tableEntityService.save(newBDTable);

            this.setAddedObj(newNum);
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private boolean check() {
        boolean fl = true;
        fl = fl && this.codeField.getText().length() == 0;
        fl = fl && this.nameField.getText().length() == 0;
        fl = fl && this.lengthField.getText().length() == 0;

        return fl;
    }

    /**
     * Дополнительные действия, запускается при вызове setVisible
     */
    @Override
    protected void extension() {
        PlainDocument doc = (PlainDocument) lengthField.getDocument();
        doc.setDocumentFilter(new DigitIntFilter());

        PlainDocument docENG = (PlainDocument) codeField.getDocument();
        docENG.setDocumentFilter(new EnglishFilter());


    }
}
