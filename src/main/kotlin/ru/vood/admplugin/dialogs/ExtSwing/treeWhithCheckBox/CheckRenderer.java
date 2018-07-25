package ru.vood.admplugin.dialogs.ExtSwing.treeWhithCheckBox;


import ru.vood.admplugin.dialogs.ExtSwing.TreeIcons;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class CheckRenderer extends JPanel implements TreeCellRenderer {
    protected JCheckBox check;

    protected TreeLabel label;

    public CheckRenderer() {
        setLayout(null);
        add(check = new JCheckBox());
        add(label = new TreeLabel());
        check.setBackground(UIManager.getColor("Tree.textBackground"));
        label.setForeground(UIManager.getColor("Tree.textForeground"));
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean isSelected, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        String stringValue = tree.convertValueToText(value, isSelected,
                expanded, leaf, row, hasFocus);
        setEnabled(tree.isEnabled());

        //check.setSelected(((CheckNode)  value).isSelected());
        check.setSelected(((CheckNode) ((DefaultMutableTreeNode) value).getUserObject()).isSelected());
        label.setFont(tree.getFont());
        label.setText(stringValue);
        label.setSelected(isSelected);
        label.setFocus(hasFocus);

        DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) value;

        if (treenode.getUserObject() instanceof CheckNode) {
            CheckNode ch = (CheckNode) treenode.getUserObject();
            if (ch.getUserObject() instanceof VBdObjectEntity) {
                VBdObjectEntity ob = (VBdObjectEntity) ch.getUserObject();
                label.setIcon(TreeIcons.getIconByType(ob));
            }
        }


        /*if (leaf) {
            label.setIcon(UIManager.getIcon("Tree.leafIcon"));
        } else if (expanded) {
            label.setIcon(UIManager.getIcon("Tree.openIcon"));
        } else {
            label.setIcon(UIManager.getIcon("Tree.closedIcon"));
        }*/

        return this;
    }

    public Dimension getPreferredSize() {
        Dimension d_check = check.getPreferredSize();
        Dimension d_label = label.getPreferredSize();
        return new Dimension(d_check.width + d_label.width,
                (d_check.height < d_label.height ? d_label.height
                        : d_check.height) + 10);
    }

    public void doLayout() {
        Dimension d_check = check.getPreferredSize();
        Dimension d_label = label.getPreferredSize();
        int y_check = 0;
        int y_label = 0;
        if (d_check.height < d_label.height) {
            y_check = (d_label.height - d_check.height) / 2;
        } else {
            y_label = (d_check.height - d_label.height) / 2;
        }
        check.setLocation(0, y_check);
        check.setBounds(0, y_check, d_check.width, d_check.height);
        label.setLocation(d_check.width, y_label);
        label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
    }

    public void setBackground(Color color) {
        if (color instanceof ColorUIResource)
            color = null;
        super.setBackground(color);
    }


}
