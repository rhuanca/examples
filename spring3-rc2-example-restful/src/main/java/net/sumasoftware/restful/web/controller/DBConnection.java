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
        logger.info(">>> getTableNames().size() = " + getTableNames().size());
        for (Iterator i = getTableNames().iterator(); i.hasNext();) {
            String tableName = (String) i.next();
            logger.info(">>> tableName = " + tableName);
            DBConnectionItem connectionItem = new DBConnectionItem();
            connectionItem.setTableName(tableName);
            connectionItem.setWsUrl(instance.getBasePath()+"/services/"+this.getName()+"/"+tableName);
            connectionItems.add(connectionItem);
        }
        return connectionItems;
    }
}
