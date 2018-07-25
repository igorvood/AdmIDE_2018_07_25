package ru.vood.admplugin.dialogs.ExtSwing.treeWhithCheckBox;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NodeSelectionListener extends MouseAdapter {
    JTree tree;

    public NodeSelectionListener(JTree tree) {
        this.tree = tree;
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int row = tree.getRowForLocation(x, y);
        TreePath path = tree.getPathForRow(row);
        if (path != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            CheckNode node = (CheckNode) currentNode.getUserObject();
            boolean isSelected = !(node.isSelected());
            node.setSelected(isSelected);
            DefaultMutableTreeNode nextNode = currentNode.getNextNode();
            while (nextNode != null && currentNode.getLevel() < nextNode.getLevel()) {
                CheckNode nodetmp = ((CheckNode) nextNode.getUserObject());
                nodetmp.setSelected(node.isSelected());
                System.out.println(this.getClass().toString() + " " + nodetmp);
                nextNode = nextNode.getNextNode();
            }
            ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
            // I need revalidate if node is root.  but why?
            if (row == 0) {
                tree.revalidate();
                tree.repaint();
            }
            tree.updateUI();
        }
    }
}
