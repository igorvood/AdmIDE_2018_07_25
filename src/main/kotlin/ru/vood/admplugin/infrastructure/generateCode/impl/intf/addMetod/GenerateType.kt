package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.GenCodeCommonFunctionKT
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnyClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddJavaClassToImportService
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import java.lang.reflect.Type

@Component
class GenerateType : GenerateTypeService /*(val clazz: VBdObjectEntity, val wrapperClass: WrapperClass = WrapperClass.NO_WRAPPER)*/ {

    @Autowired
    lateinit var genCodeCommonFunctionKT: GenCodeCommonFunctionKT

    @Autowired
    lateinit var addAnyClass: AddAnyClass

    @Autowired
    lateinit var addJavaClassToImport: AddJavaClassToImportService

    override fun getCode(wrappedType: WrappedType) =
            if (wrappedType.type == null) getCode(wrappedType.bdClass, wrappedType.wrapperClass)
            else getCode(wrappedType.type, wrappedType.wrapperClass)


    fun getCode(clazz: Type, wrapperClass: WrapperClass): StringBuilder {
        val cl = StringBuilder(addJavaClassToImport.getCode(clazz))
        return wrap(cl, wrapperClass)
    }

    private fun getCode(clazz: VBdObjectEntity, wrapperClass: WrapperClass): StringBuilder {
        addAnyClass.getCode(clazz, TypeOfGenClass.ENTITY_CLASS)
        val c = genCodeCommonFunctionKT.genFieldName(clazz).append(TypeOfGenClass.ENTITY_CLASS)
        return wrap(c, wrapperClass)
    }


    private fun wrap(cl: StringBuilder, wrapperClass: WrapperClass = WrapperClass.NO_WRAPPER) =
            if (wrapperClass == WrapperClass.NO_WRAPPER) cl
            else StringBuilder().append(wrapperClass.toString())
                    .append("<").append(cl).append(">")
}