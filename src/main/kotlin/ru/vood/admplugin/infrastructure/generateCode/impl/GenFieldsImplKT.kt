package ru.vood.admplugin.infrastructure.generateCode.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenAnnotationFieldsServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenFieldsServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddJavaClassToImportService
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import ru.vood.admplugin.infrastructure.spring.except.ApplicationException
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes
import java.math.BigDecimal
import java.util.*

@Component
class GenFieldsImplKT(@Autowired
                      val genCodeCommonFunction: GenCodeCommonFunctionKT,

                      @Autowired
                      val genAnnotationFieldsService: GenAnnotationFieldsServiceKT,

                      @Autowired
                      @Qualifier("addJavaClassToImport")
                      val addJavaClass: AddJavaClassToImportService) : GenFieldsServiceKT {

    override fun genCode(entity: VBdColumnsEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        val code = StringBuilder()
        if (typeOfGenClass == TypeOfGenClass.ENTITY_CLASS) {
            code.append("/*Наименование поля - ${entity.name}*/\n")
            code.append(genAnnotationFieldsService.genCode(entity, typeOfGenClass))
            code.append("private lateinit var ")
            code.append(genCodeCommonFunction.genFieldName(entity).toString()).append(" : ")
            code.append(genColumnClass(entity)).append("\n\n")
        }

        if (typeOfGenClass == TypeOfGenClass.EDITOR_CLASS) {
            if (entity.typeColumn == ObjectTypes.getREFERENCE()) {
                code.append("private lateinit var ")
                code.append(genCodeCommonFunction.genFieldName(entity).toString()).append(" : ")
                code.append(genCodeCommonFunction.getClassName(entity.typeValue)).append("\n\n")
            }
        }
        return code
    }

    private fun genColumnClass(col: VBdColumnsEntity): String =
            when (col.typeColumn) {
                ObjectTypes.getSTRING() -> " String "
                ObjectTypes.getNUMBER() -> {
                    addJavaClass.getCode(BigDecimal::class.java)
                    " BigDecimal "
                }
                ObjectTypes.getDATE() -> {
                    addJavaClass.getCode(Date::class.java)
                    " Date "
                }
                ObjectTypes.getBOOLEAN() -> {
                    addJavaClass.getCode(Boolean::class.java)
                    " Boolean "
                }
                ObjectTypes.getARRAY() -> {
                    addJavaClass.getCode(BigDecimal::class.java)
                    " BigDecimal "
                }
                ObjectTypes.getREFERENCE() -> {
                    val tVal = col.typeValue as VBdTableEntity
                    " " + genCodeCommonFunction.getClassName(tVal.toType).toString() + " "
                }
                else -> throw ApplicationException("Невозможно преобразовать тип колонки ${col.typeValue.typeObject.code} ")
            }

}