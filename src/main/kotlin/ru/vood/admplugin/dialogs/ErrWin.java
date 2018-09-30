package ru.vood.admplugin.dialogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vood.admplugin.dialogs.ExtSwing.JAddDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ErrWin extends JAddDialog {
    private final static Logger lOG = LoggerFactory.getLogger(JAddDialog.class);

    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton getDetailButton;

    private ErrWin(JDialog parent) {
        super(parent);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        getDetailButton.addActionListener(e -> onGetDetailButton(e));

        //throw new ApplicationErrorException(-1, "");
    }

    public ErrWin(JDialog parent, String title, boolean modal, String err) {
        this(parent, title, modal, err, null);
    }

    public ErrWin(JDialog parent, String title, boolean modal, String err, Exception exept) {
        this(parent);
        this.setModal(modal);
        this.setTitle(title);
        textArea1.setText(err);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuffer messages = new StringBuffer();
        if (exept != null) {
            messages.append(exept.getMessage()).append("\n");
        }


        for (int i = stackTraceElements.length - 1; i > 0; i--) {
            messages.append("CLASS: ").append(stackTraceElements[i].getClassName()).append(" METHOD: ").append(stackTraceElements[i].getMethodName()).append(" LINE: ").append(stackTraceElements[i].getLineNumber()).append("\n");
        }
        textArea2.setText(messages.toString());
        lOG.error(err, exept);
        this.setVisible(true);

    }

    private void onGetDetailButton(ActionEvent e) {
        textArea2.setVisible(true);
    }

    protected void extension() {
        setSize(new Dimension(600, 300));
//        throw new ApplicationErrorException("Не известная ошибка");
    }

    private void onOK() {
// add your code here
        dispose();
    }

}
