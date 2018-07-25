package ru.vood.admplugin.infrastructure.gson.intf;

import ru.vood.admplugin.infrastructure.spring.entity.ParentForAll;

import java.io.File;
import java.util.ArrayList;

public interface UpLoad<E extends ParentForAll> {

    boolean upLoadTo(File file, ArrayList<E> arrayList);

}
