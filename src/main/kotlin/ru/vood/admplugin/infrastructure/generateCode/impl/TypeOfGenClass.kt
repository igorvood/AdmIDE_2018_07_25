package ru.vood.admplugin.infrastructure.generateCode.impl

enum class TypeOfGenClass(val nameClass: String/*, val pack: String*/) {
    ENTITY_CLASS("Entity"),
    IMPL_CLASS("Impl"),
    SERVICE_CLASS("Service");

    override fun toString(): String {
        return "$nameClass"
    }

}

