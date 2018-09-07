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
    private VBdColumnsEntityService columnsEntityService;
    private VBdIndexEntityService indexEntityService;

    @Autowired
    public AddArrayImpl(CommonFunctionService commonFunction, VBdColumnsEntityService columnsEntityService, VBdIndexEntityService indexEntityService) {
        this.commonFunction = commonFunction;
        this.columnsEntityService = columnsEntityService;
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

            VBdColumnsEntity columnsEntity = new VBdColumnsEntity();
            columnsEntity.setParent(((VBdTableEntity) bdObject).getToType());
            columnsEntity.setCode(COLLECTION);
            columnsEntity.setName("Идентификатор коллекции");
            columnsEntity.setNotNull(true);
            columnsEntity.setTypeColumn(ObjectTypes.getNUMBER());
            columnsEntity.setTypeValue(Tables.getAny("NUM"));
            columnsEntity.setTypeObject(ObjectTypes.getCOLUMN());
            columnsEntity.setJavaClass(columnsEntity.getClass().toString());
            columnsEntityService.save(columnsEntity);

            VBdIndexEntity indexEntity = new VBdIndexEntity();
            indexEntity.setCode("IDX_" + bdObject.getCode() + "_" + columnsEntity.getCode());
            indexEntity.setName("IDX_" + bdObject.getCode() + "_" + columnsEntity.getCode());
            indexEntity.setTypeObject(ObjectTypes.getINDEX());
            indexEntity.setParent(((VBdTableEntity) bdObject).getToType());
            indexEntity.setJavaClass(indexEntity.getClass().toString());
            indexEntity.setColumns(commonFunction.nextId());

            indexEntity.addColumn(columnsEntity);
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
