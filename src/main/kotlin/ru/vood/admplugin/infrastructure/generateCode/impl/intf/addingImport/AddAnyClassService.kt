package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport

import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

interface AddAnyClassService {
    fun getFullNameClass(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass): String
    fun getCode(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS): String
}
