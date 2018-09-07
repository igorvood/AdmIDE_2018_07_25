package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl.AddIndexImpl;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToDrop.impl.DropIndexImpl;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit.abstr.StepsEditServise;

@Component
public class EditIndexImpl implements StepsEditServise {

    private AddIndexImpl addIndex;
    private DropIndexImpl dropServise;

    @Autowired
    public EditIndexImpl(AddIndexImpl addIndex, DropIndexImpl dropServise) {
        this.addIndex = addIndex;
        this.dropServise = dropServise;
    }

    @Override
    public QueryTableNew editDDL(VBdObjectEntity bdObjectOld, VBdObjectEntity bdObjectNew) {
        if (!(bdObjectNew instanceof VBdIndexEntity) || !(bdObjectOld instanceof VBdIndexEntity)) {
            return null;
        }
        QueryTableNew queryTable = new QueryTableNew();
        VBdIndexEntity bdIndexOld = (VBdIndexEntity) bdObjectOld;
        VBdIndexEntity bdIndexNew = (VBdIndexEntity) bdObjectNew;
        if (bdIndexNew.getColumnsEntities() != null) {
            if (!bdIndexOld.getCode().equals(bdIndexNew.getCode())) {
                if (bdIndexOld.getColumnsEntities().size() != bdIndexNew.getColumnsEntities().size()) {
                    queryTable.addAll(dropServise.createDDL(bdIndexOld));
                    queryTable.addAll(addIndex.createDDL(bdIndexNew));
                }
            }
        }
        return queryTable;
    }

}
