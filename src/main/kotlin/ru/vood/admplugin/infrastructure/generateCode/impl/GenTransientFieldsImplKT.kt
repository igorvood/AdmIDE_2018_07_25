package ru.vood.admplugin.infrastructure.generateCode.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenAnnotationFieldsServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenTransientFieldsServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.GenerateTypeService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.WrappedType
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.WrapperClass
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity

@Component
class GenTransientFieldsImplKT : GenTransientFieldsServiceKT {

    @Autowired
    lateinit var genAnnotationFieldsServiceKT: GenAnnotationFieldsServiceKT

    @Autowired
    lateinit var genCodeCommonFunction: GenCodeCommonFunctionKT

    @Autowired
    lateinit var generateTypeService: GenerateTypeService

    override fun genCode(entity: VBdColumnsEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        val code = StringBuilder()
        if (typeOfGenClass == TypeOfGenClass.ENTITY_CLASS) {
            code.append("/*Список объектов найденных по обратной ссылке на текущую сущность\n" +
                    " Класс ${entity.parent.code}, поле - ${entity.code}\n" +
                    " ${entity.parent.name} - ${entity.name} */\n")
            code.append(genAnnotationFieldsServiceKT.genCode(entity, typeOfGenClass, true))
            code.append("lateinit var ")
            code.append(genCodeCommonFunction.genFieldName(entity.parent).toString()).append("List : ")

            val wrappedType = WrappedType(entity.parent, WrapperClass.LIST_WRAPPER)
            code.append(generateTypeService.getCode(wrappedType)).append("\n\n")
        }
        return code
    }
}