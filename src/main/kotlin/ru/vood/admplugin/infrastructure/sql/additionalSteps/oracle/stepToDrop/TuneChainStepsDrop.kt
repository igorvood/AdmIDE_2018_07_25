package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToDrop

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToDrop.abstr.TuneChainStepsDropService

@Service
class TuneChainStepsDrop : TuneChainStepsDropService() {
    @Autowired
    @Qualifier("dropTableImpl")
    private lateinit var table: StepsCreateAndDropServise

    override fun runChain(bdobj: VBdObjectEntity) {
        // Вызов первого, остальное пойдет по цепочке

        try {
            val queryTable = table.runSteps(bdobj)
            runQueryes(queryTable)
        } catch (e: Exception) {

        }

    }

}