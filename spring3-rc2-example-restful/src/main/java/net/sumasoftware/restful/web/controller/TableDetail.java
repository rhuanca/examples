package net.sumasoftware.restful.web.controller;

import java.util.List;

/**
 * @author Renan Huanca
 * @since Nov 28, 2009 5:10:53 PM
 */
public class TableDetail {
    String name;
    String connectionName;
    String connectionDescription;
    List tableColumns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnectionDescription() {
        return connectionDescription;
    }

    public void setConnectionDescription(String connectionDescription) {
        this.connectionDescription = connectionDescription;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public List getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List tableColumns) {
        this.tableColumns = tableColumns;
    }
}
