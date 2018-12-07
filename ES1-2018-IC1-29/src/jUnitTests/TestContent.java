package jUnitTests;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import com.restfb.types.Post;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.junit.Assert;
import bda.Content;
import twitter4j.Status;

public class TestContent {

	private Content c1;
	private Content c2;
	private Content c3;

	
	private String user;
	private Message m;
//	private Message m2;
	private Status s;
	private Post p;
	private String type;
	
	@Before
	public void setUp() throws Exception {
		type = "Email";
		user = "grupo29";
		m = null;
		s = null;
		p = null;
//		m2 =null;
		c1 = new Content(m, user);
		c2 = new Content(s, user);
		c3 = new Content(p, user);
	
	}

	@Test
	public void testGetUserName() {
		Assert.assertEquals(c1.getUsername(), user);
	}
	
	@Test
	public void testGetFrom() throws MessagingException, IOException {
		Assert.assertEquals(c3.getFrom(), user);
	}
	
	@Test
	public void testGetType() {
		Assert.assertEquals(c1.getType(), type);
		Assert.assertNotEquals(c1.getType() , c2.getType());
	}

	@Test
	public void testGetContent() throws IOException, MessagingException {
		Object a = (Object) c1.getContent();
		Object b = (Object) c2.getContent();
		Object c = (Object) c3.getContent();
		Assert.assertEquals(a , b);
		Assert.assertEquals(a , c);
	}


}
