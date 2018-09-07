package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateMethod

import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.WrappedType
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

interface GenerateSimpleMethodService {
    //fun genCode(bdClass: VBdObjectEntity, typeOfGenClass: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS): StringBuilder
    fun genCode(bdClass: VBdObjectEntity, typeOfGenClass: TypeOfGenClass, nameMethod: String, retType: WrappedType, inType: WrappedType?): StringBuilder
}
