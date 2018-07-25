package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

@Service
@Deprecated
public class LView {
    @Autowired
    private PluginTunes pluginTunes;

    QueryTableNew additionOne() {

        QueryTableNew queryTable = new QueryTableNew();


        String s = ("CREATE OR REPLACE VIEW VW_CLASS_FOR_TREE AS \n" +
                " select level as c_level, a1.*\n" + ", a3.table_space, a3.storage, a3.to_type, a3.length, a3.precision \n" +
                "                from " + pluginTunes.getOwner() + ".V_BD_OBJECT a1\n" +
                "                   , " + pluginTunes.getOwner() + ".V_BD_OBJECT_TYPE a2\n" +
                "                   , " + pluginTunes.getOwner() + ".V_BD_TABLE a3\n" +
                "               where a1.TYPE_OBJECT = a2.id\n" +
                "                       and a3.id(+) = a1.id\n" +
                "                   and a2.code in ('DATE' , 'REFERENCE' , 'ARRAY' , 'STRING' , 'NUMBER' , 'TABLE')\n" +
                "                CONNECT BY PRIOR a1.ID=a1.PARENT\n" +
                "                start with a1.PARENT is null ORDER SIBLINGS by a1.id desc\n" +
                "                ");

        queryTable.add(s);


        s = ("CREATE OR REPLACE VIEW VW_COLOMN_FOR_TABLE AS\n" +
                "select col.id, " +
                "col.not_null, " +
                "col.type_colomn, " +
                "col.type_value, " +
                "obj.code, " +
                "obj.name,obj.parent, " +
                "obj.type_object, " +
                "obj.java_class " +
                "           from " + pluginTunes.getOwner() + ".v_BD_COLOMNS col\n" +
                "              ," + pluginTunes.getOwner() + ".V_BD_OBJECT obj\n" +
                "           where col.id=   obj.id");
        queryTable.add(s);

        return queryTable;
    }
}