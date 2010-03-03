package net.sumasoftware.ws;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Renan Huanca
 * @since Jan 23, 2010 2:55:21 PM
 */
public class DataRetriever {
    private static Logger logger = Logger.getLogger(DataRetriever.class);

    public String retrieve(String connectionName, String tableName, String columns, String dataSetName, Map parameters, List filterNames) throws SQLException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DBConnection dbConnection = connectionManager.getConnectionProperties(connectionName);
        DataSource dataSource = DataSourceFactory.getDataSource(dbConnection);
        StringWriter swriter = new StringWriter();
        PrintWriter writer = new PrintWriter(swriter);

        Connection connection = dataSource.getConnection();
        TableDetail tableDetail = dbConnection.getTableDetail(tableName);

        Statement statement = connection.createStatement();

        if (StringUtils.isEmpty(dataSetName)) {
            dataSetName = "data";
        }
        String sql = buildQuery(tableName, columns, filterNames, parameters);
        System.out.println(">>> sql = " + sql);
        ResultSet resultSet = statement.executeQuery(sql);

//        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<data>");
        writer.println("<variable name=\"" + dataSetName + "\">");
        while (resultSet.next()) {
            writer.println("<row>");
            for (Iterator i = tableDetail.getTableColumns().iterator(); i.hasNext();) {
                TableColumn tableColumn = (TableColumn) i.next();
                String columnName = tableColumn.getName();
                String columnType = tableColumn.getType();
                writer.print("<column>");
                if (columnType.contains("VARCHAR")) {
                    writer.print(resultSet.getString(columnName));
                } else if (columnType.contains("DATETIME")) {
                    if (resultSet.getTimestamp(columnName) != null) {
                        writer.print(resultSet.getObject(columnName));
                    }
                } else {
                    writer.print(resultSet.getObject(columnName));
                }

                writer.println("</column>");
            }
            writer.println("</row>");
        }
        writer.println("</variable>");
        writer.println("</data>");
        connection.close();
        
        return swriter.toString();
    }

    public String retrieve2(String connectionName, String tableName, String columns, String dataSetName, Map parameters, List filterNames) throws SQLException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DBConnection dbConnection = connectionManager.getConnectionProperties(connectionName);
        DataSource dataSource = DataSourceFactory.getDataSource(dbConnection);
        StringWriter swriter = new StringWriter();
        PrintWriter writer = new PrintWriter(swriter);

        Connection connection = dataSource.getConnection();
        TableDetail tableDetail = dbConnection.getTableDetail(tableName);

        Statement statement = connection.createStatement();

        if (StringUtils.isEmpty(dataSetName)) {
            dataSetName = "data";
        }
        String sql = buildQuery(tableName, columns, filterNames, parameters);
        ResultSet resultSet = statement.executeQuery(sql);
        

//        writer.println("<ventas2Response>");
//        writer.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        writer.print("<BIResponse>");
        writer.print("<ventas2Result>");
        writer.print("<ventas2Return>");
        writer.print("<Success>true</Success>");
        writer.print("<Message>Tutto aposto</Message>");
        writer.print("<Records>");
        while (resultSet.next()) {
            writer.print("<ventas2Row>");
            for (Iterator i = tableDetail.getTableColumns().iterator(); i.hasNext();) {
                TableColumn tableColumn = (TableColumn) i.next();

                String columnName = StringUtils.capitalize(tableColumn.getName());
                System.out.println(">>> columnName = " + columnName);
                String columnType = tableColumn.getType();
                writer.print("<"+columnName+">");
                if (columnType.contains("VARCHAR")) {
                    writer.print(resultSet.getString(columnName));
                } else if (columnType.contains("DATETIME")) {
                    if (resultSet.getTimestamp(columnName) != null) {
                        writer.print(resultSet.getObject(columnName));
                    }
                } else {
                    writer.print(resultSet.getObject(columnName));
                }

                writer.print("</"+columnName+">");
            }
            writer.print("</ventas2Row>");
        }
//        writer.println("</variable>");
        writer.print("</Records>");
        writer.print("</ventas2Return>");
        writer.print("</ventas2Result>");
        writer.print("</BIResponse>");
//        writer.println("</ventas2Response>");
        connection.close();

        return swriter.toString();
    }

    private String buildQuery(String tableName, String columns, List filterNames, Map parameters){
        // build select
        String selectedColumns = "*";
        if(StringUtils.isNotEmpty(columns) && columns.indexOf(",")!=-1){
            String[] columnNames = columns.split(",");
            selectedColumns = "";
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i];
                selectedColumns += columnName;
                if (i<columnNames.length-1) {
                    selectedColumns+=", ";
                }
            }
        }

        // build where
        String where = "";
        for (Iterator i = filterNames.iterator(); i.hasNext();) {
            String columnName = (String) i.next();
            if(!columnName.equalsIgnoreCase("dataSetName")){
                where += columnName + " = '" + parameters.get(columnName) + "'";
                if(i.hasNext()) {
                    where += " and ";
                }
            }
        }

        return "select "+selectedColumns + " from "+ tableName + (StringUtils.isNotEmpty(where)? " where " + where:"");
    }
}
