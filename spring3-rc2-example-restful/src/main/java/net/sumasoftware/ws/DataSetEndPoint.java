package net.sumasoftware.ws;

import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.apache.log4j.Logger;


/**
 * @author Renan Huanca
 * @since Dec 21, 2009 10:55:17 PM
 */
public class DataSetEndPoint extends AbstractJDomPayloadEndpoint {

    private static Logger logger = Logger.getLogger(DataSetEndPoint.class);

    protected Element invokeInternal(Element element) throws Exception {
        Namespace namespace = Namespace.getNamespace("bis", "http://localhost/bis/schemas");
        logger.info(">>> 1- element = " + element.toString());
        XPath connectionExpression = XPath.newInstance("//bis:connection");
        XPath tablenameExpression = XPath.newInstance("//bis:tablename");
        connectionExpression.addNamespace(namespace);
        tablenameExpression.addNamespace(namespace);

        logger.info(">>> antes de realizar calculo...");

        String connection = connectionExpression.valueOf(element);
        String tablename = tablenameExpression.valueOf(element);

        System.out.println(">>> tablename = " + tablename);
        System.out.println(">>> connection = " + connection);

        Element element2 = new Element("table");
        element2.setNamespace(namespace);

        Element row = new Element("row");
        row.setNamespace(namespace);

        element2.addContent(row);
        return element2;
    }
}
