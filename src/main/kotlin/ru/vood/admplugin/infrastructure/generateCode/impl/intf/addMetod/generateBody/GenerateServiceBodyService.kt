package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateBody

import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

interface GenerateServiceBodyService {
    fun genCode(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS): StringBuilder
}