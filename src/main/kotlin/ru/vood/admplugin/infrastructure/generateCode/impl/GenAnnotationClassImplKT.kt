package ru.vood.admplugin.infrastructure.generateCode.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenAnnotationClassServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnnotationClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.ParamOfAnnotation
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import ru.vood.admplugin.infrastructure.tune.PluginTunes
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table

@Component
class GenAnnotationClassImplKT(@Autowired
                               val genCodeCommonFunction: GenCodeCommonFunctionKT,
                               @Autowired
                               val addAnnotationClass: AddAnnotationClass,
                               @Autowired
                               val pluginTunes: PluginTunes) : GenAnnotationClassServiceKT {

    override fun genCode(entity: VBdTableEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        val code = StringBuilder("")
        if (typeOfGenClass == TypeOfGenClass.ENTITY_CLASS) code.append(genCodeEntity(entity))
        return code
    }

    private fun genCodeEntity(entity: VBdTableEntity): StringBuilder {
        val code = StringBuilder()
        code.append(addAnnotationClass.getCode(Entity::class.java))

        val paramOfAnnotation = ParamOfAnnotation()
        paramOfAnnotation.put("name", "\"${genCodeCommonFunction.getTableName(entity)}\"")
        paramOfAnnotation.put("schema", "\"${pluginTunes.owner}\"")

        code.append(addAnnotationClass.getCode(Table::class.java, paramOfAnnotation))

        if (genCodeCommonFunction.isRootEntity(entity, TypeOfGenClass.ENTITY_CLASS)) {
            paramOfAnnotation.clear()
            paramOfAnnotation.put("strategy", "InheritanceType.JOINED")
            addAnnotationClass.getCode(InheritanceType::class.java)
            code.append(addAnnotationClass.getCode(Inheritance::class.java, paramOfAnnotation))
        }
        return code
    }

}