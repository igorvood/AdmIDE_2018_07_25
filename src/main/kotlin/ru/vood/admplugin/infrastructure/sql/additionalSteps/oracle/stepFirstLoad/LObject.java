package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl.AddIndexImpl;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddConstraintSql;
import ru.vood.admplugin.infrastructure.sql.dbms.oracle.AddPrimaryKeySql;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LObject {

    public final static String tableName = "V_BD_OBJECT";

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
                "PARENT NUMBER,\n" +
                "TYPE_OBJECT NUMBER not null,\n" +
                "DATE_CREATE DATE default sysdate,\n" +
                "JAVA_CLASS VARCHAR2(512) not null\n" +
                ") tablespace \n" + pluginTunes.getTableSpaceSysTable() + "\n " +
                pluginTunes.getStorageTable();
        queryTable.add(s);

        s = primaryKeySql.generateSys(tableName);
        queryTable.add(s);

        s = constraintSql.getSql(tableName, "PARENT", tableName, "ID");
        queryTable.add(s);

        s = constraintSql.getSql(tableName, "TYPE_OBJECT", "V_BD_OBJECT_TYPE", "ID");
        queryTable.add(s);

//        ArrayList<String> listColom = new ArrayList<>();
//        listColom.add(COLLECTION);
        List<String> listCol = Arrays.asList("CODE", "PARENT", "TYPE_OBJECT").stream().collect(Collectors.toList());
        s = addIndexSql.generateSys(tableName, true, listCol);
        queryTable.add(s);

        return queryTable;
    }
}
