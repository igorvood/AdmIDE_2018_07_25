package ru.vood.admplugin.infrastructure.spring.entity;

import java.io.*;

public abstract class ParentForAll implements Serializable {

    public final static String SCHEMA = "VOOD";
    ///protected BigDecimal id;

    public ParentForAll copy() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream ous = null;
        ParentForAll cloneThis = null;
        try {
            ous = new ObjectOutputStream(baos);
            ous.writeObject(this);
            ous.close();
            //сохраняем состояние объекта в поток и закрываем его(поток)
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            //создаём кота для опытов и инициализируем его состояние Васькиным
            cloneThis = (ParentForAll) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ous.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cloneThis;
    }

}
