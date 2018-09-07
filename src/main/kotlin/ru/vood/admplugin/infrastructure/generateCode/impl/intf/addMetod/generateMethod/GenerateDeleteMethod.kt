package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateMethod

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.WrappedType
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.WrapperClass
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

@Component
class GenerateDeleteMethod : GenerateSpecificMethodService {
    @Autowired
    lateinit var generateSimpleMethodService: GenerateSimpleMethodService

    override fun genCode(bdClass: VBdObjectEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        val wrappedType = WrappedType(bdClass, WrapperClass.NO_WRAPPER)
        val wrappedTypeRet = WrappedType(Unit::class.java, WrapperClass.NO_WRAPPER)
        return generateSimpleMethodService.genCode(bdClass, typeOfGenClass, "delete", wrappedTypeRet, wrappedType)
    }
}