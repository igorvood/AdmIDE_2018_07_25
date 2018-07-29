package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

import org.springframework.stereotype.Component

@Component
class InnerParameters : HashMap<String, WrappedType>() {

    fun addOne(nameParam: String, wrappedType: WrappedType): InnerParameters {
        this.put(nameParam, wrappedType)
        return this
    }

}