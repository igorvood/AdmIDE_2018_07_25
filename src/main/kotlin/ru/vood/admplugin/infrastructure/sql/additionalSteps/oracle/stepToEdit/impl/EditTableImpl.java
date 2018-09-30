package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit.abstr.StepsEditServise;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

@Component
public class EditTableImpl implements StepsEditServise {


    //    @Autowired
//    @Qualifier("editColumnImpl")
    private StepsEditServise nextStep;

    //    @Autowired
    private PluginTunes tunes;

    @Autowired
    public EditTableImpl(@Qualifier("editColumnImpl") StepsEditServise nextStep
            , PluginTunes tunes) {
        this.nextStep = nextStep;
        this.tunes = tunes;
    }

    @Override
    public QueryTableNew editDDL(VBdObjectEntity bdObjectOld, VBdObjectEntity bdObjectNew) {

        if (!(bdObjectOld instanceof VBdTableEntity) || !(bdObjectNew instanceof VBdTableEntity)) {
            return null;
        }

        QueryTableNew queryTable = new QueryTableNew();

        VBdTableEntity bdTableOld = (VBdTableEntity) bdObjectOld;
        VBdTableEntity bdTableNew = (VBdTableEntity) bdObjectNew;
        if (bdTableOld.getTypeObject().equals(ObjectTypes.getTABLE()) && bdTableNew.getTypeObject().equals(ObjectTypes.getTABLE())) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("alter table ").append(tunes.getOwner()).append(".").append(tunes.getPrefixTable()).append(bdTableOld.getCode()).append(" rename to ").append(tunes.getPrefixTable()).append(bdTableNew.getCode());
            queryTable.add(stringBuffer.toString());
        }

        return queryTable;
    }

    @Override
    public StepsEditServise getNextStep() {
        return nextStep;
    }
}
