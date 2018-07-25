package ru.vood.admplugin.dialogs.ExtSwing;

import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class DBTreeCellRenderer extends DefaultTreeCellRenderer {
    /**
     *
     */


    private static final long serialVersionUID = 707287337017494764L;

    public DBTreeCellRenderer() {
        super();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean isSelected, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, isSelected, expanded,
                leaf, row, hasFocus);
        setForeground(getTextSelectionColor());
        DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) value;

        //if (leaf) {
        if (treenode.getUserObject() instanceof VBdObjectEntity) {
            VBdObjectEntity ob = (VBdObjectEntity) treenode.getUserObject();
            setIcon(TreeIcons.getIconByType(ob));
        }
        return this;
    }

    /**
     * Returns the icon used to represent leaf nodes.
     */
   /* @Override
    public Icon getLeafIcon() {
        this.
        return super.getLeafIcon();
    }*/
}