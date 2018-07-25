package ru.vood.admplugin.infrastructure.spring.except;

public class NoDataFoundException extends CoreExeption {

    public NoDataFoundException() {
        super("Данные не найдены");
    }
}
