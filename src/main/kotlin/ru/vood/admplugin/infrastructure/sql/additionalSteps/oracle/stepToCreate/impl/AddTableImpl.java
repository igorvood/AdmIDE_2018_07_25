package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;


@Component
public class AddTableImpl implements StepsCreateAndDropServise {

    @Autowired
    private AddPrimaryKeyImpl primaryKey;

    @Autowired
    private AddForeignKeyForParentImpl foreignKeyForParent;

    @Autowired
    @Qualifier("addColumnImpl")
    private StepsCreateAndDropServise nextStep;

    @Autowired
    private PluginTunes tunes;

/*
    @Autowired
    public AddTableImpl(AddPrimaryKeyImpl primaryKey
            , AddForeignKeyForParentImpl foreignKeyForParent
            , @Qualifier("addColumnImpl") StepsCreateAndDropServise nextStep
            , PluginTunes tunes) {
        this.primaryKey = primaryKey;
        this.foreignKeyForParent = foreignKeyForParent;
        this.nextStep = nextStep;
        this.tunes = tunes;
    }
*/

    @Override
    public QueryTableNew createDDL(VBdObjectEntity bdObject) {
        if (!(bdObject instanceof VBdTableEntity)) {
            return null;
        }

        QueryTableNew queryTable = new QueryTableNew();

        VBdTableEntity bdTable = (VBdTableEntity) bdObject;
        if (bdTable.getTypeObject().equals(ObjectTypes.getTABLE())) {
            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append("-- Create table\n");
            stringBuffer.append("create table ").append(tunes.getOwner()).append(".").append(tunes.getPrefixTable()).append(bdTable.getCode()).append("\n");
            stringBuffer.append("(id NUMBER not null) ");
            stringBuffer.append(" tablespace ").append(tunes.getTableSpaceUserTable()).append("\n ");
            stringBuffer.append(tunes.getStorageTable()).append("\n");
            queryTable.add(stringBuffer.toString());

            //Добавление первичного ключа
            queryTable.addAll(primaryKey.createDDL(bdTable));

            //если добавляем таблицу с настоящим родителем, то надо их связать внешним ключем
            if (bdTable.getParent() instanceof VBdTableEntity) {
                queryTable.addAll(foreignKeyForParent.createDDL(bdTable));
            }
        }

        return queryTable;
    }

    @Override
    public StepsCreateAndDropServise getNextStep() {
        return nextStep;
    }

}
