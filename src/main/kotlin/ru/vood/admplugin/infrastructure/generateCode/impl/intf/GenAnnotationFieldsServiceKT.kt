package ru.vood.admplugin.infrastructure.generateCode.impl.intf

import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity

interface GenAnnotationFieldsServiceKT {

    fun genCode(entity: VBdColumnsEntity, typeOfGenClass: TypeOfGenClass, backReference: Boolean = false): StringBuilder
}