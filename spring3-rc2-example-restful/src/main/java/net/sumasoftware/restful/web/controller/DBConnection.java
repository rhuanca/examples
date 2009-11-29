package net.sumasoftware.restful.web.controller;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

/**
 * @author Renan Huanca
 * @since Nov 25, 2009 4:07:01 PM
 */
public class DBConnection {
    private static Logger logger = Logger.getLogger(DBConnection.class);

    String name;
    String description;
    String driverClassName;
    String url;
    String username;
    String password;

    DataSource dataSource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DataSource getDataSource() {
        if(this.dataSource==null){
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(this.getDriverClassName());
            basicDataSource.setUsername(this.getUsername());
            basicDataSource.setPassword(this.getPassword());
            basicDataSource.setUrl(this.getUrl());
            this.dataSource = basicDataSource;
        }
        return this.dataSource;
    }

    public List getTableNames() throws SQLException {
        Connection connection = this.getDataSource().getConnection();
        List tableNames = new ArrayList();
        ResultSet tables = connection.getMetaData().getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});
        while(tables.next()){
            String tableName = tables.getString(3);
            tableNames.add(tableName);
        }
        return tableNames;
    }

    public List getConnectionItems() throws SQLException {
        ApplicationConfiguration instance = ApplicationConfiguration.getInstance();

        List connectionItems = new ArrayList();
        for (Iterator i = getTableNames().iterator(); i.hasNext();) {
            String tableName = (String) i.next();
            DBConnectionItem connectionItem = new DBConnectionItem();
            connectionItem.setTableName(tableName);
            connectionItem.setWsUrl(instance.getBasePath()+"/services/"+this.getName()+"/"+tableName);
            connectionItems.add(connectionItem);
        }
        return connectionItems;
    }

    public TableDetail getTableColumns(String tableName) throws SQLException {

        TableDetail tableDetail = new TableDetail();
        tableDetail.setConnectionName(this.getName());
        tableDetail.setConnectionDescription(this.getDescription());
        tableDetail.setName(tableName);
        List tableColumns = new ArrayList();

        Connection connection = this.getDataSource().getConnection();
        ResultSet columns = connection.getMetaData().getColumns(connection.getCatalog(), null, tableName, null);
        // 4 = columna name, 5 = type, 6 = type name

        //DBUtils.printRestulSet(columns);

        while(columns.next()){
            String columnName = columns.getString(4);
            String columnType = columns.getString(6);
            TableColumn tableColumn = new TableColumn();
            tableColumn.setName(columnName);
            tableColumn.setType(columnType);
            logger.info(">>> tableColumn.getType() = " + tableColumn.getType());
            tableColumns.add(tableColumn);
        }

        tableDetail.setTableColumns(tableColumns);
        
        return tableDetail;
    }
}
