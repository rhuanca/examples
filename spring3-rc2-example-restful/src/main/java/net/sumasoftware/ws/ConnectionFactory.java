package net.sumasoftware.ws;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Renan Huanca
 * @since Nov 30, 2009 7:39:21 AM
 */
public class ConnectionFactory {
    private static Logger logger = Logger.getLogger(ConnectionFactory.class);

    static Map connections =  new HashMap();

    public static DataSource getDataSource(DBConnection dbConnection){
        BasicDataSource dataSource = (BasicDataSource) connections.get(dbConnection.getName());
        if(dataSource == null){
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(dbConnection.getDriverClassName());
            dataSource.setUsername(dbConnection.getUsername());
            dataSource.setPassword(dbConnection.getPassword());
            dataSource.setUrl(dbConnection.getUrl());
            connections.put(dbConnection.getName(), dataSource);
        }
        return dataSource;
    }
}
