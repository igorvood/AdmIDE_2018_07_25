package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateBody

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.GenCodeCommonFunctionKT
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnnotationClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnyClassService
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import ru.vood.admplugin.infrastructure.spring.intf.VBdColumnsEntityService
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes

@Component
class GenerateEditorBodyKotlinCode(@Autowired
                                   val columnsEntityService: VBdColumnsEntityService,

                                   @Autowired
                                   val genCodeCommonFunction: GenCodeCommonFunctionKT,

                                   @Autowired
                                   val addAnyClassService: AddAnyClassService,

                                   @Autowired
                                   val addAnnotationClass: AddAnnotationClass

) : GenerateServiceBodyService {

    override fun genCode(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        val retVal = StringBuilder()
        val columnsEntities = columnsEntityService.findByParent(entity as VBdTableEntity?)
        val listReference = columnsEntities.asSequence()
                .filter { e -> e.typeColumn == ObjectTypes.getREFERENCE() }
                .toList()

        //val fields = StringBuilder()

        retVal.append(generateFields(listReference))

        retVal.append(generateConstructor(entity, listReference))

        return retVal
    }

    private fun generateConstructor(entity: VBdObjectEntity, listReference: List<VBdColumnsEntity>): StringBuilder {
        val retVal = StringBuilder()
        val listReference = listReference.asSequence()
                .map { vBdColumnsEntity -> (vBdColumnsEntity.typeValue as VBdTableEntity).toType }
                .toList()
        var reducedReference = java.lang.StringBuilder()
        if (listReference.size > 0) {
            reducedReference = listReference
                    .map { column ->
                        genCodeCommonFunction.getParametrName(column, TypeOfGenClass.SERVICE_CLASS).append(": ")
                                .append(genCodeCommonFunction.getClassName(column, TypeOfGenClass.SERVICE_CLASS))
                    }.reduce({ acc, stringBuilder -> acc.append(", ").append(stringBuilder) })
            reducedReference.append(", ")
        }


        // формирование парметров вызова конструктора
        // пример constructor(repository: CustomerRepository, typeCustomerRepository: TypeCustomerRepository)
        val currentRepository = genCodeCommonFunction.getParametrName(entity, TypeOfGenClass.SERVICE_CLASS)

        retVal.append(addAnnotationClass.getCode(Autowired::class.java))
                .append("constructor(")
                .append(reducedReference)
                .append(currentRepository)
                .append(": ")
                .append(genCodeCommonFunction.getClassName(entity, TypeOfGenClass.SERVICE_CLASS))
                .append(")\n")
        // формирование вызова конструктора супер класса
        retVal.append(": super(")
                .append(genCodeCommonFunction.getClassName(entity)).append("::class.java, ")
                .append(currentRepository)
                .append(")")

        // формирование тела конструктора
        retVal.append(" {\n")
        listReference.asSequence()
                .forEach {
                    retVal.append("this.")
                            .append(genCodeCommonFunction.getParametrName(it, TypeOfGenClass.SERVICE_CLASS))
                            .append(" = ")
                            .append(genCodeCommonFunction.getParametrName(it, TypeOfGenClass.SERVICE_CLASS))
                            .append("\n")
                }
        retVal.append("}\n\n")
        //список классов куда идет ссылка


/*
                .reduce { acc, vBdColumnsEntity ->
                    genCodeCommonFunction.getParametrName(acc).append(": ")
                            .append(genCodeCommonFunction.getClassName(acc, TypeOfGenClass.SERVICE_CLASS))
                            .append(",")
                            .append(genCodeCommonFunction.getParametrName(vBdColumnsEntity).append(": "))
                            .append(genCodeCommonFunction.getClassName(vBdColumnsEntity, TypeOfGenClass.SERVICE_CLASS))
                }
*/


        //.reduce { genCodeCommonFunction.getClassName(toType).append(": ") }
//                .forEach {
//            val toType = (it.typeValue as VBdTableEntity).toType
//            retVal.append(genCodeCommonFunction.getClassName(toType)).append(": ")
//                    .append(genCodeCommonFunction.getClassName(toType))
//                    .append(")")
        return retVal
    }

    private fun generateFields(listReference: List<VBdColumnsEntity>): StringBuilder {
        val retVal = StringBuilder()
        listReference.asSequence()
                .forEach {
                    val vBdTableEntity = (it.typeValue as VBdTableEntity).toType
                    addAnyClassService.getCode(vBdTableEntity, TypeOfGenClass.SERVICE_CLASS)
                    retVal.append("private val ")
                            .append(genCodeCommonFunction.getParametrName(vBdTableEntity, TypeOfGenClass.SERVICE_CLASS))
                            .append(": ")
                            .append(genCodeCommonFunction.getClassName(vBdTableEntity, TypeOfGenClass.SERVICE_CLASS))
                            .append("\n")
                }
        return retVal
    }
}