package jUnitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import bda.ContentGUI;
import bda.GUI;

public class TestContentGUI {

	@SuppressWarnings("unused")
	private ContentGUI c1;
	private boolean b;
	private GUI g;

	@Before
	public void setup() throws Exception {
		g = new GUI();
		b = false;

	}

	@Test
	public void testNullGUI() {
		try {
			c1 = new ContentGUI (null, g);
		} catch (Exception e) {
			b = (e instanceof NullPointerException);
			Assert.assertTrue(b);

		}
	}


}
