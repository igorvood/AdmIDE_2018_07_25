package ru.vood.admplugin.infrastructure.generateCode.impl

enum class TypeOfGenClass(val nameClass: String, val comment: String) {
    ENTITY_CLASS("Entity", "Сущность"),
    IMPL_CLASS("Impl", "Репозиторий"),
    SERVICE_CLASS("Service", "Интерфейс"),
    REPOSITORY_CLASS("Repository", "Репозиторий");

    override fun toString(): String {
        return "$nameClass"
    }

}

