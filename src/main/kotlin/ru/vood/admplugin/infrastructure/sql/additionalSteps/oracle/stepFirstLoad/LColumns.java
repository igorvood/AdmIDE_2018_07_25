package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddConstraintSql;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddPrimaryKeySql;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

@Service
public class LColumns {

    protected final static String tableName = "V_BD_COLOMNS";
    @Autowired
    private PluginTunes pluginTunes;

    @Autowired
    private AddPrimaryKeySql primaryKeySql;

    @Autowired
    private AddConstraintSql constraintSql;

    QueryTableNew additionOne() {
        QueryTableNew queryTable = new QueryTableNew();


        String s = "create table " + pluginTunes.getUser() + "." + tableName + "\n" +
                "(ID    NUMBER not null,\n" +
                "NOT_NULL   VARCHAR2(1),\n" +
                "TYPE_COLOMN   NUMBER /*not null*/,\n" +
                "TYPE_VALUE NUMBER\n" +
                ") tablespace \n" + pluginTunes.getTableSpaceSysTable() + "\n " +
                pluginTunes.getStorageTable();
        queryTable.add(s);

        s = primaryKeySql.generateSys(tableName);
        queryTable.add(s);

        s = constraintSql.getSql(tableName, "TYPE_COLOMN", "V_BD_OBJECT_TYPE", "ID");
        queryTable.add(s);

        s = constraintSql.getSql(tableName, "TYPE_VALUE", "V_BD_OBJECT", "ID");
        queryTable.add(s);

        s = constraintSql.getSql(tableName, "ID", "V_BD_OBJECT", "ID");
        queryTable.add(s);

        return queryTable;
    }
}
