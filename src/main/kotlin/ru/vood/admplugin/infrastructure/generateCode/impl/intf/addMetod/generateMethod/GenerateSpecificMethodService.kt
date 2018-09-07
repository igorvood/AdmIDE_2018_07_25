package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateMethod

import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

interface GenerateSpecificMethodService {

    fun genCode(bdClass: VBdObjectEntity, typeOfGenClass: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS): StringBuilder
}