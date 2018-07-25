package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl.AddIndexImpl;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddConstraintSql;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddPrimaryKeySql;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

import java.util.ArrayList;

@Service
public class LObjType {

    public final static String tableName = "V_BD_OBJECT_TYPE";

    @Autowired
    private PluginTunes pluginTunes;

    @Autowired
    private AddPrimaryKeySql primaryKeySql;

    @Autowired
    private AddConstraintSql constraintSql;

    @Autowired
    private AddIndexImpl addIndexSql;

    public QueryTableNew additionOne() {
        QueryTableNew queryTable = new QueryTableNew();

        String s = "create table " + pluginTunes.getUser() + "." + tableName + "\n" +
                "(ID    NUMBER not null,\n" +
                "CODE   VARCHAR2(50) not null,\n" +
                "NAME   VARCHAR2(250) not null,\n" +
                "NEED_DDL   VARCHAR2(1),\n" +
                "PARENT NUMBER\n" +
                ") tablespace \n" + pluginTunes.getTableSpaceSysTable() + "\n " +
                pluginTunes.getStorageTable();
        queryTable.add(s);

        s = primaryKeySql.generateSys(tableName);
        queryTable.add(s);

        s = constraintSql.getSql(tableName, "PARENT", tableName, "ID");
        queryTable.add(s);
        ArrayList<String> listCol = new ArrayList<>();
        listCol.add("CODE");
        s = addIndexSql.generateSys(tableName, true, listCol);
        queryTable.add(s);

        return queryTable;
    }
}
