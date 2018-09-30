package ru.vood.admplugin.infrastructure.spring.impl;

import org.junit.Test;
import ru.vood.admplugin.BaseTest;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.referenceBook.Tables;

public class VBdColumnsEntityImplTest extends BaseTest {

    @Test
    public void findByParent() {
    }

    @Test
    public void findAllByParent() {
    }

    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findColumn() {
    }

    @Test
    public void findColumnRefIn() {
        final VBdTableEntity type_adress = Tables.getAny("TYPE_ADRESS");
//        final List<VBdColumnsEntity> columnRefIn = colomnsEntityService.findColumnRefIn(type_adress);
        //      System.out.println(columnRefIn);
    }
}