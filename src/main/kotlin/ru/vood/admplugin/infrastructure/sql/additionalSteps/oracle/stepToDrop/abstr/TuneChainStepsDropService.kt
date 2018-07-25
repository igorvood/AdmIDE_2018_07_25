package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToDrop.abstr

import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.ChainQueryServise

abstract class TuneChainStepsDropService : ChainQueryServise() {

    abstract fun runChain(bdobj: VBdObjectEntity)

}