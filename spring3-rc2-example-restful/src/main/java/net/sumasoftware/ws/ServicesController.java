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
        DataSource dataSource = ConnectionFactory.getDataSource(dbConnection);
        response.setContentType("text/xml");
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        String columns = request.getParameter("columns");
        String sql = buildQuery(request, tableName, columns, getFilterNames(request));
        logger.info("sql: " + sql);
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData metaData = resultSet.getMetaData();
        PrintWriter writer = response.getWriter();
        writer.print("<result>");
        writer.print("<tableName>");
        writer.print(tableName);
        writer.print("</tableName>");
        writer.print("<rows>");
        while(resultSet.next()){
            writer.print("<row>");
            for(int i=0; i<metaData.getColumnCount();i++){
                writer.print("<"+metaData.getColumnName(i+1)+">");
                writer.print(resultSet.getString(i+1));
                writer.print("</"+metaData.getColumnName(i+1)+">");
            }
            writer.print("</row>");
        }
        writer.print("</rows>");
        writer.print("</result>");
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
            where += columnName + " = '" + request.getParameter(columnName) + "'";
            if(i.hasNext()) {
                where += " and ";
            }
        }

        return "select "+selectedColumns + " from "+ tableName + (StringUtils.isNotEmpty(where)? " where " + where:"");
    }
}
