package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.intf.CommonFunctionService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdColumnsEntityService;
import ru.vood.admplugin.infrastructure.spring.intf.VBdIndexEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.spring.referenceBook.Tables;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise;

import static ru.vood.admplugin.infrastructure.applicationConst.Const.COLLECTION;


@Component
public class AddArrayImpl implements StepsCreateAndDropServise {

    private CommonFunctionService commonFunction;
    private VBdColumnsEntityService colomnsEntityService;
    private VBdIndexEntityService indexEntityService;

    @Autowired
    public AddArrayImpl(CommonFunctionService commonFunction, VBdColumnsEntityService columnsEntityService, VBdIndexEntityService indexEntityService) {
        this.commonFunction = commonFunction;
        this.colomnsEntityService = columnsEntityService;
        this.indexEntityService = indexEntityService;
    }

    @Override
    public QueryTableNew createDDL(VBdObjectEntity bdObject) {
        if (!(bdObject instanceof VBdTableEntity)) {
            return null;
        }

        VBdTableEntity bdTable = (VBdTableEntity) bdObject;
        QueryTableNew queryTable = null;
        if (bdTable.getTypeObject().equals(ObjectTypes.getARRAY())) {
            queryTable = new QueryTableNew();

            VBdColumnsEntity colomnsEntity = new VBdColumnsEntity();
            colomnsEntity.setParent(((VBdTableEntity) bdObject).getToType());
            colomnsEntity.setCode(COLLECTION);
            colomnsEntity.setName("Идентификатор коллекции");
            colomnsEntity.setNotNull(true);
            colomnsEntity.setTypeColomn(ObjectTypes.getNUMBER());
            colomnsEntity.setTypeValue(Tables.getAny("NUM"));
            colomnsEntity.setTypeObject(ObjectTypes.getCOLUMN());
            colomnsEntity.setJavaClass(colomnsEntity.getClass().toString());
            VBdColumnsEntity new_colomnsEntity = colomnsEntityService.save(colomnsEntity);

            VBdIndexEntity indexEntity = new VBdIndexEntity();
            indexEntity.setCode("IDX_" + bdObject.getCode() + "_" + colomnsEntity.getCode());
            indexEntity.setName("IDX_" + bdObject.getCode() + "_" + colomnsEntity.getCode());
            indexEntity.setTypeObject(ObjectTypes.getINDEX());
            indexEntity.setParent(((VBdTableEntity) bdObject).getToType());
            indexEntity.setJavaClass(indexEntity.getClass().toString());
            indexEntity.setColumns(commonFunction.nextId());

            indexEntity.addColumn(colomnsEntity);
            indexEntity = indexEntityService.save(indexEntity);


            //String tmp = SQLFactory.getInstance().getSQLForAddCollectionId(bdTable.getToType().getCode());
//            queryTable.add(tmp);
//
//            tmp = addIndexSql.generateSys(pluginTunes.getPrefixTable() + bdTable.getToType().getCode(), SQLInterface.COLLECTION);
//            queryTable.add(tmp);


        }
        return queryTable;
    }
}
