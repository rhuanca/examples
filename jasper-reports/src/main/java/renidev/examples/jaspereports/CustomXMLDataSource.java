package renidev.examples.jaspereports;

import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * This is a sample custom data source for xml.
 * @author rhuanca
 *
 */
public class CustomXMLDataSource implements JRDataSource{

	private Document document;
	
	public CustomXMLDataSource(Document document) {
		this.document = document;
	}

	public Object getFieldValue(JRField jrField) throws JRException {
		return null;
	}

	public boolean next() throws JRException {
		return false;
	}

}
