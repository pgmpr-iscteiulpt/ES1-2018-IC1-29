package jUnitTests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ContentHandlers.FetchPosts;
import bda.Content;


public class TestFetchPosts {
	
	private FetchPosts p;
	private ArrayList<Content> c;
	private String user;

	@Before
	public void setUp() throws Exception {
		user = "Grupovinte Enove";
		p = new FetchPosts();
		c = new ArrayList<Content>();
		p.connect("EAAD4Yt8YSVgBALt9oAijYZC6PcjocBZBfiXQ2ZBQwZAf0MvLETVZBZC13IfYLMqUA2Q5BUY3tsrl0h1IpAjvdRyESSFWEZBE"
				+ "MDhs3MODEYIzd335XxzBwwfSG9JX412JaasskH7BLe5p9mYCuZAtAZBijw5hq9E3hAQeAzRXgG5JaHHqzReWxz5l2");	
	}

	@Test
	public void testReturnPosts() throws MessagingException, IOException {
		p.checkPosts();
		c = p.getPosts();
		Assert.assertNotEquals(0, c.size());
	}
	
	@Test
	public void testReturnGroupPosts() {
		p.checkGroupPosts();
		c = p.getPosts();
		Assert.assertNotEquals(0, c.size());
	}
	
	@Test
	public void testGetUsername() {
		Assert.assertEquals(p.getUserName(), user);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File dir = new File("Resources/Posts");
		for (File file : dir.listFiles())
			if (!file.getName().equals("Untitled"))
				file.delete();
		
		File dir2 = new File("Resources/GroupPosts");
		for (File file : dir2.listFiles())
			if (!file.getName().equals("Untitled"))
				file.delete();
	}

}
