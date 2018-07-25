package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport

import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

interface AddAnyClassService {
    fun getFullNameClass(entity: VBdObjectEntity): String
    fun getCode(entity: VBdObjectEntity): String
}
