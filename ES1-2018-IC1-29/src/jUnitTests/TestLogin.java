package jUnitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bda.BDAButton;
import bda.GUI;
import bda.Login;

public class TestLogin {

	private Login l;
	private GUI g;
	private BDAButton j;
	private String s;
	
	@Before
	public void setup() throws Exception {
		g = new GUI();
		s = "Email";
		j = new BDAButton(g, s);
		l = new Login(g,j);	
	}	
	
	
	@Test
	public void testState() {
		l.open();
		Assert.assertNotEquals(j.getState(), true);
		Assert.assertEquals(l.getUsername(), "");
		Assert.assertEquals(l.getPassword(), "");
	}
	
	
}
