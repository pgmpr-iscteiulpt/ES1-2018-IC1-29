package jUnitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bda.BDAButton;
import bda.GUI;

public class TestBDAButton {

	private BDAButton b1;
	private BDAButton b2;
	private GUI g;
	private String s;

	@Before
	public void setup() throws Exception {
		g = new GUI();
		s = "";
		b1 = new BDAButton(g, s);
		b2 = new BDAButton(g, s);
	}

	@Test
	public void testChangeState() {
		b1.changeState();
		Assert.assertNotEquals(b1.getState(), b2.getState());

		b2.changeState();
		Assert.assertEquals(b1.getState(), b2.getState());

	}

}