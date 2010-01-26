package net.sumasoftware.ws;

import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.springframework.ws.server.endpoint.AbstractDomPayloadEndpoint;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.jdom.xpath.XPath;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;


/**
 * @author Renan Huanca
 * @since Dec 21, 2009 10:55:17 PM
 */                                   
public class DataSetEndPoint extends AbstractDomPayloadEndpoint {

    private static Logger logger = Logger.getLogger(DataSetEndPoint.class);


    protected Element invokeInternal(Element element, Document document) throws Exception {


        System.out.println(">>> entro.....");
//        Element responseElement = document.createElementNS("http://mycompany.com/hr/schemas", "response");
//        Element responseElement = document.createElementNS("http://samples", "response");

//        XMLOutputter out = new XMLOutputter();
//        System.out.println("Given XML:");
//        out.output(element, System.out);
        XPath connectionExpression = XPath.newInstance("//connection");
        XPath tablenameExpression = XPath.newInstance("//tablename");

//        String connection = connectionExpression.valueOf(element);
//        String tablename = tablenameExpression.valueOf(element);
        String connection = "conn0";
        String tablename = "ventas2";

        DataRetriever dataRetriever = new DataRetriever();

        System.out.println(">>> antes de obtener datos...." );
        String xml = dataRetriever.retrieve(connection,
                tablename,
                null,
                "data",
                new HashMap(),
                new ArrayList());

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document1 = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));

        return document1.getDocumentElement();
    }

    public void serialize(Document doc, OutputStream out) throws Exception {

        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer serializer;
        try {
            serializer = tfactory.newTransformer();
            //Setup indenting to "pretty print"
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            serializer.transform(new DOMSource(doc), new StreamResult(out));
        } catch (TransformerException e) {
            // this is fatal, just dump the stack and throw a runtime exception
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }



}
