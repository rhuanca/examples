package net.sumasoftware.restful.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Iterator;

/**
 * @author Renan Huanca
 * @since Nov 26, 2009 7:39:41 PM
 */
@Controller
public class AppController {
    
    private static Logger logger = Logger.getLogger(AppController.class);
    
    @RequestMapping("/ui/services/index.do")
    public String init(HttpServletRequest request) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        request.setAttribute("connections", connectionManager.getConnections());
        return "webServicesList";
    }

    @RequestMapping("/ui/services/list_connection.do")
    public String listConnection(HttpServletRequest request, @RequestParam("connectionName") String connectionName) throws SQLException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DBConnection dbConnection = connectionManager.getConnectionProperties(connectionName);

        if(dbConnection !=null){
            request.setAttribute("activeConnection", dbConnection);
            request.setAttribute("connectionItems", dbConnection.getConnectionItems());
        }
        return init(request);
    }

    @RequestMapping("/ui/services/table_detail.do")
    public String tableDetail(HttpServletRequest request) throws SQLException {
        String connectionName = request.getParameter("connectionName");
        String tableName = request.getParameter("tableName");
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DBConnection dbConnection = connectionManager.getConnectionProperties(connectionName);

        TableDetail tableDetail = dbConnection.getTableColumns(tableName);

        request.setAttribute("tableDetail", tableDetail);
        return "tableDetail";
    }

}
