package net.sumasoftware.ws;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.apache.log4j.Logger;

import javax.xml.xpath.*;


/**
 * @author Renan Huanca
 * @since Jan 7, 2010 4:39:52 PM
 */
public class XPathReader {

    private static Logger logger = Logger.getLogger(XPathReader.class);

    Document document;
    XPath xpath;

    public XPathReader(Document document){
        XPathFactory factory = XPathFactory.newInstance();
        this.document = document;
        this.xpath = factory.newXPath();
    }

    /**
     * Reads and expression assuming that it returns an empty node.
     * @param xpathExpr
     * @return
     */
    public String readNotEmptyNode(String xpathExpr){
        Object result = null;
        try {
            result = this.xpath.evaluate(xpathExpr, this.document);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Unable to evaluate Expression: '" + xpathExpr +"'",e);
        }
        if(!(result instanceof String)){
            throw new RuntimeException("Invalid returned expression's value. result class is instance of " + result.getClass());
        }
        return (String) result;
    }

    /**
     * Reads and expression assuming that it returns an not ampty node and convert its to int data type
     * @param xpathExpr
     * @return
     */
    public int readInteger(String xpathExpr){
        String value = readNotEmptyNode(xpathExpr);

        int i = 0;
        try {
            i = Integer.parseInt(value);
        } catch (NumberFormatException e){
            logger.warn("Unable to parse in for the given expression: " +xpathExpr);
        }

        return i;
    }

    /**
     * Reads and expression assuming that it returns an empty node.
     * @param xpathExpr
     * @return
     */
    public String[] readNodesTextConent(String xpathExpr){
         NodeList result = null;
        try {
            result = (NodeList) this.xpath.evaluate(xpathExpr, this.document, XPathConstants.NODESET);

        } catch (XPathExpressionException e) {
            throw new RuntimeException("Unable to evaluate Expression: '" + xpathExpr +"'",e);
        }
        String[] values = new String[result.getLength()];

        for (int i = 0; i < result.getLength(); i++) {
            Node node = result.item(i);
            values[i] = node.getTextContent();
        }
        return values;
    }
    
}
