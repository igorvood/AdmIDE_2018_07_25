package ru.vood.admplugin.dialogs.ExtSwing;


import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;

import javax.swing.*;

public class TreeIcons {

    public static ImageIcon getIconByType(VBdObjectEntity entity) {
        ImageIcon icon = null;
        String userDir = System.getProperty("user.dir");
        if (entity.getTypeObject().equals(ObjectTypes.getTABLE()) && !entity.getCode().equals("OBJECT")) {
            icon = new ImageIcon(userDir + "\\src\\main\\resources\\images\\tree\\_Table16.png");
        } else if (entity.getTypeObject().equals(ObjectTypes.getREFERENCE())) {
            icon = new ImageIcon(userDir + "\\src\\main\\resources\\images\\tree\\_reference16.png");
        } else if (entity.getTypeObject().equals(ObjectTypes.getDATE())) {
            icon = new ImageIcon(userDir + "\\src\\main\\resources\\images\\tree\\_Date16.png");
        } else if (entity.getTypeObject().equals(ObjectTypes.getARRAY())) {
            icon = new ImageIcon(userDir + "\\src\\main\\resources\\images\\tree\\array16.png");
        } else if (entity.getTypeObject().equals(ObjectTypes.getNUMBER())) {
            icon = new ImageIcon(userDir + "\\src\\main\\resources\\images\\tree\\_number16.png");
        } else if (entity.getTypeObject().equals(ObjectTypes.getSTRING())) {
            icon = new ImageIcon(userDir + "\\src\\main\\resources\\images\\tree\\_Text16.png");
        } else if (entity.getTypeObject().equals(ObjectTypes.getBOOLEAN())) {
            icon = new ImageIcon(userDir + "\\src\\main\\resources\\images\\tree\\_boolean16.png");
        } else if (entity.getTypeObject().equals(ObjectTypes.getINDEX())) {
            icon = new ImageIcon(userDir + "\\src\\main\\resources\\images\\tree\\_index16.png");
        } else if (entity.getTypeObject().equals(ObjectTypes.getCOLUMN())) {
            icon = new ImageIcon(userDir + "\\src\\main\\resources\\images\\tree\\_colomn16.png");
        }
        return icon;
    }
}
