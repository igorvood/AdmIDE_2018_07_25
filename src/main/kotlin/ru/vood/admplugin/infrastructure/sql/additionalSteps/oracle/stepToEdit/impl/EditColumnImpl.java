package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.sql.QueryTableNew;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepToEdit.abstr.StepsEditServise;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;

@Component
public class EditColumnImpl implements StepsEditServise {

    private PluginTunes tunes;

    @Autowired
    public EditColumnImpl(PluginTunes tunes) {
        this.tunes = tunes;
    }

    @Override
    public QueryTableNew editDDL(VBdObjectEntity bdObjectOld, VBdObjectEntity bdObjectNew) {
        QueryTableNew queryTable = new QueryTableNew();
        if (!(bdObjectOld instanceof VBdColumnsEntity) || !(bdObjectNew instanceof VBdColumnsEntity)) {
            return queryTable;
        }

        VBdColumnsEntity bdColumnOld = (VBdColumnsEntity) bdObjectOld;
        VBdColumnsEntity bdColumnNew = (VBdColumnsEntity) bdObjectNew;


        if (!bdColumnOld.getCode().equals(bdObjectNew.getCode()) || !bdColumnOld.getNotNull() == bdColumnNew.getNotNull()) {
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBegin = new StringBuffer();
            stringBegin.append(" ALTER TABLE ").append(tunes.getOwner()).append(".").append(tunes.getPrefixTable()).append(bdColumnNew.getParent().getCode());

            stringBuffer.append(stringBegin);
            // если переименование идет
            if (!bdColumnOld.getCode().equals(bdObjectNew.getCode())) {
                stringBuffer.append(" RENAME COLUMN ").append(bdColumnOld.getCode()).append(" to ").append(bdObjectNew.getCode());
                queryTable.add(stringBuffer.toString());
                stringBuffer = new StringBuffer(stringBegin);
            }

            // если ставится или убирается not null
            if (!bdColumnOld.getNotNull() == bdColumnNew.getNotNull()) {
                VBdTableEntity vBdTableEntity = (VBdTableEntity) bdColumnNew.getTypeValue();
                stringBuffer.append(" MODIFY ( ").append(bdColumnNew.getCode()).append(" ");
                if (bdColumnNew.getTypeValue().getTypeObject().equals(ObjectTypes.getSTRING())) {
                    String length = vBdTableEntity.getLength() == null ? " " : "(" + vBdTableEntity.getLength() + ")";
                    stringBuffer.append(" VARCHAR2 ").append(length).append(" ").append((bdColumnNew.getNotNull()) ? " not null" : " ").append(" )");
                    queryTable.add(stringBuffer.toString());
                } else if (bdColumnNew.getTypeValue().getTypeObject().equals(ObjectTypes.getNUMBER())) {
                    Long len = vBdTableEntity.getLength();
                    Long pres = vBdTableEntity.getPrecision();
                    String paramNum = "";
                    if (len > 0 && pres > 0) {
                        paramNum = "(" + len + "," + pres + ")";
                    } else if (len > 0) {
                        paramNum = "(" + len + ")";
                    }
                    stringBuffer.append(" NUMBER").append(paramNum).append(" ").append((bdColumnNew.getNotNull()) ? "not null" : "" + ")");
                    queryTable.add(stringBuffer.toString());
                } else if (bdColumnNew.getTypeValue().getTypeObject().equals(ObjectTypes.getDATE())) {
                    stringBuffer.append(" DATE ").append((bdColumnNew.getNotNull()) ? "not null" : "").append(")");
                    queryTable.add(stringBuffer.toString());
                } else if (bdColumnNew.getTypeValue().getTypeObject().equals(ObjectTypes.getREFERENCE())) {
                    stringBuffer.append(" NUMBER ").append((bdColumnNew.getNotNull()) ? "not null" : "").append(")");
                    queryTable.add(stringBuffer.toString());
                } else if (bdColumnNew.getTypeValue().getTypeObject().equals(ObjectTypes.getARRAY())) {
                    // МАссив всегда not null
                }
            }
        }
        return queryTable;
    }
}
