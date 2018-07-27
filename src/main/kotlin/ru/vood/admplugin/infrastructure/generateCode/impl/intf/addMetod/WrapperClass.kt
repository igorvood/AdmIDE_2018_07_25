package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

enum class WrapperClass(val wrapperName: String) {
    SET_WRAPPER("Set"),
    LIST_WRAPPER("List"),
    MAP_WRAPPER("Map"),
    NO_WRAPPER("");

    override fun toString(): String {
        return "$wrapperName"
    }


}

