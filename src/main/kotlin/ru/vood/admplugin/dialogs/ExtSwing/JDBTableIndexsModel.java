package ru.vood.admplugin.dialogs.ExtSwing;


import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexedColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdIndexEntityService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class JDBTableIndexsModel extends AbstractTableModel {

    ArrayList<VBdObjectEntity> rows = new ArrayList<VBdObjectEntity>();
    //ArrayList<Object> rows = new ArrayList<Object>();
    ArrayList<String> cols = new ArrayList<String>();

    public JDBTableIndexsModel() {
        cols.add("Имя");
        cols.add("Наименование типа");
        cols.add("Класс владелец");
        cols.add("Индексируемые солонки");
        rows.add(new VBdObjectEntity());
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return cols.size();
    }

    public String getColumnName(int column) {
        return cols.get(column);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rows.get(rowIndex) != null && rows.get(rowIndex).getId() != null) {
            if (columnIndex == 0) {
                return rows.get(rowIndex).getCode();
            }
            if (columnIndex == 1) {
                if (rows.get(rowIndex).getTypeObject() == null) {
                    return null;
                } else {
                    if (rows.get(rowIndex) instanceof VBdIndexEntity) {
                        return rows.get(rowIndex).getParent().getName();
                    } else {
                        return rows.get(rowIndex).getTypeObject().getName();
                    }
                }
            }
            if (columnIndex == 2) {
                return rows.get(rowIndex).getParent().getName();
            }
            if (columnIndex == 3) {
                List<VBdIndexedColumnsEntity> indexedColumnsEntities = ((VBdIndexEntity) rows.get(rowIndex)).getColumnsEntities();
                if (indexedColumnsEntities != null && !indexedColumnsEntities.isEmpty()) {
                    return indexedColumnsEntities.stream()
                            .map(entity -> entity.getColumnRef().getCode())
                            .reduce((s1, s2) -> s1 + ", " + s2).orElse(" ");
                }
                return null;
            }
        }
        return null;
    }

    public boolean loadTableByObj(VBdTableEntity bdObject) {
        clear();
        boolean isEmpty = true;
        if (bdObject == null) {
            return false;
        }

        VBdIndexEntityService indexEntityService = LoadedCTX.getService(VBdIndexEntityService.class);
        List<VBdIndexEntity> indexEntities = indexEntityService.findByParent(bdObject);
        if (indexEntities != null) {
            for (VBdIndexEntity idx : indexEntities) {
                rows.add(idx);
                isEmpty = false;
            }
        }
        if (isEmpty) {
            rows.add(new VBdObjectEntity());
        }
        return true;
    }

    public void clear() {
        rows.clear();
    }

    public VBdObjectEntity getSelectedTypeObject(int rowIndex) {
        return rows.get(rowIndex);
    }

}
