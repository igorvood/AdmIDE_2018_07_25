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
            stringBuffer.append("create table " + tunes.getOwner() + "." + tunes.getPrefixTable() + bdTable.getCode() + "\n");
            stringBuffer.append("(id NUMBER not null) ");
            stringBuffer.append(" tablespace " + tunes.getTableSpaceUserTable() + "\n ");
            stringBuffer.append(tunes.getStorageTable() + "\n");
            queryTable.add(stringBuffer.toString());

            //Добавление первичного ключа
            queryTable.addAll(primaryKey.createDDL(bdTable));

            // Автоматически добавить ID в колонки
//            VBdColumnsEntity colomnsEntity = new VBdColumnsEntity();
//            colomnsEntity.setParent(bdObject);
//            colomnsEntity.setCode("ID");
//            colomnsEntity.setName("ID");
//            colomnsEntity.setNotNull("1");
//            colomnsEntity.setTypeColomn(ObjectTypes.getNUMBER());
//            colomnsEntity.setTypeValue(Tables.getAny("NUM"));
//            colomnsEntity = columnsEntityService.save(colomnsEntity);


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
