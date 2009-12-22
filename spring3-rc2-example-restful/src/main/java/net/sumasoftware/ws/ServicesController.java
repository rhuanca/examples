package net.sumasoftware.ws;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Enumeration;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author Renan Huanca
 * @since Nov 22, 2009  10:54:31 AM
 */

@Controller
public class ServicesController {
    private static Logger logger = Logger.getLogger(ServicesController.class);

    @RequestMapping("/{connectionName}/{tableName}")
    public void readTable(@PathVariable String connectionName,
                          @PathVariable String tableName,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException, SQLException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DBConnection dbConnection = connectionManager.getConnectionProperties(connectionName);
        DataSource dataSource = DataSourceFactory.getDataSource(dbConnection);
        response.setContentType("text/xml");
        Connection connection = dataSource.getConnection();
        TableDetail tableDetail = dbConnection.getTableDetail(tableName);

        Statement statement = connection.createStatement();
        String columns = request.getParameter("columns");
        String dataSetName = request.getParameter("dataSetName");
        if(StringUtils.isEmpty(dataSetName)){
            dataSetName = "data";
        }
        String sql = buildQuery(request, tableName, columns, getFilterNames(request));
        logger.info("sql: " + sql);
        ResultSet resultSet = statement.executeQuery(sql);
        PrintWriter writer = response.getWriter();
        writer.println("<data>");
        writer.println("<variable name=\""+dataSetName+"\">");
//        writer.print("<tableName>");
//        writer.print(tableName);
//        writer.print("</tableName>");
//        writer.print("<rows>");
        while(resultSet.next()){
            writer.println("<row>");
            for (Iterator i = tableDetail.getTableColumns().iterator(); i.hasNext();) {
                TableColumn tableColumn = (TableColumn) i.next();
                String columnName = tableColumn.getName();
                String columnType = tableColumn.getType();
//                writer.print("<"+columnName+">");
                writer.print("<column>");
                if(columnType.contains("VARCHAR")) {
                    writer.print(resultSet.getString(columnName));
                } else if(columnType.contains("DATETIME")) {
                    if(resultSet.getTimestamp (columnName)!=null){
//                        writer.print(resultSet.getTimestamp (columnName));
                        writer.print(resultSet.getObject(columnName));
                    }
                } else {
                    writer.print(resultSet.getObject(columnName));
                }

                writer.println("</column>");
            }
            writer.println("</row>");
        }
//        writer.print("</rows>");
        writer.println("</variable>");
        writer.println("</data>");
        connection.close();
    }

    private List getFilterNames(HttpServletRequest request) {
        Enumeration attributeNames = request.getParameterNames();
        List filterNames = new ArrayList();
        while(attributeNames.hasMoreElements()){
            String attributename = (String)attributeNames.nextElement();
            if(!"columns".equals(attributename)){
                filterNames.add(attributename);
            }
        }
        return filterNames;
    }

    private String buildQuery(HttpServletRequest request, String tableName, String columns, List filterNames){
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
                where += columnName + " = '" + request.getParameter(columnName) + "'";
                if(i.hasNext()) {
                    where += " and ";
                }
            }
        }

        return "select "+selectedColumns + " from "+ tableName + (StringUtils.isNotEmpty(where)? " where " + where:"");
    }
}
