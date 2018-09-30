package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.TuneChainStepsCreateServise;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddConstraintSql;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

@Component
public class AddColumnImpl implements StepsCreateAndDropServise {

    @Autowired
    @Qualifier("addIndexImpl")
    private StepsCreateAndDropServise nextStep;

    @Autowired
    private PluginTunes tunes;

    @Autowired
    private AddConstraintSql constraintSql;

    @Autowired
    private TuneChainStepsCreateServise stepsCreate;

/*
    @Autowired
    public AddColumnImpl(@Qualifier("addIndexImpl") StepsCreateAndDropServise nextStep
            , PluginTunes tunes
            , AddConstraintSql constraintSql
            , TuneChainStepsCreateServise stepsCreate) {
        this.nextStep = nextStep;
        this.tunes = tunes;
        this.constraintSql = constraintSql;
        this.stepsCreate = stepsCreate;
    }
*/

    @Override
    public QueryTableNew createDDL(VBdObjectEntity bdObject) {
        QueryTableNew queryTable = new QueryTableNew();
        if (!(bdObject instanceof VBdColumnsEntity)) {
            return queryTable;
        }

        VBdColumnsEntity bdColumns = (VBdColumnsEntity) bdObject;
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("ALTER TABLE ").append(tunes.getPrefixTable()).append(bdColumns.getParent().getCode()).append("\n");
        stringBuffer.append("ADD ").append(bdColumns.getCode());

        VBdTableEntity vBdTableEntity = (VBdTableEntity) bdColumns.getTypeValue();

        if (bdColumns.getTypeValue().getTypeObject().equals(ObjectTypes.getSTRING())) {
            String length = vBdTableEntity.getLength() == null ? " " : "(" + vBdTableEntity.getLength() + ")";
            stringBuffer.append(" VARCHAR2").append(length).append(" ").append((bdColumns.getNotNull()) ? " not null" : " ");
        } else if (bdColumns.getTypeValue().getTypeObject().equals(ObjectTypes.getNUMBER())) {
            Long len = vBdTableEntity.getLength();
            Long pres = vBdTableEntity.getPrecision();
            String paramNum = "";
            if (len == null && pres == null) {
                stringBuffer.append(" NUMBER" + " ").append((bdColumns.getNotNull()) ? "not null" : "");
            } else {
                if (len > 0 && pres > 0) {
                    paramNum = "(" + len + "," + pres + ")";
                } else if (len > 0) {
                    paramNum = "(" + len + ")";
                }
                stringBuffer.append(" NUMBER").append(paramNum).append(" ").append((bdColumns.getNotNull()) ? "not null" : "");
            }
        } else if (bdColumns.getTypeValue().getTypeObject().equals(ObjectTypes.getDATE())) {
            stringBuffer.append(" DATE ").append((bdColumns.getNotNull()) ? "not null" : "");
        } else if (bdColumns.getTypeValue().getTypeObject().equals(ObjectTypes.getREFERENCE())) {
            stringBuffer.append(" NUMBER ").append((bdColumns.getNotNull()) ? "not null" : "");
            queryTable.add(stringBuffer.toString());
            String pref = tunes.getPrefixTable();
            stringBuffer = new StringBuffer(constraintSql.getSql(pref + (bdColumns).getParent().getCode(), (bdColumns).getCode(), pref + vBdTableEntity.getToType().getCode(), "ID"));
        } else if (bdColumns.getTypeValue().getTypeObject().equals(ObjectTypes.getARRAY())) {
            //Если работа идет с массивом то сначала добавить колонку, потом заполнить ее значениями, и поптом ее сделать не пустой.
            stringBuffer.append(" NUMBER ");
            queryTable.add(stringBuffer.toString());
            stepsCreate.runQueryes(queryTable);
            stringBuffer = new StringBuffer();
            stringBuffer.append(" UPDATE  ").append(tunes.getOwner()).append(".").append(tunes.getPrefixTable()).append(bdColumns.getParent().getCode()).append("\n");
            stringBuffer.append(" SET ").append(bdColumns.getCode()).append(" = SEQ_ID.nextval  ");

            queryTable = new QueryTableNew();
            queryTable.add(stringBuffer.toString());
            queryTable.add("commit ");

            stringBuffer = new StringBuffer();

            stepsCreate.runQueryes(queryTable);
        }
        if (!bdColumns.getTypeValue().getTypeObject().equals(ObjectTypes.getARRAY())) {
            queryTable.add(stringBuffer.toString());
        }
        return queryTable;

    }

    @Override
    public StepsCreateAndDropServise getNextStep() {
        return nextStep;
    }
}
