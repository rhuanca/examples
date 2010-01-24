package net.sumasoftware.ws;

import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.Namespace;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.io.ByteArrayInputStream;


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

//        Element element1 = new Element(xml);

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new ByteArrayInputStream(xml.getBytes()));

        System.out.println(">>> XML Output:");
        out.output(document.getRootElement(), System.out);
        return document.getRootElement().setNamespace(Namespace.getNamespace("http://mycompany.com/hr/schemas"));
    }
}
