package ru.vood.admplugin.infrastructure.applicationConst;

public enum SystemObject {

    VW_CLASS_FOR_TREE(TypeSystemObject.VIEW
            , "VW_CLASS_FOR_TREE"
            , "поиск всех родителей для класса который передается через контекст через параметр ID"
            , ""),
    VW_COLOMN_FOR_TABLE(TypeSystemObject.VIEW
            , "VW_COLOMN_FOR_TABLE"
            , "поиск всех реквизитов индексо и ДР"
            , ""),


    SEQ_ID(TypeSystemObject.SEQUENCE
            , "SEQ_ID"
            , "Общий счетчик для генерации ID"
            , "");


    /*VW_VD_VCLASS_ALL_PARENT(TypeSystemObject.VIEW
            , "VW_VD_VCLASS_all_parent"
            , "поиск всех родителей для класса который передается через контекст через параметр ID"
            , ""),

    VW_VD_CLASS_PROP_FOR_CL(TypeSystemObject.VIEW
            , "VW_VD_CLASS_PROP_FOR_CL"
            , "-- представление отображает все реквизиты переданного типа \n" +
            "-- тип передается через контекст TYPE\n"
            , ""),

    VW_VD_DEPEND_FOR_STUCTURE(TypeSystemObject.VIEW
            , "VW_VD_DEPEND_FOR_STUCTURE"
            , "-- представление отображает все реквизиты сложного типа в случае когда тип текущего реквизита - сложный реквизит \n" +
            "-- тип передается через контекст TYPE\n"
            , ""),


    VW_VD_VCLASS_DEPENDENCE(TypeSystemObject.VIEW
            , "VW_VD_VCLASS_DEPENDENCE"
            , "-- поиск всех зависимостей для класс который передается через контекст через параметр ID\n" +
            "-- так же через параметр TYPE_DEPEND передается тип зависимости, которую нужно получить, null значит все зависимости\n" +
            "--поиск всех потомком для текущего класса\n"
            , ""),
    VW_VD_VCLASS_FOR_TREE(TypeSystemObject.VIEW
            , "VW_VD_VCLASS_FOR_TREE"
            , "-- Сбор информации по дереву классов для отображения в дереве на ЭФ\n"
            , ""),
    VW_VD_VCLASS_COLUMN_TABLE(TypeSystemObject.VIEW
            , "VW_VD_VCLASS_COLUMN_TABLE"
            , "-- Сбор информации по колонкам класса, из ID класса вычисляется\n"
            , ""),
*/


    private TypeSystemObject type;
    private String nameInDB;
    private String description;
    private String script;

    SystemObject(TypeSystemObject type, String nameInDB, String description, String script) {
        this.nameInDB = nameInDB;
        this.description = description;
        this.type = type;
        this.script = script;
    }

    public TypeSystemObject getType() {
        return type;
    }

    public String getNameInDB() {
        return nameInDB;
    }

    public String getDescription() {
        return description;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
