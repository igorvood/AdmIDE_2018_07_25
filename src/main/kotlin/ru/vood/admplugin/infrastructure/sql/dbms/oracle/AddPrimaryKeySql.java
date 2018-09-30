package ru.vood.admplugin.infrastructure.sql.dbms.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.LimitingNameDBMS;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

@Service
public class AddPrimaryKeySql {

    private PluginTunes tunes;

    private LimitingNameDBMS nameDBMS;

    @Autowired
    public AddPrimaryKeySql(PluginTunes tunes, LimitingNameDBMS nameDBMS) {
        this.tunes = tunes;
        this.nameDBMS = nameDBMS;
    }

    public String generateUserPK(String tableName) {
        return generate(tableName, tunes.getTableSpaceUserIndex());
    }

    public String generateSys(String tableName) {
        return generate(tableName, tunes.getTableSpaceSysIndex());
    }

    private String generate(String tableName, String tableSpace) {
        String nameConstraint = nameDBMS.getNameObj("PK#" + tableName);
        return "alter table " + tunes.getUser() + "." + tableName + "\n" +
                "  add constraint " + nameConstraint + " primary key (ID)\n" +
                "  using index tablespace \n" + tableSpace + tunes.getStorageIndex();
    }
}
