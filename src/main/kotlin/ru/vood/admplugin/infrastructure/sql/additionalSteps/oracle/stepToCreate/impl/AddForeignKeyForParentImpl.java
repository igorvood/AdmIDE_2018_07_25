package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.spring.referenceBook.RootObjects;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddConstraintSql;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

@Component
public class AddForeignKeyForParentImpl implements StepsCreateAndDropServise {

    //    @Autowired
//    @Qualifier("addArrayImpl")
    private StepsCreateAndDropServise nextStep;

    //    @Autowired
    private PluginTunes pluginTunes;

    //    @Autowired
    private AddConstraintSql constraintSql;

    @Autowired
    public AddForeignKeyForParentImpl(@Qualifier("addArrayImpl") StepsCreateAndDropServise nextStep
            , PluginTunes pluginTunes
            , AddConstraintSql constraintSql) {
        this.nextStep = nextStep;
        this.pluginTunes = pluginTunes;
        this.constraintSql = constraintSql;
    }

    public QueryTableNew createDDL(VBdObjectEntity bdObject) {

        if (!(bdObject instanceof VBdTableEntity)) {
            return null;
        }
        QueryTableNew queryTable = new QueryTableNew();

        VBdTableEntity bdTable = (VBdTableEntity) bdObject;
        if (bdTable.getParent() != null && !bdTable.getParent().equals(RootObjects.getTABLE()) && bdTable.getTypeObject().equals(ObjectTypes.getTABLE())) {
            String pref = pluginTunes.getPrefixTable();
            String forKey = constraintSql.getSql(pref + bdTable.getCode(), "ID", pref + bdTable.getParent().getCode(), "ID");
            queryTable.add(forKey);
        }

        return queryTable;

    }

    public StepsCreateAndDropServise getNextStep() {
        return nextStep;
    }
}
