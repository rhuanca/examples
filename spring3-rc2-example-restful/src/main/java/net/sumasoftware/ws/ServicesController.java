package net.sumasoftware.ws;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.*;
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

        DataRetriever dataRetriever = new DataRetriever();
        response.setContentType("text/xml");
        String xml = dataRetriever.retrieve(connectionName,
                tableName,
                request.getParameter("columns"),
                request.getParameter("dataSetName"),
                requestToMap(request),
                getFilterNames(request));
        response.getWriter().print(xml);

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

    private Map requestToMap(HttpServletRequest request){
        HashMap hashMap = new HashMap();

        Enumeration parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String parameterName = (String) parameterNames.nextElement();
            hashMap.put(parameterName, requestToMap(request));
        }
        return hashMap;
    }


}
