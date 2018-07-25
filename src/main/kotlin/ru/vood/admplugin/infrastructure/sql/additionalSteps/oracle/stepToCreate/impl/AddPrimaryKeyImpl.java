package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddPrimaryKeySql;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

@Component
public class AddPrimaryKeyImpl implements StepsCreateAndDropServise {

    //    @Autowired
    private PluginTunes tunes;

    //    @Autowired
    private AddPrimaryKeySql primaryKeySql;

    @Autowired
    public AddPrimaryKeyImpl(PluginTunes tunes, AddPrimaryKeySql primaryKeySql) {
        this.tunes = tunes;
        this.primaryKeySql = primaryKeySql;
    }

    public QueryTableNew createDDL(VBdObjectEntity bdObject) {

        if (!(bdObject instanceof VBdTableEntity)) {
            return null;
        }

        QueryTableNew queryTable = null;
        VBdTableEntity bdTable = (VBdTableEntity) bdObject;
        if (bdTable.getTypeObject().equals(ObjectTypes.getTABLE())) {
            queryTable = new QueryTableNew();
            queryTable.add(primaryKeySql.generateUserPK(tunes.getPrefixTable() + bdTable.getCode()));
        }
        return queryTable;

    }

    public StepsCreateAndDropServise getNextStep() {
        return null;
    }
}
