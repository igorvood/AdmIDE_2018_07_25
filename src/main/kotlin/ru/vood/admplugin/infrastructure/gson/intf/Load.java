package ru.vood.admplugin.infrastructure.gson.intf;

import ru.vood.admplugin.infrastructure.spring.entity.ParentForAll;

import java.io.File;
import java.util.ArrayList;

public interface Load<E extends ParentForAll> {

    ArrayList<E> loadFrom(File file);

}
