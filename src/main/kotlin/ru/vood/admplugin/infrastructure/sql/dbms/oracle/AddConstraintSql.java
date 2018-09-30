package ru.vood.admplugin.infrastructure.sql.dbms.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.LimitingNameDBMS;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

@Service
public class AddConstraintSql {

    private PluginTunes pluginTunes;

    private LimitingNameDBMS limitingNameDBMS;

    @Autowired
    public AddConstraintSql(PluginTunes pluginTunes, LimitingNameDBMS limitingNameDBMS) {
        this.pluginTunes = pluginTunes;
        this.limitingNameDBMS = limitingNameDBMS;
    }

    public String getSql(String tableName, String column, String refTableName, String refColumn) {
        return "alter table " + pluginTunes.getUser() + "." + tableName + "\n" +
                "  add constraint " + limitingNameDBMS.getNameObj("FK#" + tableName + "_" + column + "_" + refTableName) + " foreign key (" + column + ")\n" +
                "  references " + pluginTunes.getUser() + "." + refTableName + " (" + refColumn + ")\n";
    }

    public String getSqlAndAddPrefix(String tableName, String column, String refTableName, String refColumn) {
        return "alter table " + pluginTunes.getUser() + "." + pluginTunes.getPrefixTable() + tableName + "\n" +
                "  add constraint " + limitingNameDBMS.getNameObj("FK#" + pluginTunes.getPrefixTable() +
                tableName + "_" + column + "_" + pluginTunes.getPrefixTable() + refTableName) + " foreign key (" + column + ")\n" +
                "  references " + pluginTunes.getUser() + "." + pluginTunes.getPrefixTable() + refTableName + " (" + refColumn + ")\n";
    }
}
