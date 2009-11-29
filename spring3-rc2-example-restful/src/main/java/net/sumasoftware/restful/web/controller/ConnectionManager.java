package net.sumasoftware.restful.web.controller;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author rhuanca
 * @since Nov 25, 2009 3:58:37 PM
 */
public class ConnectionManager {
    private static Logger logger = Logger.getLogger(ConnectionManager.class);
    static ConnectionManager instance = null;

    TreeMap connections = new TreeMap();

    public void addConnectionProperties(DBConnection dbConnection){
        connections.put(dbConnection.getName(), dbConnection);

    }

    public DBConnection getConnectionProperties(String name){
        return (DBConnection) connections.get(name);
    }

    public Collection getConnections() {
        return connections.values();
    }

    public List getConnectionNames(){
        return new ArrayList(connections.keySet());   
    }

    public static void loadConnectionProperties(ApplicationConfiguration configuration){
        ConnectionManager connectionManager = new ConnectionManager();
        // get connection properties
        int i = 0;
        while(configuration.getConfiguration().getString("jdbc_connection."+i+".driverClassName")!=null) {
            connectionManager.addConnectionProperties(buildConnectionProperties(configuration.getConfiguration(), i));
            i++;
        }
        logger.info("Found database connections: " + i);
        instance = connectionManager;
    }

    private static DBConnection buildConnectionProperties(Configuration configuration, int index){
        DBConnection dbConnection = new DBConnection();
        String connectionName = configuration.getString("jdbc_connection." + index + ".name");
        dbConnection.setName(connectionName);
        dbConnection.setDescription(configuration.getString("jdbc_connection."+index+".description"));
        logger.info("Found connection: " + connectionName);
        dbConnection.setDriverClassName(configuration.getString("jdbc_connection."+index+".driverClassName"));
        dbConnection.setUrl(configuration.getString("jdbc_connection."+index+".url"));
        dbConnection.setUsername(configuration.getString("jdbc_connection."+index+".username"));
        dbConnection.setPassword(configuration.getString("jdbc_connection."+index+".password"));
        return dbConnection;
    }

    public static ConnectionManager getInstance(){
        if(instance == null){
            throw new RuntimeException("Connection Manager was not initialized.");
        }
        return instance;
    }


}
