package renidev.examples.jasperreports;

import static org.junit.Assert.*;
import org.junit.Test;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.design.JRDesignField;
import renidev.examples.jaspereports.CustomXMLDataSource;


public class CustomXMLDataSourceTest {
	
	@Test
	public void single_case() throws JRException{
		String xml ="<root></root>";
		CustomXMLDataSource dataSource = new CustomXMLDataSource(SimpleDocumentBuilder.buildDocument(xml));
		assertFalse(dataSource.next());
	}
	
	@Test
	public void one_child_node() throws JRException{
		String xml ="<friends><friend name='carlos'/></friends>";
		CustomXMLDataSource dataSource = new CustomXMLDataSource(SimpleDocumentBuilder.buildDocument(xml));
		assertTrue(dataSource.next());
		assertEquals("carlos", dataSource.getFieldValue(createField("name")));
		assertFalse(dataSource.next());
	}
	
	@Test
	public void two_child_nodes() throws JRException{
		String xml =
				"<friends>" +
					"<friend name='carlos'/>" +
					"<friend name='gonzalo'/>" +
				"</friends>";
		CustomXMLDataSource dataSource = new CustomXMLDataSource(SimpleDocumentBuilder.buildDocument(xml));
		JRField nameField = createField("name");
		
		// reading data data
		assertTrue(dataSource.next());
		assertEquals("carlos", dataSource.getFieldValue(nameField));
		assertTrue(dataSource.next());
		assertEquals("gonzalo", dataSource.getFieldValue(nameField));
		
		// checking there is no more data 
		assertFalse(dataSource.next());
	}
	
	@Test
	public void two_child_nodes_and_two_attributes() throws JRException{
		String xml =
				"<friends>" +
					"<friend name='carlos' from='work'/>" +
					"<friend name='gonzalo' from='raquet'/>" +
				"</friends>";
		CustomXMLDataSource dataSource = new CustomXMLDataSource(SimpleDocumentBuilder.buildDocument(xml));
		JRField nameField = createField("name");
		JRField fromField = createField("from");
		
		// reading data data
		assertTrue(dataSource.next());
		assertEquals("carlos", dataSource.getFieldValue(nameField));
		assertEquals("work", dataSource.getFieldValue(fromField));
		assertTrue(dataSource.next());
		assertEquals("gonzalo", dataSource.getFieldValue(nameField));
		assertEquals("raquet", dataSource.getFieldValue(fromField));
		
		// checking there is no more data 
		assertFalse(dataSource.next());
	}

	
	@Test
	public void two_child_nodes_and_rewind() throws JRException{
		String xml =
				"<friends>" +
					"<friend name='carlos'/>" +
					"<friend name='gonzalo'/>" +
				"</friends>";
		
		CustomXMLDataSource dataSource = new CustomXMLDataSource(SimpleDocumentBuilder.buildDocument(xml));
		JRField nameField = createField("name");
		
		// bypassing two nodes
		assertTrue(dataSource.next());
		assertTrue(dataSource.next());
		
		// checking there is no more data 
		assertFalse(dataSource.next());
		
		// rewind the data source
		dataSource.moveFirst();
		
		// reading data data
		assertTrue(dataSource.next());
		assertEquals("carlos", dataSource.getFieldValue(nameField));
		assertTrue(dataSource.next());
		assertEquals("gonzalo", dataSource.getFieldValue(nameField));
	}
	
	public JRField createField(String description){
		JRDesignField field = new JRDesignField();
		field.setDescription(description);
		return field;
	}
}
