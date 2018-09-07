package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

import java.lang.reflect.Type
import java.util.*

enum class WrapperClass(val wrapperName: String, val type: Type) {
    SET_WRAPPER("Set", Set::class.java),
    LIST_WRAPPER("List", List::class.java),
    KOTLIN_LIST_WRAPPER("kotlin.collections.List", kotlin.collections.List::class.java),
    MAP_WRAPPER("Map", Map::class.java),
    MUTABLEITERABLE_WRAPPER("MutableIterable", MutableIterable::class.java),
    OPTIONAL_WRAPPER("Optional", Optional::class.java),


    NO_WRAPPER("", Any::class.java);

    override fun toString(): String {
        return "$wrapperName"
    }


}

