package net.sumasoftware.ws;

import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;
import org.springframework.ws.server.endpoint.AbstractDomPayloadEndpoint;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
//import org.jdom.xpath.XPath;

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
public class DataSetEndPoint extends AbstractDomPayloadEndpoint  {

    private static Logger logger = Logger.getLogger(DataSetEndPoint.class);


    /*protected Element invokeInternal(Element element, Document document) throws Exception {
        try {
            XPathReader reader = new XPathReader(element);

            System.out.println("Invoking web service....");
            System.out.println(">>> element = " + element.getTextContent());

            XMLUtils.serialize(element.getOwnerDocument(), System.out);
            XMLUtils.serialize(document, System.out);

            String connection = "conn0"; //reader.readNotEmptyNode("//connection");
            String tablename = "ventas2"; //reader.readNotEmptyNode("//tablename");

            DataRetriever dataRetriever = new DataRetriever();

            String xml = dataRetriever.retrieve2(connection,
                    tablename,
                    null,
                    "data",
                    new HashMap(),
                    new ArrayList());

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document response = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
            response.createAttributeNS("http://localhost/bis/ws/myws", "myws");
            return response.getDocumentElement();
        }  catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }*/

    protected Element invokeInternal(
            Element requestElement,
            Document document) throws Exception {
        String requestText = requestElement.getTextContent();
        Random r = new Random();

        String[] messes = new String[]{"enero","febrero","marzo","abril","mayo","junio"};
        Element element = document.createElementNS("http://localhost/bis/ws/myws", "BIResponse");



        Element ventas2Result = document.createElement("BIResult");
        Element ventas2Return = document.createElement("BIReturn");



        Element success = document.createElement("Success");
        success.setTextContent("true");
        Element message = document.createElement("Message");
        message.setTextContent("Haber que pasa ahora");

        ventas2Return.appendChild(success);
        ventas2Return.appendChild(message);
        Element records = document.createElement("Records");
        for (int i = 0; i < messes.length; i++) {
            Element ventas2Row = document.createElement("BIRow");
            String mess = messes[i];
            Element mes = document.createElement("Mes");
            mes.setTextContent("enero");
            Element monto = document.createElement("Monto");
            monto.setTextContent(""+(r.nextInt(1000)+2000));
            ventas2Row.appendChild(mes);
            ventas2Row.appendChild(monto);
            records.appendChild(ventas2Row);
        }

        ventas2Return.appendChild(records);


        ventas2Result.appendChild(ventas2Return);

        element.appendChild(ventas2Result);


        return element;
    }




}
