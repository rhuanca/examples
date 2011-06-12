package renidev.examples.jaspereports;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRRewindableDataSource;

/**
 * This is a sample custom data source for xml.
 * @author Renan Huanca
 *
 */
public class CustomXMLDataSource implements JRDataSource, JRRewindableDataSource {

	private NodeList childNodes;
	private int index;
	
	public CustomXMLDataSource(Document document) {
		// Note: document.getFirstChild() will return the root node.
		this.childNodes = document.getFirstChild().getChildNodes();
		this.index = -1;
	}

	/**
	 * Implementation of the getFieldValue method.
	 * In this case this method is extracting the value 
	 * from the note's attributes.
	 */
	public Object getFieldValue(JRField jrField) throws JRException {
		String nodeName = jrField.getDescription();
		return childNodes.item(index).getAttributes().getNamedItem(nodeName).getTextContent();
	}

	public boolean next() throws JRException {
		++this.index;
		return this.index < childNodes.getLength();
	}

	public void moveFirst() throws JRException {
		this.index = -1;
	}
}
