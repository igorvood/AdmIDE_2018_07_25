package ru.vood.admplugin.dialogs.ExtSwing;


import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class JDBTableIndexColomnsModel extends AbstractTableModel {

    ArrayList<VBdObjectEntity> rows = new ArrayList<VBdObjectEntity>();

    ArrayList<String> cols = new ArrayList<String>();

    public JDBTableIndexColomnsModel(ArrayList<VBdObjectEntity> rows, String col) {
        this.rows = rows;
        this.cols.add(col);
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cols.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rows.get(rowIndex) != null && rows.get(rowIndex).getId() != null) {
            if (columnIndex == 0) {
                return rows.get(rowIndex).getName() + " [" + rows.get(rowIndex).getCode() + "]";
            }
        }
        return null;
    }

    public String getColumnName(int column) {
        return cols.get(column);
    }

    public VBdColumnsEntity getSelectedTypeObject(int rowIndex) {
        return ((VBdColumnsEntity) rows.get(rowIndex));
    }

    public void addColomn(VBdColumnsEntity colomnsEntity) {
        rows.add(colomnsEntity);
    }

    public void deleteColomn(VBdColumnsEntity colomnsEntity) {
        rows.remove(colomnsEntity);
    }

    public ArrayList<VBdObjectEntity> getRows() {
        return rows;
    }
}
