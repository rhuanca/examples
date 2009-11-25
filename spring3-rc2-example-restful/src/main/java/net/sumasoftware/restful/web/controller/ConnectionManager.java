package net.sumasoftware.restful.web.controller;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;

/**
 * @author rhuanca
 * @since Nov 25, 2009 3:58:37 PM
 */
public class ConnectionManager {
    private static Logger logger = Logger.getLogger(ConnectionManager.class);

    List connections = new ArrayList();

    public void addConnectionProperties(ConnectionProperties connectionProperties){
        connections.add(connectionProperties);
    }

    public ConnectionProperties getConnectionProperties(int index){
        return (ConnectionProperties) connections.get(index);
    }

    public int getNumberOfConnections(){
        return connections.size();
    }

    public static void loadConnectionProperties(Configuration configuration){
        ConnectionManager connectionManager = new ConnectionManager();
        // get connection properties
        int i = 0;
        while(configuration.getString("jdbc_connection."+i+".driverClassName")!=null) {
            connectionManager.addConnectionProperties(buildConnectionProperties(configuration, i));
            i++;
        }
        logger.info("Found connections: " + i);
    }

    private static ConnectionProperties buildConnectionProperties(Configuration configuration, int index){
        ConnectionProperties connectionProperties = new ConnectionProperties();
        connectionProperties.setDriverClassName(configuration.getString("jdbc_connection."+index+".driverClassName"));
        connectionProperties.setUrl(configuration.getString("jdbc_connection."+index+".url"));
        connectionProperties.setUsername(configuration.getString("jdbc_connection."+index+".username"));
        connectionProperties.setPassword(configuration.getString("jdbc_connection."+index+".password"));
        return connectionProperties;
    }
}
