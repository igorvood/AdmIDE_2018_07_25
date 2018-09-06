package ru.vood.admplugin.infrastructure.generateCode.impl

import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenAnnotationClassServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnnotationClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.ParamOfAnnotation
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import ru.vood.admplugin.infrastructure.tune.PluginTunes
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table
import javax.transaction.Transactional

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
        if (typeOfGenClass == TypeOfGenClass.IMPL_CLASS) {
            code.append(addAnnotationClass.getCode(Service::class.java))
            code.append(addAnnotationClass.getCode(Repository::class.java))
            code.append(addAnnotationClass.getCode(Transactional::class.java))
        }
        if (typeOfGenClass == TypeOfGenClass.EDITOR_CLASS) {
            code.append(addAnnotationClass.getCode(SpringComponent::class.java))
            code.append(addAnnotationClass.getCode(UIScope::class.java))
        }

        if (typeOfGenClass == TypeOfGenClass.GRID_CLASS) {

            val paramAnnotation = ParamOfAnnotation()
            paramAnnotation.put(key = "value", value = "\"" + genCodeCommonFunction.getClassName(entity, TypeOfGenClass.GRID_CLASS) + "\"")
            code.append(addAnnotationClass.getCode(Route::class.java, paramAnnotation))
            code.append(addAnnotationClass.getCode(UIScope::class.java))
        }
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