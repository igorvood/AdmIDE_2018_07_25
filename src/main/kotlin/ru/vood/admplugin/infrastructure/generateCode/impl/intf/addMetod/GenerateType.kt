package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.GenCodeCommonFunctionKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddJavaClassToImportService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.message.AddingClassPublisher
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import java.lang.reflect.Type

@Component
class GenerateType : GenerateTypeService /*(val clazz: VBdObjectEntity, val wrapperClass: WrapperClass = WrapperClass.NO_WRAPPER)*/ {

    @Autowired
    lateinit var genCodeCommonFunctionKT: GenCodeCommonFunctionKT

/*
    @Autowired
    lateinit var addAnyClass: AddAnyClass
*/

    @Autowired
    lateinit var addJavaClassToImport: AddJavaClassToImportService

    @Autowired
    protected lateinit var addingClassPublisher: AddingClassPublisher


    override fun getCode(wrappedType: WrappedType): StringBuilder {
        addingClassPublisher.publish(this, wrappedType.wrapperClass.type)
        return if (wrappedType.type == null) getCode(wrappedType.bdClass, wrappedType.wrapperClass)
        else getCode(wrappedType.type!!, wrappedType.wrapperClass)
    }


    private fun getCode(clazz: Type, wrapperClass: WrapperClass): StringBuilder {
        addingClassPublisher.publish(this, clazz)
        val cl = StringBuilder(addJavaClassToImport.getCode(clazz))
        return wrap(cl, wrapperClass)
    }

    private fun getCode(clazz: VBdObjectEntity, wrapperClass: WrapperClass): StringBuilder {
        val fullClassName = genCodeCommonFunctionKT.getFullClassName(clazz).toString()
        addingClassPublisher.publish(this, fullClassName)
        val c = genCodeCommonFunctionKT.getClassName(clazz)//.append(TypeOfGenClass.ENTITY_CLASS)
        return wrap(c, wrapperClass)
    }


    private fun wrap(cl: StringBuilder, wrapperClass: WrapperClass = WrapperClass.NO_WRAPPER) =
            if (wrapperClass == WrapperClass.NO_WRAPPER) cl
            else StringBuilder().append(wrapperClass.toString())
                    .append("<").append(cl).append(">")
}