package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.LimitingNameDBMS;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToCreate.abstr.StepsCreateAndDropServise;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;
import ru.vood.core.runtime.exception.ApplicationErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddIndexImpl implements StepsCreateAndDropServise {

    //    @Autowired
//    @Qualifier("addArrayImpl")
    private StepsCreateAndDropServise nextStep;

    //    @Autowired
    private LimitingNameDBMS limitingNameDBMS;

    //    @Autowired
    private PluginTunes pluginTunes;

    @Autowired
    public AddIndexImpl(@Qualifier("addArrayImpl") StepsCreateAndDropServise nextStep
            , LimitingNameDBMS limitingNameDBMS
            , PluginTunes pluginTunes) {
        this.nextStep = nextStep;
        this.limitingNameDBMS = limitingNameDBMS;
        this.pluginTunes = pluginTunes;
    }

    @Override
    public StepsCreateAndDropServise getNextStep() {
        return nextStep;
    }

    @Override
    public QueryTableNew createDDL(VBdObjectEntity bdObject) {
        if (!(bdObject instanceof VBdIndexEntity)) {
            return null;
        }

        QueryTableNew queryTable = new QueryTableNew();
        VBdIndexEntity bdIndex = (VBdIndexEntity) bdObject;
        if (bdIndex.getColumnsEntities() != null) {
            List s = bdIndex.getColumnsEntities().stream()
                    .map((c) -> c.getColumnRef().getCode())
                    .collect(Collectors.toList());
            //String sql = indexSql.generateUser(bdIndex.getParent().getCode(), bdIndex.getUniqueI(), s, null);
            String sql = generateAll(pluginTunes.getPrefixTable() + bdIndex.getParent().getCode(), bdIndex.getUniqueI(), false, pluginTunes.getTableSpaceUserIndex(), s, bdIndex.getCode());
            queryTable.add(sql);
        }

        return queryTable;

    }

    private String generateAll(String tableName, boolean isUnique, boolean isReverse, String tableSpace, List<String> columns, String nameIdx) {
        if (columns == null || columns.isEmpty()) {
            throw new ApplicationErrorException("Не определен список колонок для индекса.");
        }

        StringBuffer nameIndex = new StringBuffer(nameIdx);
        //nameIndex.append(tableName.toUpperCase());

        StringBuffer col = new StringBuffer(" (");

        StringBuffer res;
        if (!isUnique) {
            res = new StringBuffer("create index ");
        } else {
            res = new StringBuffer("create unique index ");
        }
        res.append(nameIndex);
        col.append(columns.stream().reduce((s1, s2) -> s1 + ", " + s2).orElse(" "));

        res.append(" on ");
        res.append(tableName);
        res.append(col);
        res.append(" ) ");
        res.append(" tablespace ");
        res.append(tableSpace);
        res.append(" ");
        res.append(pluginTunes.getStorageIndex());
        if (isReverse) {
            res.append(" REVERSE");
        }
        return res.toString();
    }

    public String generateSys(String tableName, boolean isUnique, List<String> columns) {
        String nameSysIndex = columns.stream().reduce((s1, s2) -> s1 + "_" + s2).orElse(" ");
        nameSysIndex = "SYS_IDX_" + tableName + "_" + nameSysIndex;
        nameSysIndex = limitingNameDBMS.getNameObj(nameSysIndex);
        return generateAll(tableName, isUnique, false, pluginTunes.getTableSpaceSysIndex(), columns, nameSysIndex);
    }

}
