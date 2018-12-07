package jUnitTests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.mail.MessagingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import bda.BDATableModel;
import bda.Content;


public class TestBDATableModel {
	
	private Content c1;
	private String columnName;
	private ArrayList<Content> l;
	private ArrayList<Object> s;
	private BDATableModel t;

	
	@Before
	public void setUp() throws Exception {
		l = new ArrayList<Content>();
		c1 = new Content(new File(System.getProperty("user.dir") + File.separator + "Resources\\ContentTest"));
		columnName = "Fonte";
		l.add(c1);
		t = new BDATableModel(l);
		s = new ArrayList<>();
	}

	@Test
	public void testBDATableModel() {
		Assert.assertNotEquals(new BDATableModel(l), null);;
	}


	@Test
	public void testGetColumnCount() {
		Assert.assertEquals(4, t.getColumnCount());
	}


	@Test
	public void testGetRowCount() {
		Assert.assertEquals(1, t.getRowCount());
	}


	@Test
	public void testGetValueAt() throws MessagingException, IOException {
		t.getValueAt(0, 0);
		Assert.assertEquals(c1.getDate(), "Fri Nov 30 07:36:21 GMT 2018");
		t.getValueAt(0, 1);
		Assert.assertEquals(c1.getType(), "Email");
		t.getValueAt(0, 2);
		Assert.assertEquals(c1.getFrom(), "notification@facebookmail.com");
		t.getValueAt(0, 3);
		Assert.assertEquals(c1.getSubject(), "Grupovinte, tens 1 notificação nova");
	}


	@Test
	public void testGetMessageAt() throws IOException, MessagingException {
		Assert.assertEquals(t.getMessageAt(0), c1);
	}


	@Test
	public void testGetColumnNameInt() {
		Assert.assertEquals(columnName, t.getColumnName(1));
	}


	@Test
	public void testGetTableContent() {
		Assert.assertEquals(l, t.getTableContent());
	}


	@Test
	public void testGetContentHandlers() {
		Assert.assertEquals(s, t.getContentHandlers());
	}

}
