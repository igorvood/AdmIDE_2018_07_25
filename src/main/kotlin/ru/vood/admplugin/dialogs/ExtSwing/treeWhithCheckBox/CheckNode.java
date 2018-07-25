package ru.vood.admplugin.dialogs.ExtSwing.treeWhithCheckBox;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

public class CheckNode extends DefaultMutableTreeNode {

    public final static int SINGLE_SELECTION = 0;

    public final static int DIG_IN_SELECTION = 4;

    protected int selectionMode;

    private boolean isSelected;

    public CheckNode() {
        this(null);
    }

    public CheckNode(Object userObject) {
        this(userObject, true, false);
    }

    public CheckNode(Object userObject, boolean allowsChildren,
                     boolean isSelected) {
        super(userObject, allowsChildren);
        this.isSelected = isSelected;
        setSelectionMode(DIG_IN_SELECTION);
    }

    public int getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(int mode) {
        selectionMode = mode;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;

        if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
            Enumeration e = children.elements();
            while (e.hasMoreElements()) {
                CheckNode node = (CheckNode) e.nextElement();
                node.setSelected(isSelected);
            }
        }
    }

    // If you want to change "isSelected" by CellEditor,
  /*
   public void setUserObject(Object obj) { if (obj instanceof Boolean) {
   * setSelected(((Boolean)obj).booleanValue()); } else {
   * super.setUserObject(obj); } }
   */
}
