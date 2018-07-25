package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl.AddIndexImpl;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddConstraintSql;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddPrimaryKeySql;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

import java.util.ArrayList;

import static ru.vood.admplugin.infrastructure.applicationConst.Const.COLLECTION;


@Service
public class LIndexedColumns {
    public final static String tableName = "V_BD_INDEXED_COLOMNS";

    @Autowired
    private PluginTunes pluginTunes;

    @Autowired
    private AddPrimaryKeySql primaryKeySql;

    @Autowired
    private AddIndexImpl addIndex;

    @Autowired
    private AddConstraintSql constraintSql;

    public QueryTableNew additionOne() {

        QueryTableNew queryTable = new QueryTableNew();

        String s = "create table " + pluginTunes.getUser() + "." + tableName + "\n" +
                "(ID    NUMBER not null,\n" +
                COLLECTION + " NUMBER,\n" +
                "COLUMN_REF   NUMBER  not null \n" +
                ") tablespace \n" + pluginTunes.getTableSpaceSysTable() + "\n" +
                pluginTunes.getStorageTable();
        queryTable.add(s);

        s = primaryKeySql.generateSys(tableName);
        queryTable.add(s);

        ArrayList<String> listColom = new ArrayList<>();
        listColom.add(COLLECTION);
        s = addIndex.generateSys(tableName, false, listColom);
        queryTable.add(s);

        s = constraintSql.getSql(tableName, "COLUMN_REF", "V_BD_OBJECT", "ID");
        queryTable.add(s);


        return queryTable;
    }
}
