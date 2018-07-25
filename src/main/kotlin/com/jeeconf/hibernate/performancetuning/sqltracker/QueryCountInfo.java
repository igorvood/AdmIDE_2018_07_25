package com.jeeconf.hibernate.performancetuning.sqltracker;

import java.util.LinkedList;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 12/6/15
 */
public class QueryCountInfo {
    private int selectCount;
    private int insertCount;
    private int updateCount;
    private int deleteCount;
    private int callCount;

    private LinkedList<String> selectSQL = new LinkedList<>();
    private LinkedList<String> insertSQL = new LinkedList<>();
    private LinkedList<String> updateSQL = new LinkedList<>();
    private LinkedList<String> deleteSQL = new LinkedList<>();
    private LinkedList<String> callSQL = new LinkedList<>();

    public void incrementSelectCount(String sql) {
        selectCount++;
        selectSQL.add(sql);
    }

    public void incrementInsertCount(String sql) {
        insertCount++;
        insertSQL.add(sql);
    }

    public void incrementUpdateCount(String sql) {
        updateCount++;
        updateSQL.add(sql);
    }

    public void incrementDeleteCount(String sql) {
        deleteCount++;
        deleteSQL.add(sql);
    }

    public void incrementCallCount(String sql) {
        callCount++;
        callSQL.add(sql);
    }

    public void clear() {
        selectCount = 0;
        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        callCount = 0;
        selectSQL = new LinkedList<>();
        insertSQL = new LinkedList<>();
        updateSQL = new LinkedList<>();
        deleteSQL = new LinkedList<>();
        callSQL = new LinkedList<>();
    }

    public int countAll() {
        return selectCount + insertCount + updateCount + deleteCount + callCount;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public int getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(int deleteCount) {
        this.deleteCount = deleteCount;
    }

    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    public LinkedList<String> getSelectSQL() {
        return selectSQL;
    }

    public LinkedList<String> getInsertSQL() {
        return insertSQL;
    }

    public LinkedList<String> getUpdateSQL() {
        return updateSQL;
    }

    public LinkedList<String> getDeleteSQL() {
        return deleteSQL;
    }

    public LinkedList<String> getCallSQL() {
        return callSQL;
    }
}
