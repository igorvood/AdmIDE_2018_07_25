package ru.vood.admplugin.infrastructure.generateCode.impl

import org.hibernate.annotations.GenericGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenClassBodyServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenFieldsServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnnotationClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddJavaClassToImport
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.ParamOfAnnotation
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import ru.vood.admplugin.infrastructure.spring.intf.VBdColumnsEntityService
import java.math.BigDecimal
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Component
class GenClassBodyImplKT(@Autowired
                         val genCodeCommonFunction: GenCodeCommonFunctionKT,

                         @Autowired
                         val columnsEntityService: VBdColumnsEntityService,

                         @Autowired
                         val genFieldsService: GenFieldsServiceKT,

                         @Autowired
                         val addAnnotationClass: AddAnnotationClass,

                         @Autowired
                         val addJavaClass: AddJavaClassToImport) : GenClassBodyServiceKT {


    private fun genCodeEntiy(entity: VBdTableEntity): StringBuilder {
        val code = StringBuilder()
        if (genCodeCommonFunction.isRootEntity(entity)) {
            code.append(getIdField())
        }

        val columnsEntities = columnsEntityService.findByParent(entity)

        for (column in columnsEntities) {
            code.append(genFieldsService.genCode(column, TypeOfGenClass.ENTITY_CLASS))
        }

        return code
    }

    private fun getIdField(): StringBuilder {
        val res = StringBuilder()
        val param = ParamOfAnnotation()
        res.append("/*Уникальный Идентификатор*/\n")

        res.append(addAnnotationClass.getCode(Id::class.java))

        param.put("name", "\"seqId\"")
        param.put("strategy", "\"ru.vood.Plugin.infrastructure.spring.entity.GeneratorId\"")
        res.append(addAnnotationClass.getCode(GenericGenerator::class.java, param))

        param.clear()
        param.put("generator", "\"seqId\"")
        res.append(addAnnotationClass.getCode(GeneratedValue::class.java, param))

        addJavaClass.getCode(BigDecimal::class.java)
        res.append("lateinit var id :  BigDecimal\n\n")
        return res
    }

    @JvmOverloads
    override fun genCode(entity: VBdTableEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        val code = StringBuilder()
        return if (typeOfGenClass == TypeOfGenClass.ENTITY_CLASS) genCodeEntiy(entity) else code
    }


}