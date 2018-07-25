package ru.vood.admplugin.infrastructure.applicationConst;

public enum TypeSystemObject {
    VIEW("Представления"),
    TABLE("Таблица"),
    INDEX("Таблицы"),

    PACAGE("Пакет"),
    CONSTRAINT("Ключ"),
    SEQUENCE("Последовательность");

    private String name;

    private TypeSystemObject(String name) {
        this.name = name;
    }

}
