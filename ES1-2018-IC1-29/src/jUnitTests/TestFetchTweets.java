package jUnitTests;

import java.io.File;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ContentHandlers.FetchTweets;
import bda.Content;


public class TestFetchTweets {

	private FetchTweets t;
	private ArrayList<Content> c;
	private String user;

	@Before
	public void setup() throws Exception {
		user = "Grupo29";
		t = new FetchTweets();
		c = new ArrayList<Content>();
		t.connect("AWYJQX6EFgYwtAj0mvND4mAB4", "6mTebovEjo5NB5DCkoXNgv6ftylM4vLgcI1POtED5JlMH3H1s8", "1064175567191330817-iJzBvZrNu4nVTccmXDK4wZDbuzaKx3", "mGdzd4dgGZgP2atSUfjBXaKigI0N1PCTQPD1faurpR4HH");
		t.checkTweets();	
	}

	@Test
	public void twitterReturnTweets() {
		c = t.getStatus();
		Assert.assertNotEquals(0, c.size());
	}
	
	@Test
	public void testGetUsername() {
		Assert.assertEquals(t.getUserName(), user);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File dir = new File("Resources/Tweets");
		for (File file : dir.listFiles())
			if (!file.getName().equals("Untitled"))
				file.delete();
	}
}
