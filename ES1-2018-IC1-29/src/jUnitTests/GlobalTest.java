package jUnitTests;

import java.io.IOException;
import java.util.ArrayList;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import bda.BDAButton;
import bda.BDATableModel;
import bda.Content;
import bda.ContentGUI;
import bda.DateComparator;
import bda.FetchEmails;
import bda.FetchTweets;
import bda.GUI;
import bda.Login;
import twitter4j.Status;

public class GlobalTest {

	private ContentGUI cg;

	private FetchEmails f;
	private FetchTweets tt;

	private Content c1;
	private Content c2;
	private Message m1;
	private Message m2;
	private ArrayList<Content> l;
	private BDATableModel t;

	private BDAButton b1;
	private BDAButton b2;
	private BDAButton b3;
	private GUI g;
	private String n;
	private String e;

	private Content c3;
	private Content c4;
	private Message m;
	private Status s;
	private Content a;
	private Content b;
	private Message c;

	private boolean bool;

	private DateComparator dc;
	private Login log;

	@Before
	public void setup() throws Exception {

		bool = false;

		f = new FetchEmails();
		tt = new FetchTweets();

		m = null;
		s = null;
		c = null;
		e = "email";

		c1 = new Content(m1);
		c2 = new Content(m2);
		a = new Content(c);
		b = new Content(c);
		c3 = new Content(m);
		c4 = new Content(s);

		l = new ArrayList<Content>();
		l.add(c1);
		l.add(c2);


		t = new BDATableModel(l);
		dc = new DateComparator();

		n = "";
		g = new GUI();
		b1 = new BDAButton(g, n);
		b2 = new BDAButton(g, n);
		b3 = new BDAButton(g, e);
		log = new Login(g,b3);

	}



	//BDATableModel
	@Test
	public void testGetColumnCount() {
		Assert.assertEquals(4, t.getColumnCount());
		g.open();
	}

	@Test
	public void testGetRowCount() {
		Assert.assertEquals(2, t.getRowCount());
	}

	@Test
	public void testReturnColumnNames() {
		String a = "Data e Hora";
		String b = "Fonte";
		String c = "Remetente";
		String d = "Assunto";
		Assert.assertEquals(a, t.getColumnName(0));
		Assert.assertEquals(b, t.getColumnName(1));
		Assert.assertEquals(c, t.getColumnName(2));
		Assert.assertEquals(d, t.getColumnName(3));
	}

	//FetchEmails
	@Test
	public void testGetMsgs() {
		l = f.getMsgs();
		Assert.assertEquals(0, l.size());
	}

	//FetchTweets
	@Test
	public void testGetStatus() {
		l = tt.getStatus();
		Assert.assertEquals(0, l.size());
	}

	//Login
	@Test
	public void testgetLog() {
		Assert.assertNotEquals(g.getLog(1), g.getLog(2));
		Assert.assertNotEquals(g.getLog(4), g.getLog(3));	
	}

	@Test
	public void testState() {
		log.login();
		log.open();
		Assert.assertEquals(b3.getState(), true);
	}

	//BDAButton
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

	//Content
	@Test
	public void testGetType() {
		String t = "email";
		Assert.assertEquals(c3.getType(), t);

		Assert.assertNotEquals(c3.getType() , c4.getType());
	}

	@Test
	public void testGetContent() throws IOException, MessagingException {
		Object a = (Object) c1.getContent();
		Object b = (Object) c2.getContent();

		Assert.assertEquals(a , b);
	}

	//ContentGUI
	@Test
	public void testNullGUI() {
		try {
			cg = new ContentGUI (null);
		} catch (Exception e) {
			bool = (e instanceof NullPointerException);
			Assert.assertTrue(bool);

		}
	}

	//DateComparator
	@Test(expected = NullPointerException.class)
	public void testCompare() {
		int i = dc.compare(a, b);
		Assert.assertEquals(null, i);
	}
}
