package jUnitTests;


import javax.mail.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import bda.Content;
import bda.DateComparator;


public class TestDateComparator {

	private DateComparator dc;
	private Content a;
	private Content b;
	private Message c;
	private boolean bl;
	private String user;
	
	@Before
	public void setup() throws Exception {
		user = "Grupo29";
		dc = new DateComparator();
		c = null;
		a = new Content(c, user);
		b = new Content(c, user);

	}

	@Test
	public void testeNullCompare() {
		try {
			dc.compare(a, b);
		} catch (Exception e) {
			bl = (e instanceof NullPointerException);
			Assert.assertTrue(bl);
		}
	}


}
