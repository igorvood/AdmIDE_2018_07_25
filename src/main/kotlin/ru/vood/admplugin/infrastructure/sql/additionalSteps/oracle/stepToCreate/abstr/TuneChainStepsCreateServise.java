package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr;

import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.ChainQueryServise;

public abstract class TuneChainStepsCreateServise extends ChainQueryServise {
    public abstract void runChain(VBdObjectEntity bdobj);
}
