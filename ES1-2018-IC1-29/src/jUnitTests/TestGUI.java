package jUnitTests;


import javax.swing.JTable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bda.GUI;

public class TestGUI {

	private GUI g;
	private String notL;
	private JTable inboxTable;
	
	@Before
	public void setUp() throws Exception {
		notL = "Not Logged In   ";
		g = new GUI();
		g.open();
	}

	@Test
	public void testGetLog() {
		Assert.assertEquals(g.getLog(1).getText(), notL);
		Assert.assertEquals(g.getLog(2).getText(), notL);
		Assert.assertEquals(g.getLog(3).getText(), notL);
		Assert.assertEquals(g.getLog(4), null);	
	}
	
	@Test
	public void testGetInboxTable() {
		Assert.assertNotEquals(g.getInboxTable(),inboxTable);
	}
	
}
