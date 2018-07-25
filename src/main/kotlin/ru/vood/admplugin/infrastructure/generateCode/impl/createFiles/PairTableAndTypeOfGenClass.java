package ru.vood.admplugin.infrastructure.generateCode.impl.createFiles;

import javafx.util.Pair;
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;

public class PairTableAndTypeOfGenClass extends Pair<VBdTableEntity, TypeOfGenClass> {
    /**
     * Creates a new pair
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public PairTableAndTypeOfGenClass(VBdTableEntity key, TypeOfGenClass value) {
        super(key, value);
    }
}
