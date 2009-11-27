package net.sumasoftware.restful.web.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
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
                          HttpServletResponse response) throws IOException, SQLException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DBConnection dbConnection = connectionManager.getConnectionProperties(connectionName);
        DataSource dataSource = dbConnection.getDataSource();
        response.setContentType("text/xml");
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + tableName);
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

}
