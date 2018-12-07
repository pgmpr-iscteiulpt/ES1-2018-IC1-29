package jUnitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bda.BDAButton;
import bda.GUI;

public class TestBDAButton {

	private String s;
	private String n;
	private GUI g;
	private BDAButton b1;
	private BDAButton b2;
	private BDAButton b3;
	

	@Before
	public void setUp() throws Exception {
		g = new GUI();
		s = "";
		n = "email";
		b1 = new BDAButton(g, s);
		b2 = new BDAButton(g, s);
		b3 = new BDAButton(g, n);	
	}

	@Test
	public void testChangeImage() {
		b1.changeImage();
		b1.getIconName().equals("emailOff");
	}
	
	
	@Test
	public void testChangeState() {
		b1.changeState();
		Assert.assertNotEquals(b1.getState(), b2.getState());

		b2.changeState();
		Assert.assertEquals(b1.getState(), b2.getState());

	}

	@Test
	public void testgetIconName() {
		Assert.assertNotEquals(b1.getIconName() , b3.getIconName());
	}

}
