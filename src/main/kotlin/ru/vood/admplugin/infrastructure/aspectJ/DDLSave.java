package ru.vood.admplugin.infrastructure.aspectJ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.except.ApplicationException;
import ru.vood.admplugin.infrastructure.sql.ExceptObjectName;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.LimitingNameDBMS;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.TuneChainStepsCreateServise;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToDrop.TuneChainStepsDrop;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit.TuneChainStepsEdit;

@Service
public class DDLSave {

    private TuneChainStepsCreateServise tuneChainStepsCreateServise;

    private TuneChainStepsDrop tuneChainStepsDrop;

    private TuneChainStepsEdit tuneChainStepsEdit;

    private ExceptObjectName exeptObjectName;

    private LimitingNameDBMS limitingNameDBMS;

    @Autowired
    public DDLSave(TuneChainStepsCreateServise tuneChainStepsCreateServise, TuneChainStepsDrop tuneChainStepsDrop, TuneChainStepsEdit tuneChainStepsEdit, ExceptObjectName exeptObjectName, LimitingNameDBMS limitingNameDBMS) {
        this.tuneChainStepsCreateServise = tuneChainStepsCreateServise;
        this.tuneChainStepsDrop = tuneChainStepsDrop;
        this.tuneChainStepsEdit = tuneChainStepsEdit;
        this.exeptObjectName = exeptObjectName;
        this.limitingNameDBMS = limitingNameDBMS;
    }

    public void checkRun(Object joinPoint, Object o) {
        if (o instanceof VBdObjectEntity) {
            beforeTest((VBdObjectEntity) o);
        }
        System.out.println(joinPoint);
        System.out.println(o);
    }

    public void before(Object joinPoint, Object[] o, VBdObjectEntity objectEntity) {
        Object bdObj = o[0];
        System.out.println(objectEntity);
        if (bdObj instanceof VBdObjectEntity) {
            VBdObjectEntity entity = (VBdObjectEntity) bdObj;
            if (exeptObjectName.allowAdd(entity.getCode())) {

            }
        }
        System.out.println(joinPoint);
        System.out.println(o);
    }

    public void afterSave(Object savedObj, boolean create, Object oldObj) {
        if (create & savedObj != null) {
            if (savedObj instanceof VBdObjectEntity) {
                VBdObjectEntity entity = (VBdObjectEntity) savedObj;
                if (entity.getTypeObject().isNeedDDL()) {
                    if (exeptObjectName.allowAdd(entity.getCode())) {
                        try {
                            tuneChainStepsCreateServise.runChain(entity);
                        } catch (Exception e) {
                            throw new ApplicationException(e.getMessage(), e);
                        }
                    }
                }
            }
        } else if (!create) {
            if (savedObj instanceof VBdObjectEntity && oldObj instanceof VBdObjectEntity) {
                VBdObjectEntity bdTableOld = (VBdObjectEntity) oldObj;
                VBdObjectEntity bdTableNew = (VBdObjectEntity) savedObj;
                if (bdTableNew.getTypeObject().isNeedDDL()) {
                    if (exeptObjectName.allowAdd(bdTableNew.getCode())) {
                        tuneChainStepsEdit.runChain(bdTableOld, bdTableNew);
                    }
                }
            }
        }
        System.out.println(savedObj);
    }

    public void afterDrop(Object[] dropedObj) {
        Object d = dropedObj[0];
        if (d instanceof VBdObjectEntity) {
            VBdObjectEntity entity = (VBdObjectEntity) d;
            if (entity.getTypeObject().isNeedDDL()) {
                tuneChainStepsDrop.runChain(entity);
            }
        }
    }

    public void error(Throwable throwable) {
        throwable.printStackTrace();
    }

    public void beforeTest(VBdObjectEntity o) {
        limitingNameDBMS.getNameObj(o);
    }
}
