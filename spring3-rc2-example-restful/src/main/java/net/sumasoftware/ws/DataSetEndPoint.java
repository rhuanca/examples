package net.sumasoftware.ws;

import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @author Renan Huanca
 * @since Dec 21, 2009 10:55:17 PM
 */
public class DataSetEndPoint extends AbstractJDomPayloadEndpoint {

    private static Logger logger = Logger.getLogger(DataSetEndPoint.class);

    protected Element invokeInternal(Element element) throws Exception {
        XMLOutputter out = new XMLOutputter();
        System.out.println("Given XML:");
        out.output(element, System.out);
        XPath connectionExpression = XPath.newInstance("//connection");
        XPath tablenameExpression = XPath.newInstance("//tablename");
        String connection = connectionExpression.valueOf(element);
        String tablename = tablenameExpression.valueOf(element);

        DataRetriever dataRetriever = new DataRetriever();

        System.out.println(">>> antes de obtener datos...." );
        String xml = dataRetriever.retrieve(connection,
                tablename,
                null,
                "data",
                new HashMap(),
                new ArrayList());


        System.out.println(">>> xml = " + xml);

        System.out.println(">>> tablename = " + tablename);
        System.out.println(">>> connection = " + connection);

        Element element2 = new Element("table");
//        element2.setNamespace(namespace);

        Element row = new Element("row");
//        row.setNamespace(namespace);

        element2.addContent(row);
        return element2;
    }


}
