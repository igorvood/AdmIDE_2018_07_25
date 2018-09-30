package ru.vood.admplugin.dialogs;

import ru.vood.admplugin.dialogs.ExtSwing.JAddDialog;

import javax.swing.*;
import java.awt.*;

public class MessageWin extends JAddDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textArea1;

    public MessageWin(String textMessage) {
        setContentPane(contentPane);
        setModal(true);
        textArea1.setText(textMessage);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        this.setSize(new Dimension(500, 300));
        this.pack();
        this.setVisible(true);
    }

    public MessageWin(String textMessage, Exception e) {
        this(textMessage + "\n" + e.getMessage());
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    /**
     * Дополнительные действия, запускается при вызове setVisible
     */
    @Override
    protected void extension() {

    }
}
