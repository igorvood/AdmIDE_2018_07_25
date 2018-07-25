package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.TuneChainStepsCreateServise;


@Service
public class TuneChainStepsCreate extends TuneChainStepsCreateServise {

    @Autowired
    @Qualifier("addTableImpl")
    private StepsCreateAndDropServise table;
/*
Тут есть циклическая зависимость
┌─────┐
|  tuneChainStepsCreate defined in file [C:\_developer\java\AdmIDEBoot1\admplugin\out\production\classes\ru\vood\admplugin\infrastructure\sql\additionalSteps\oracle\stepToCreate\TuneChainStepsCreate.class]
↑     ↓
|  addTableImpl defined in file [C:\_developer\java\AdmIDEBoot1\admplugin\out\production\classes\ru\vood\admplugin\infrastructure\sql\additionalSteps\oracle\stepToCreate\impl\AddTableImpl.class]
↑     ↓
|  addColumnImpl defined in file [C:\_developer\java\AdmIDEBoot1\admplugin\out\production\classes\ru\vood\admplugin\infrastructure\sql\additionalSteps\oracle\stepToCreate\impl\AddColumnImpl.class]
└─────┘
*/

    public void runChain(VBdObjectEntity bdobj) {
        // Вызов первого, остальное пойдет по цепочке
        QueryTableNew queryTable = null;
        queryTable = table.runSteps(bdobj);
        runQueryes(queryTable);
    }
}
