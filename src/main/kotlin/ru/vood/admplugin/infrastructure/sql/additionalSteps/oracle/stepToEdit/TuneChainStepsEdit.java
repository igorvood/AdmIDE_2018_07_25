package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit.abstr.StepsEditServise;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit.abstr.TuneChainStepsEditService;

@Component
public class TuneChainStepsEdit extends TuneChainStepsEditService {

    //    @Autowired
//    @Qualifier("editTableImpl")
    private StepsEditServise table;

    @Autowired
    public TuneChainStepsEdit(@Qualifier("editTableImpl") StepsEditServise table) {
        this.table = table;
    }

    public void runChain(VBdObjectEntity bdobjOld, VBdObjectEntity bdobjNew) {
        // Вызов первого, остальное пойдет по цепочке
        QueryTableNew queryTable = table.runSteps(bdobjOld, bdobjNew);
        runQueryes(queryTable);
    }
}
