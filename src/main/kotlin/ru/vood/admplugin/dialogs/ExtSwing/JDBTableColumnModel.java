package ru.vood.admplugin.dialogs.ExtSwing;

import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdColumnsEntityService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class JDBTableColumnModel extends AbstractTableModel {
    ArrayList<VBdObjectEntity> rows = new ArrayList<VBdObjectEntity>();
    //ArrayList<Object> rows = new ArrayList<Object>();
    ArrayList<String> cols = new ArrayList<String>();

    public JDBTableColumnModel() {
        cols.add("Длинное имя");
        cols.add("Короткое имя");
        cols.add("Наименование типа");
        cols.add("Тип");
        cols.add("Класс владелец");
        rows.add(new VBdObjectEntity());
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    @Override
    public int getRowCount() {
        return rows.size();
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    @Override
    public int getColumnCount() {
        return cols.size();
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rows.get(rowIndex) != null && rows.get(rowIndex).getId() != null) {
            if (columnIndex == 0) {
                return rows.get(rowIndex).getName();
            }
            if (columnIndex == 1) {
                return rows.get(rowIndex).getCode();
            }
            if (columnIndex == 2) {
                if (rows.get(rowIndex).getTypeObject() == null) {
                    return null;
                } else {
                    if (rows.get(rowIndex) instanceof VBdColumnsEntity) {
                        return ((VBdColumnsEntity) rows.get(rowIndex)).getTypeValue().getName();
                    } else {
                        return rows.get(rowIndex).getTypeObject().getName();
                    }
                }
            }
            if (columnIndex == 3) {
                if (rows.get(rowIndex).getTypeObject() == null) {
                    return null;
                } else {
                    if (rows.get(rowIndex) instanceof VBdColumnsEntity) {
                        return ((VBdColumnsEntity) rows.get(rowIndex)).getTypeValue().getTypeObject().getName();
                    } else {
                        return rows.get(rowIndex).getTypeObject().getName();
                    }
                    //return rows.get(rowIndex).getTypeObject().getName();
                }
            }


//                if (rows.get(rowIndex).getParent() == null) {
//                    return null;
//                } else {
//                    return rows.get(rowIndex).getParent().getCode();
//                }

            if (columnIndex == 4) {
                return rows.get(rowIndex).getParent().getName();
            }

        }
        return null;
    }

    /**
     * Returns a default name for the column using spreadsheet conventions:
     * A, B, C, ... Z, AA, AB, etc.  If <code>column</code> cannot be found,
     * returns an empty string.
     *
     * @param column the column being queried
     * @return a string containing the default name of <code>column</code>
     */
    @Override
    public String getColumnName(int column) {
        return cols.get(column);
    }

    /**
     * Returns false.  This is the default implementation for all cells.
     *
     * @param rowIndex    the row being queried
     * @param columnIndex the column being queried
     * @return false
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public boolean loadTableByObj(VBdTableEntity bdObject) {
        boolean isEmpty = true;
        if (bdObject == null) {
            return false;
        }

        VBdColumnsEntityService vBdColumnsEntityService = LoadedCTX.getService(VBdColumnsEntityService.class);
        List<VBdColumnsEntity> columns = vBdColumnsEntityService.findAllByParent(bdObject);
        if (columns != null) {
            for (VBdColumnsEntity col : columns) {
                rows.add(col);
                isEmpty = false;
            }
        }
        // }
        if (isEmpty) {
            rows.add(new VBdObjectEntity());
        }
        return true;
    }

    public void clear() {
        rows.clear();
        //rows.add(new BDObject());
    }

    public VBdObjectEntity getSelectedTypeObject(int rowIndex) {
        return ((VBdColumnsEntity) rows.get(rowIndex)).getTypeValue();
    }
}
