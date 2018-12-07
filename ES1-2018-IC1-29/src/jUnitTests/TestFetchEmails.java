package jUnitTests;

import java.io.File;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ContentHandlers.FetchEmails;
import bda.Content;


public class TestFetchEmails {

	private FetchEmails e;
	private ArrayList<Content> c;
	private String userName;
	private String password;

	@Before
	public void setup() throws Exception {
		userName = "es1.grupo29@gmail.com";
		password = "tenhosono";
		e = new FetchEmails();
		c = new ArrayList<Content>();
		e.connect(userName, password);
		e.checkMail();
	}

	@Test
	public void emailReturnEmails() {
		c = e.getMsgs();
		Assert.assertNotEquals(0, c.size());
	}
	
	@Test
	public void testGetUsername() {
		Assert.assertEquals(e.getUserName(), userName);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File dir = new File("Resources/Emails");
		for (File file : dir.listFiles())
			if (!file.getName().equals("Untitled"))
				file.delete();
	}

}
