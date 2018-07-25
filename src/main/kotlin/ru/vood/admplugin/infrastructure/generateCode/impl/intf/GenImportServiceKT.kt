package ru.vood.admplugin.infrastructure.generateCode.impl.intf

import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

interface GenImportServiceKT : GenAnyPartKT<VBdObjectEntity> {
    fun clearImports()
}