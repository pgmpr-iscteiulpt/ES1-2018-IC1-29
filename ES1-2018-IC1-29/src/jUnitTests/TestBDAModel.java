package jUnitTests;

import javax.mail.Message;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import bda.BDATableModel;

public class TestBDAModel {

	private Message m1;
	private Message m2;
	private Message m3;
	private Message[] m;
	private BDATableModel t;

	@Before
	public void setup() throws Exception {
		m1 = null;
		m2 = null;
		m3 = null;
		m = new Message[] { m1, m2, m3 };
		t = new BDATableModel(m);
	}

	@Test
	public void testGetColumnCount() {
		Assert.assertEquals(4, t.getColumnCount());

	}

	@Test
	public void testGetRowCount() {
		Assert.assertEquals(3, t.getRowCount());

	}

	@Test
	public void testReturnColumnNames() {
		String a = "Data e Hora";
		String b = "Fonte";
		String c = "Remetente";
		String d = "Assunto";
		Assert.assertEquals(a, t.getColumnName(0));
		Assert.assertEquals(b, t.getColumnName(1));
		Assert.assertEquals(c, t.getColumnName(2));
		Assert.assertEquals(d, t.getColumnName(3));

	}

}
