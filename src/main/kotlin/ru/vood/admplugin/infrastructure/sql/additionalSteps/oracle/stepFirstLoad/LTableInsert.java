package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdColumnsEntityService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectEntityService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdTableEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.spring.referenceBook.RootObjects;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;

@Service
public class LTableInsert {

    @Autowired
    private VBdTableEntityService bdTableEntityService;

    @Autowired
    private VBdObjectEntityService objectEntityService;

    @Autowired
    private VBdColumnsEntityService columnsEntityService;

    public QueryTableNew additionOne() {

        VBdTableEntity bdObject_date_table = new VBdTableEntity();
        bdObject_date_table.setCode("DATE");
        bdObject_date_table.setName("Дата");
        bdObject_date_table.setParent(RootObjects.getDATE());
        bdObject_date_table.setTypeObject(ObjectTypes.getDATE());
        bdObject_date_table.setJavaClass(VBdTableEntity.class.toString());
        VBdTableEntity newTableEntity = bdTableEntityService.save(bdObject_date_table);

        VBdTableEntity bdObject_boolean_table = new VBdTableEntity();
        bdObject_boolean_table.setCode("BOOLEAN");
        bdObject_boolean_table.setName("Логика");
        bdObject_boolean_table.setParent(RootObjects.getBOOLEAN());
        bdObject_boolean_table.setTypeObject(ObjectTypes.getBOOLEAN());
        bdObject_boolean_table.setJavaClass(VBdTableEntity.class.toString());
        VBdTableEntity newbdObject_boolean_table = bdTableEntityService.save(bdObject_boolean_table);


        VBdTableEntity bdObject_num1_table = new VBdTableEntity();
        bdObject_num1_table.setCode("NUM");
        bdObject_num1_table.setName("Число");
        bdObject_num1_table.setParent(RootObjects.getNUMBER());
        bdObject_num1_table.setTypeObject(ObjectTypes.getNUMBER());
        bdObject_num1_table.setJavaClass(VBdTableEntity.class.toString());
        VBdTableEntity newbdObject_num_table = bdTableEntityService.save(bdObject_num1_table);

//        VBdTableEntity bdObject_str_table = new VBdTableEntity();
//        bdObject_str_table.setCode("STR_160");
//        bdObject_str_table.setName("Стока(160)");
//        bdObject_str_table.setParent(RootObjects.getSTRING());
//        bdObject_str_table.setTypeObject(ObjectTypes.getSTRING());
//        bdObject_str_table.setLength(160L);
//        bdObject_str_table.setJavaClass(VBdTableEntity.class.toString());
//
//        VBdTableEntity bdObject_str_table_new = bdTableEntityService.save(bdObject_str_table);
//
//
//        VBdTableEntity bdObject_num_table = new VBdTableEntity();
//        bdObject_num_table.setCode("NUM_17_2");
//        bdObject_num_table.setName("Число(17,2)");
//        bdObject_num_table.setParent(RootObjects.getNUMBER());
//        bdObject_num_table.setTypeObject(ObjectTypes.getNUMBER());
//        bdObject_num_table.setLength(17L);
//        bdObject_num_table.setPrecision(2L);
//        bdObject_num_table.setJavaClass(VBdTableEntity.class.toString());
//
//        VBdTableEntity bdObject_num_table_new = bdTableEntityService.save(bdObject_num_table);
//
//
//        VBdTableEntity bdObject_table = new VBdTableEntity();
//        bdObject_table.setCode("CLIENT");
//        bdObject_table.setName("Клиенты");
//        bdObject_table.setParent(RootObjects.getTABLE());
//        bdObject_table.setTypeObject(ObjectTypes.getTABLE());
//        bdObject_table.setJavaClass(VBdTableEntity.class.toString());
//
//        VBdTableEntity bdObject_table_new = bdTableEntityService.save(bdObject_table);
//
//        VBdTableEntity bdObject_table_aderss = new VBdTableEntity();
//        bdObject_table_aderss.setCode("address");
//        bdObject_table_aderss.setName("Адреса");
//        bdObject_table_aderss.setParent(RootObjects.getTABLE());
//        bdObject_table_aderss.setTypeObject(ObjectTypes.getTABLE());
//        bdObject_table_aderss.setJavaClass(VBdTableEntity.class.toString());
//
//        bdObject_table_aderss = bdTableEntityService.save(bdObject_table_aderss);
//
//        VBdColumnsEntity colomnsEntity = new VBdColumnsEntity();
//        colomnsEntity.setCode("CITY");
//        colomnsEntity.setName("Город");
//        colomnsEntity.setTypeColumn(ObjectTypes.getSTRING());
//        colomnsEntity.setTypeValue(bdObject_str_table_new);
//        colomnsEntity.setParent(bdObject_table_aderss);
//        colomnsEntity.setTypeObject(ObjectTypes.getCOLUMN());
//        colomnsEntity.setJavaClass(colomnsEntity.getClass().toString());
//        colomnsEntity = columnsEntityService.save(colomnsEntity);
//
//        colomnsEntity = new VBdColumnsEntity();
//        colomnsEntity.setCode("STREET");
//        colomnsEntity.setName("Улица");
//        colomnsEntity.setTypeColumn(ObjectTypes.getSTRING());
//        colomnsEntity.setTypeValue(bdObject_str_table_new);
//        colomnsEntity.setParent(bdObject_table_aderss);
//        colomnsEntity.setTypeObject(ObjectTypes.getCOLUMN());
//        colomnsEntity.setJavaClass(colomnsEntity.getClass().toString());
//        colomnsEntity = columnsEntityService.save(colomnsEntity);
//
//        VBdTableEntity table = new VBdTableEntity();
//        table.setCode(bdObject_table_aderss.getCode() + "_ARR");
//        table.setName("Массив <" + bdObject_table_aderss.getName() + ">");
//        table.setTypeObject(ObjectTypes.getARRAY());
//        table.setJavaClass(table.getClass().toString());
//        table.setParent(RootObjects.getARRAY());
//        table.setToType(bdObject_table_aderss);
//
//        table = bdTableEntityService.save(table);
//
//
////        for (int i = 0; i <10000 ; i++) {
////            bdObject_table_aderss = new VBdTableEntity();
////            bdObject_table_aderss.setCode("TMP_"+i);
////            bdObject_table_aderss.setName("Временно "+i);
////            bdObject_table_aderss.setParent(Tables.getTABLE());
////            bdObject_table_aderss.setTypeObject(ObjectTypes.getTABLE());
////            bdObject_table_aderss.setJavaClass(VBdTableEntity.class.toString());
////
////            bdObject_table_aderss = bdTableEntityService.save(bdObject_table_aderss);
////        }
//
//        VBdTableEntity bdObject_table_type_adress = new VBdTableEntity();
//        bdObject_table_type_adress.setCode("TYPE_ADRESS");
//        bdObject_table_type_adress.setName("Типы адресов");
//        bdObject_table_type_adress.setParent(objectEntityService.findByCode("TABLE"));
//        bdObject_table_type_adress.setTypeObject(ObjectTypes.getTABLE());
//        bdObject_table_type_adress.setJavaClass(VBdTableEntity.class.toString());
//
//        bdObject_table_type_adress = bdTableEntityService.save(bdObject_table_type_adress);
//
//
//        table = new VBdTableEntity();
//        table.setCode(bdObject_table_type_adress.getCode() + "_REF");
//        table.setName("Ссылка <" + bdObject_table_type_adress.getName() + ">");
//        table.setTypeObject(ObjectTypes.getREFERENCE());
//        table.setJavaClass(table.getClass().toString());
//        table.setParent(objectEntityService.findByCode("REFERENCE"));
//        table.setToType(bdObject_table_type_adress);
//
//        table = bdTableEntityService.save(table);
//
//
//        colomnsEntity = new VBdColumnsEntity();
//        colomnsEntity.setCode("NAME");
//        colomnsEntity.setName("ФИО");
//        colomnsEntity.setTypeColumn(ObjectTypes.getSTRING());
//        colomnsEntity.setTypeValue(bdObject_str_table_new);
//        colomnsEntity.setParent(bdObject_table_new);
//        colomnsEntity.setTypeObject(ObjectTypes.getCOLUMN());
//        colomnsEntity.setJavaClass(colomnsEntity.getClass().toString());
//        colomnsEntity = columnsEntityService.save(colomnsEntity);
//
//        colomnsEntity = new VBdColumnsEntity();
//        colomnsEntity.setCode("DATE_BIRTH");
//        colomnsEntity.setName("Дата рождения");
//        colomnsEntity.setTypeColumn(ObjectTypes.getDATE());
//        colomnsEntity.setTypeValue(Tables.getAny("DATE"));
//        colomnsEntity.setParent(bdObject_table_new);
//        colomnsEntity.setTypeObject(ObjectTypes.getCOLUMN());
//        colomnsEntity.setJavaClass(colomnsEntity.getClass().toString());
//        colomnsEntity = columnsEntityService.save(colomnsEntity);


        return null;
    }
}
