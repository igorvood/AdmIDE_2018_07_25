package ru.vood.admplugin.infrastructure.generateCode.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenAnnitationFieldsServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnnotationClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.ParamOfAnnotation
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity
import ru.vood.admplugin.infrastructure.spring.except.ApplicationException
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes
import javax.persistence.*

@Component
class GenAnnitationFieldsImplKT : GenAnnitationFieldsServiceKT {


    private val addAnnotationClass: AddAnnotationClass

    @Autowired
    constructor(addAnnotationClass: AddAnnotationClass) {
        this.addAnnotationClass = addAnnotationClass
    }


    override fun genCode(entity: VBdColumnsEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        when (entity.typeColumn) {
            ObjectTypes.getSTRING() -> return genSimple(entity)
            ObjectTypes.getNUMBER() -> return genSimple(entity)
            ObjectTypes.getDATE() -> return genDate(entity)
            ObjectTypes.getBOOLEAN() -> return genSimple(entity)
            ObjectTypes.getARRAY() -> return genSimple(entity)
            ObjectTypes.getREFERENCE() -> return genRef(entity)
            else -> throw ApplicationException("Невозможно преобразовать тип колонки ${entity.typeColumn?.code} ")
        }
    }

    private fun genSimple(col: VBdColumnsEntity): StringBuilder {
        var param = ParamOfAnnotation()
        param.put("name ", "\"" + col.code + "\"")
        val nullable = if (!col.notNull) "true" else "false"
        param.put("nullable ", nullable)
        return StringBuilder(addAnnotationClass.getCode(Column::class.java, param))
    }

    private fun genDate(col: VBdColumnsEntity): StringBuilder {
        val ret = genSimple(col)
        //для того что бы в импорт добавить
        addAnnotationClass.getCode(TemporalType::class.java)

        val param = ParamOfAnnotation()
        param.put("TemporalType.DATE", "")
        ret.append(addAnnotationClass.getCode(Temporal::class.java, param))

        return ret
    }

    private fun genRef(col: VBdColumnsEntity): StringBuilder {
        val ret = StringBuilder()
        val paramManyToOne = ParamOfAnnotation()
        addAnnotationClass.getCode(FetchType::class.java)

        paramManyToOne.put("fetch", "FetchType.LAZY")
        ret.append(addAnnotationClass.getCode(ManyToOne::class.java, paramManyToOne))

        val paramJoinColumn = ParamOfAnnotation()
        paramJoinColumn.put("name", "\"${col.code}\"")
        paramJoinColumn.put("referencedColumnName", "\"ID\"")

        ret.append(addAnnotationClass.getCode(JoinColumn::class.java, paramJoinColumn))

        return ret
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PARENT", referencedColumnName = "ID")


}