package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToDrop.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import ru.vood.admplugin.infrastructure.sql.QueryTableNew
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise
import ru.vood.admplugin.infrastructure.tune.PluginTunes

@Component
class DropIndexImpl(@Autowired
                    val tunes: PluginTunes
) : StepsCreateAndDropServise {

    override fun createDDL(bdObject: VBdObjectEntity): QueryTableNew {
        val index = bdObject as? VBdIndexEntity ?: return QueryTableNew()
        val queryTable = QueryTableNew()
        queryTable.add("DROP INDEX  ${tunes.owner}.${index.code}")
        return queryTable
    }

}