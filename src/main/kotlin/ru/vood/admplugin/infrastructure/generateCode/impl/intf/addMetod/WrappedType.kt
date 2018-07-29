package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import java.lang.reflect.Type

class WrappedType(val bdClass: VBdObjectEntity, val wrapperClass: WrapperClass = WrapperClass.NO_WRAPPER) {

    var type: Type? = null

    constructor(type: Type, wrapperClass: WrapperClass = WrapperClass.NO_WRAPPER) : this(VBdObjectEntity(), wrapperClass) {
        this.type = type
    }

}
