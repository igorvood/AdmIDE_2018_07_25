package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.GenCodeCommonFunctionKT
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.message.AddingClassPublisher
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import ru.vood.admplugin.infrastructure.tune.PluginTunes

@Component
open class AddAnyClass : AddAnyClassService {

    @Autowired
    protected lateinit var pluginTunes: PluginTunes

    @Autowired
    protected lateinit var genCodeCommonFunctionKT: GenCodeCommonFunctionKT

    @Autowired
    protected lateinit var addingClassPublisher: AddingClassPublisher


    override fun getFullNameClass(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass): String {
        val clName = genCodeCommonFunctionKT.getClassName(entity, typeOfGenClass)
        val pack = genCodeCommonFunctionKT.getPackageName(typeOfGenClass)

        return "$pack.${clName}"
    }

    override fun getCode(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass): String {
        addingClassPublisher.publish(this, genCodeCommonFunctionKT.getFullClassName(entity, typeOfGenClass).toString())
        return genCodeCommonFunctionKT.getClassName(entity, typeOfGenClass).toString()

    }
}