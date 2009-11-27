package net.sumasoftware.restful.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

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
        request.setAttribute("connectionNames", connectionManager.getConnectionNames());
        return "webServicesList";
    }

    @RequestMapping("/ui/services/list_connection.do")
    public String listConnection(HttpServletRequest request, @RequestParam("connectionName") String connectionName) throws SQLException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DBConnection dbConnection = connectionManager.getConnectionProperties(connectionName);

        if(dbConnection !=null){
            request.setAttribute("connectionName", connectionName);
            request.setAttribute("connectionItems", dbConnection.getConnectionItems());
        }
        return init(request);
    }
}
