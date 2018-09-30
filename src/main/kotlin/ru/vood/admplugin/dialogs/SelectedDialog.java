package ru.vood.admplugin.dialogs;

import ru.vood.admplugin.dialogs.ExtSwing.JAddDialog;
import ru.vood.admplugin.dialogs.ExtSwing.JDBTree;
import ru.vood.admplugin.dialogs.ExtSwing.treeWhithCheckBox.CheckNode;
import ru.vood.admplugin.dialogs.ExtSwing.treeWhithCheckBox.CheckRenderer;
import ru.vood.admplugin.dialogs.ExtSwing.treeWhithCheckBox.NodeSelectionListener;
import ru.vood.admplugin.infrastructure.gson.UploadStorage;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectedDialog extends JAddDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTree allTree;
    private JButton saveButton;
    private JButton generateButton;
    private File file;

    public SelectedDialog(File file) {
        this.file = file;
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
        saveButton.addActionListener(new SaveActionListener());
        generateButton.addActionListener(e -> {
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    @Override
    protected void extension() {
        this.setSize(new Dimension(1000, 500));

        if (file != null) {
            generateButton.setEnabled(false);
        }

    }

    private void createUIComponents() {

        allTree = new JDBTree();//JDBTree.getInstance();
        // загрызка из базы
        if (file == null) {
            ((JDBTree) allTree).loadTree(false);
        } else {
            UploadStorage uploadStorage = LoadedCTX.getService(UploadStorage.class);
            ArrayList<VBdObjectEntity> bdObjects = uploadStorage.load(file);
            ((JDBTree) allTree).loadList(bdObjects);


        }
        //allTree.setCellRenderer(new DBTreeCellRenderer());

        allTree.setCellRenderer(new CheckRenderer());
        allTree.addMouseListener(new NodeSelectionListener(allTree));

    }

    private class SaveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            File file;
            JFileChooser fileopen = new JFileChooser();
            fileopen.setDialogType(JFileChooser.SAVE_DIALOG);
            int ret = fileopen.showDialog(null, "Save File");
            if (ret == JFileChooser.APPROVE_OPTION) {
                file = fileopen.getSelectedFile();

                DefaultMutableTreeNode root = (DefaultMutableTreeNode) allTree.getModel().getRoot();
                List<VBdObjectEntity> entityList = new ArrayList<>();
                while (root != null) {
                    CheckNode checkNode = (CheckNode) root.getUserObject();
                    if (checkNode.isSelected()) {
                        entityList.add((VBdObjectEntity) checkNode.getUserObject());
                    }
                    root = root.getNextNode();
                }

                UploadStorage uploadStorage = LoadedCTX.getService(UploadStorage.class);
                uploadStorage.upload(file, entityList);
            }
        }
    }


}
