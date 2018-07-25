package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectTypeEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;

@Service
public class LObjectInsert {

    @Autowired
    private VBdObjectEntityService bdObjectEntityService;

    QueryTableNew additionOne() {


        VBdObjectTypeEntity bdObjType_TABLE = ObjectTypes.getTABLE();
        VBdObjectTypeEntity bdObjType_OBJECT = ObjectTypes.getOBJECT();


        VBdObjectEntity bdObject_obj = new VBdObjectEntity();
        bdObject_obj.setCode("OBJECT");
        bdObject_obj.setName("Объекты");
        bdObject_obj.setParent(null);
        bdObject_obj.setTypeObject(bdObjType_OBJECT);
        bdObject_obj.setJavaClass(VBdObjectEntity.class.toString());

        VBdObjectEntity newbdObject_obj = bdObjectEntityService.save(bdObject_obj);

        VBdObjectTypeEntity bdObjType_DATE = ObjectTypes.getDATE();

        VBdObjectEntity bdObject_date = new VBdObjectEntity();
        bdObject_date.setCode("DATE");
        bdObject_date.setName("Даты");
        bdObject_date.setParent(bdObject_obj);
        bdObject_date.setTypeObject(bdObjType_OBJECT);
        bdObject_date.setJavaClass(VBdObjectEntity.class.toString());
        bdObjectEntityService.save(bdObject_date);


        VBdObjectEntity bdObject_bool = new VBdObjectEntity();
        bdObject_bool.setCode("BOOLEAN");
        bdObject_bool.setName("Логика");
        bdObject_bool.setParent(bdObject_obj);
        bdObject_bool.setTypeObject(bdObjType_OBJECT);
        bdObject_bool.setJavaClass(VBdObjectEntity.class.toString());
        bdObjectEntityService.save(bdObject_bool);


        VBdObjectTypeEntity bdObjType_REFERENCE = ObjectTypes.getREFERENCE();

        VBdObjectEntity bdObject_REFERENCE = new VBdObjectEntity();
        bdObject_REFERENCE.setCode("REFERENCE");
        bdObject_REFERENCE.setName("Ссылки");
        bdObject_REFERENCE.setParent(bdObject_obj);
        bdObject_REFERENCE.setTypeObject(bdObjType_OBJECT);
        bdObject_REFERENCE.setJavaClass(VBdObjectEntity.class.toString());
        bdObjectEntityService.save(bdObject_REFERENCE);

        VBdObjectTypeEntity bdObjType_ARRAY = ObjectTypes.getDATE();

        VBdObjectEntity bdObject_ARRAY = new VBdObjectEntity();
        bdObject_ARRAY.setCode("ARRAY");
        bdObject_ARRAY.setName("Массивы");
        bdObject_ARRAY.setParent(bdObject_obj);
        bdObject_ARRAY.setTypeObject(bdObjType_OBJECT);
        bdObject_ARRAY.setJavaClass(VBdObjectEntity.class.toString());
        bdObjectEntityService.save(bdObject_ARRAY);


        VBdObjectTypeEntity bdObjType_STRING = ObjectTypes.getSTRING();

        VBdObjectEntity bdObject_STRING = new VBdObjectEntity();
        bdObject_STRING.setCode("STRING");
        bdObject_STRING.setName("Строки");
        bdObject_STRING.setParent(bdObject_obj);
        bdObject_STRING.setTypeObject(bdObjType_OBJECT);
        bdObject_STRING.setJavaClass(VBdObjectEntity.class.toString());
        bdObjectEntityService.save(bdObject_STRING);


        VBdObjectTypeEntity bdObjType_NUMBER = ObjectTypes.getNUMBER();

        VBdObjectEntity bdObject_NUMBER = new VBdObjectEntity();
        bdObject_NUMBER.setCode("NUMBER");
        bdObject_NUMBER.setName("Числа");
        bdObject_NUMBER.setParent(bdObject_obj);
        bdObject_NUMBER.setTypeObject(bdObjType_OBJECT);
        bdObject_NUMBER.setJavaClass(VBdObjectEntity.class.toString());
        bdObjectEntityService.save(bdObject_NUMBER);


        VBdObjectEntity bdObject_table = new VBdObjectEntity();
        bdObject_table.setCode("TABLE");
        bdObject_table.setName("Таблицы");
        bdObject_table.setParent(bdObject_obj);
        bdObject_table.setTypeObject(bdObjType_OBJECT);
        bdObject_table.setJavaClass(VBdObjectEntity.class.toString());
        bdObjectEntityService.save(bdObject_table);

        return null;
    }
}
