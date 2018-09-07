package ru.vood.admplugin.infrastructure.generateCode.impl

import org.hibernate.annotations.GenericGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenClassBodyServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenFieldsServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenTransientFieldsServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateBody.GenerateServiceBodyService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnnotationClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddJavaClassToImportService
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
                         @Qualifier("addJavaClassToImport")
                         val addJavaClass: AddJavaClassToImportService) : GenClassBodyServiceKT {

    @Autowired
    @Qualifier("generateServiceBodyKotlinCode")
    lateinit var generateServiceBodyService: GenerateServiceBodyService

    @Autowired
    @Qualifier("generateImplementationBodyKotlinCode")
    lateinit var generateImplementationBodyKotlinCode: GenerateServiceBodyService

    @Autowired
    @Qualifier("generateRepositoryBodyKotlinCode")
    lateinit var generateRepositoryBodyKotlinCode: GenerateServiceBodyService

    @Autowired
    @Qualifier("generateEditorBodyKotlinCode")
    lateinit var generateEditorBodyKotlinCode: GenerateServiceBodyService

    @Autowired
    lateinit var vBdColumnsEntityService: VBdColumnsEntityService

    @Autowired
    lateinit var genTransientFieldsServiceKT: GenTransientFieldsServiceKT


    private fun genCodeEntity(entity: VBdTableEntity): StringBuilder {
        val code = StringBuilder()
        if (genCodeCommonFunction.isRootEntity(entity)) {
            code.append(getIdField())
        }
        val columnsEntities = columnsEntityService.findByParent(entity)
        //создание обычных полей
        for (column in columnsEntities) {
            code.append(genFieldsService.genCode(column, TypeOfGenClass.ENTITY_CLASS))
        }
        //создание Transient полей, напрмер для обратных ссылок
        val columnRefInCurrentEntity = vBdColumnsEntityService.findColumnRefIn(entity)

        for (trancientColumn in columnRefInCurrentEntity) {
            code.append(genTransientFieldsServiceKT.genCode(trancientColumn))
        }

        if (genCodeCommonFunction.isRootEntity(entity)) {
            code.append("\noverride fun getId() = id\n")
        }
        return code
    }

    private inline fun getIdField(): StringBuilder {
        val res = StringBuilder()
        val param = ParamOfAnnotation()
        res.append("/*Уникальный Идентификатор*/\n")

        res.append(addAnnotationClass.getCode(Id::class.java))

        param.put("name", "\"seqId\"")
        param.put("strategy", "\"ru.vood.admplugin.infrastructure.spring.entity.GeneratorId\"")
        res.append(addAnnotationClass.getCode(GenericGenerator::class.java, param))

        param.clear()
        param.put("generator", "\"seqId\"")
        res.append(addAnnotationClass.getCode(GeneratedValue::class.java, param))

        addJavaClass.getCode(BigDecimal::class.java)
        res.append("private lateinit var id :  BigDecimal\n\n")
        return res
    }


    override fun genCode(entity: VBdTableEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        val code = StringBuilder()
        return when (typeOfGenClass) {
            TypeOfGenClass.ENTITY_CLASS -> genCodeEntity(entity)
            TypeOfGenClass.SERVICE_CLASS -> generateServiceBodyService.genCode(entity, typeOfGenClass)
            TypeOfGenClass.IMPL_CLASS -> generateImplementationBodyKotlinCode.genCode(entity, typeOfGenClass)
            TypeOfGenClass.REPOSITORY_CLASS -> generateRepositoryBodyKotlinCode.genCode(entity, typeOfGenClass)
            TypeOfGenClass.EDITOR_CLASS -> generateEditorBodyKotlinCode.genCode(entity, typeOfGenClass)
            else -> code
        }

    }


}