package ru.vood.admplugin.infrastructure.tune;

import jfork.nproperty.Cfg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Cfg
public class PluginTunes {
    @Value("${nameCurrentList}")
    private String nameCurrentList;

    @Value("${defaultConfiguration}")
    private Boolean defaultConfiguration;

    @Value("${packageIn}")
    private String packageIn;

    @Value("${password}")
    private String password;

    @Value("${host}")
    private String host;

    @Value("${port}")
    private String port;

    @Value("${sid}")
    private String sid;

    @Value("${tableSpaceSysTable}")
    private String tableSpaceSysTable;

    @Value("${tableSpaceSysIndex}")
    private String tableSpaceSysIndex;

    @Value("${tableSpaceUserTable}")
    private String tableSpaceUserTable;

    @Value("${tableSpaceUserIndex}")
    private String tableSpaceUserIndex;

    @Value("${encoding}")
    private String encoding;

    @Value("${prefixTable}")
    private String prefixTable;

    @Value("${prefixColumn}")
    private String prefixColumn;

    @Value("${dbmsType}")
    private String dbmsType;

    @Value("${user}")
    private String user;

    @Value("${owner}")
    private String owner;

    @Value("${storageTable}")
    private String storageTable;

    @Value("${storageIndex}")
    private String storageIndex;

    @Value("${defaultFolder}")
    private String defaultFolder;

    public String getNameCurrentList() {
        return nameCurrentList;
    }

    public void setNameCurrentList(String nameCurrentList) {
        this.nameCurrentList = nameCurrentList;
    }

    public Boolean getDefaultConfiguration() {
        return defaultConfiguration;
    }

    public void setDefaultConfiguration(Boolean defaultConfiguration) {
        this.defaultConfiguration = defaultConfiguration;
    }

    public String getPackageIn() {
        return packageIn;
    }

    public void setPackageIn(String packageIn) {
        this.packageIn = packageIn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTableSpaceSysTable() {
        return tableSpaceSysTable;
    }

    public void setTableSpaceSysTable(String tableSpaceSysTable) {
        this.tableSpaceSysTable = tableSpaceSysTable;
    }

    public String getTableSpaceSysIndex() {
        return tableSpaceSysIndex;
    }

    public void setTableSpaceSysIndex(String tableSpaceSysIndex) {
        this.tableSpaceSysIndex = tableSpaceSysIndex;
    }

    public String getTableSpaceUserTable() {
        return tableSpaceUserTable;
    }

    public void setTableSpaceUserTable(String tableSpaceUserTable) {
        this.tableSpaceUserTable = tableSpaceUserTable;
    }

    public String getTableSpaceUserIndex() {
        return tableSpaceUserIndex;
    }

    public void setTableSpaceUserIndex(String tableSpaceUserIndex) {
        this.tableSpaceUserIndex = tableSpaceUserIndex;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getPrefixTable() {
        return prefixTable;
    }

    public void setPrefixTable(String prefixTable) {
        this.prefixTable = prefixTable;
    }

    public String getPrefixColumn() {
        return prefixColumn;
    }

    public void setPrefixColumn(String prefixColumn) {
        this.prefixColumn = prefixColumn;
    }

    public String getDbmsType() {
        return dbmsType;
    }

    public void setDbmsType(String dbmsType) {
        this.dbmsType = dbmsType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStorageTable() {
        if (storageTable == null || storageTable.length() == 0 || storageTable.equals("null")) {
            return "  pctfree 20\n" +
                    "  initrans 2\n" +
                    "  maxtrans 255\n" +
                    "  storage\n" +
                    "  (\n" +
                    "    initial 32K\n" +
                    "    next 1M\n" +
                    "    minextents 1\n" +
                    "    maxextents unlimited\n" +
                    "  )\n";
        }
        return storageTable;
    }

    public void setStorageTable(String storageTable) {
        this.storageTable = storageTable;
    }

    public String getStorageIndex() {
        if (storageIndex == null || storageIndex.length() == 0 || storageIndex.equals("null")) {
            return "  pctfree 20\n" +
                    "  initrans 2\n" +
                    "  maxtrans 255\n" +
                    "  storage\n" +
                    "  (\n" +
                    "    initial 32K\n" +
                    "    next 1M\n" +
                    "    minextents 1\n" +
                    "    maxextents unlimited\n" +
                    "  )\n";
        }
        return storageIndex;
    }

    public void setStorageIndex(String storageIndex) {
        this.storageIndex = storageIndex;
    }

    public String getDefaultFolder() {
        return defaultFolder;
    }

    public void setDefaultFolder(String defaultFolder) {
        this.defaultFolder = defaultFolder;
    }
}

