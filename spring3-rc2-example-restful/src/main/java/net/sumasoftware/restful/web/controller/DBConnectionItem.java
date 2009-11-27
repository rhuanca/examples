package net.sumasoftware.restful.web.controller;

/**
 * @author Renan Huanca
 * @since Nov 26, 2009 9:16:56 PM
 */
public class DBConnectionItem {
    String tableName;
    String wsUrl;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }
}
