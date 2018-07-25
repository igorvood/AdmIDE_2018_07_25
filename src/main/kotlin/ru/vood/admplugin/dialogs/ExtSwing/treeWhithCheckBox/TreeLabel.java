package ru.vood.admplugin.dialogs.ExtSwing.treeWhithCheckBox;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class TreeLabel extends JLabel {
    private boolean isSelected;

    private boolean hasFocus;

    public TreeLabel() {
    }

    public void setBackground(Color color) {
        if (color instanceof ColorUIResource)
            color = null;
        super.setBackground(color);
    }

    public void paint(Graphics g) {
        String str;
        if ((str = getText()) != null) {
            if (0 < str.length()) {
                if (isSelected) {
                    g.setColor(UIManager
                            .getColor("Tree.selectionBackground"));
                } else {
                    g.setColor(UIManager.getColor("Tree.textBackground"));
                }
                Dimension d = getPreferredSize();
                int imageOffset = 0;
                Icon currentI = getIcon();
                if (currentI != null) {
                    imageOffset = currentI.getIconWidth()
                            + Math.max(0, getIconTextGap() - 1);
                }
                g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
                        d.height);
                if (hasFocus) {
                    g.setColor(UIManager
                            .getColor("Tree.selectionBorderColor"));
                    g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
                            d.height - 1);
                }
            }
        }
        super.paint(g);
    }

    public Dimension getPreferredSize() {
        Dimension retDimension = super.getPreferredSize();
        if (retDimension != null) {
            retDimension = new Dimension(retDimension.width + 3,
                    retDimension.height);
        }
        return retDimension;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setFocus(boolean hasFocus) {
        this.hasFocus = hasFocus;
    }
}