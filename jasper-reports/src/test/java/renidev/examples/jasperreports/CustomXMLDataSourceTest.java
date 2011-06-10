package renidev.examples.jasperreports;

import static org.junit.Assert.*;
import org.junit.Test;
import net.sf.jasperreports.engine.JRException;
import renidev.examples.jaspereports.CustomXMLDataSource;

public class CustomXMLDataSourceTest {
	
	@Test
	public void single_case() throws JRException{
		String xml ="<root></root>";
		CustomXMLDataSource dataSource = new CustomXMLDataSource(SimpleDocumentBuilder.buildDocument(xml));
		assertFalse(dataSource.next());
	}
}
