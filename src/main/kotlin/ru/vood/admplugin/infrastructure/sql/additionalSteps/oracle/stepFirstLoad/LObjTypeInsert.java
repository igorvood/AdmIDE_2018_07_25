package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectTypeEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectTypeEntityService;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;

@Service
public class LObjTypeInsert {

    @Autowired
    //@Qualifier("jpaVBdObjectTypeEntityService")
    private VBdObjectTypeEntityService entityService;

    public QueryTableNew additionOne() {

        VBdObjectTypeEntity bdObjType_obj = new VBdObjectTypeEntity();
        bdObjType_obj.setCode("OBJECT");
        bdObjType_obj.setName("Объект");
        bdObjType_obj.setNeedDDL(false);
        bdObjType_obj = entityService.save(bdObjType_obj);


        VBdObjectTypeEntity bdObjType_TYPE = new VBdObjectTypeEntity();
        bdObjType_TYPE.setCode("TABLE");
        bdObjType_TYPE.setName("Таблица");
        bdObjType_TYPE.setNeedDDL(true);
        bdObjType_TYPE = entityService.save(bdObjType_TYPE);


        VBdObjectTypeEntity bdObjType_REFERENCE = new VBdObjectTypeEntity();
        bdObjType_REFERENCE.setCode("REFERENCE");
        bdObjType_REFERENCE.setName("Ссылка");
        bdObjType_REFERENCE = entityService.save(bdObjType_REFERENCE);

        VBdObjectTypeEntity bdObjType_ARRAY = new VBdObjectTypeEntity();
        bdObjType_ARRAY.setCode("ARRAY");
        bdObjType_ARRAY.setName("Массив");
        bdObjType_ARRAY.setNeedDDL(true);
        bdObjType_ARRAY = entityService.save(bdObjType_ARRAY);

        VBdObjectTypeEntity bdObjType_STRING = new VBdObjectTypeEntity();
        bdObjType_STRING.setCode("STRING");
        bdObjType_STRING.setName("Строка");
        bdObjType_STRING = entityService.save(bdObjType_STRING);

        VBdObjectTypeEntity bdObjType_NUMBER = new VBdObjectTypeEntity();
        bdObjType_NUMBER.setCode("NUMBER");
        bdObjType_NUMBER.setName("Число");
        bdObjType_NUMBER = entityService.save(bdObjType_NUMBER);

        VBdObjectTypeEntity bdObjType_DATE = new VBdObjectTypeEntity();
        bdObjType_DATE.setCode("DATE");
        bdObjType_DATE.setName("Дата");
        bdObjType_DATE = entityService.save(bdObjType_DATE);


        VBdObjectTypeEntity bdObjType_BOOLEAN = new VBdObjectTypeEntity();
        bdObjType_BOOLEAN.setCode("BOOLEAN");
        bdObjType_BOOLEAN.setName("Логика");
        bdObjType_BOOLEAN = entityService.save(bdObjType_BOOLEAN);

        VBdObjectTypeEntity bdObjType_column = new VBdObjectTypeEntity();
        bdObjType_column.setCode("COLOMN");
        bdObjType_column.setName("Колонка таблицы");
        bdObjType_column.setParent(bdObjType_TYPE);
        bdObjType_column.setNeedDDL(true);
        bdObjType_column = entityService.save(bdObjType_column);

        VBdObjectTypeEntity bdObjType_index = new VBdObjectTypeEntity();
        bdObjType_index.setCode("INDEX");
        bdObjType_index.setName("Индекс таблицы");
        bdObjType_index.setParent(bdObjType_TYPE);
        bdObjType_index.setNeedDDL(true);
        bdObjType_index = entityService.save(bdObjType_index);

        return null;
    }
}
