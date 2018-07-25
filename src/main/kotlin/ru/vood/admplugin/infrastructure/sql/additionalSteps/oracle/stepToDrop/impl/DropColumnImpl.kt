package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToDrop.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import ru.vood.admplugin.infrastructure.sql.QueryTableNew
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise
import ru.vood.admplugin.infrastructure.tune.PluginTunes

@Component
class DropColumnImpl : StepsCreateAndDropServise {

    @Autowired
    private lateinit var tunes: PluginTunes

    @Autowired
    @Qualifier("dropIndexImpl")
    private lateinit var nextStep: StepsCreateAndDropServise


    override fun createDDL(bdObject: VBdObjectEntity): QueryTableNew {
        val col = bdObject as? VBdColumnsEntity ?: return QueryTableNew()
        val queryTable = QueryTableNew()
        queryTable.add("ALTER TABLE ${tunes.owner}.${tunes.prefixTable}${col.parent.code} DROP COLUMN ${col.code}")
        return queryTable
    }

    override fun getNextStep(): StepsCreateAndDropServise {
        return nextStep
    }
}