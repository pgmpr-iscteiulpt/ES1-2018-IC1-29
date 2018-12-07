package jUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBDAButton.class, TestBDATableModel.class, TestContent.class, TestContentGUI.class,
		TestDateComparator.class, TestFetchEmails.class, TestFetchPosts.class, TestFetchTweets.class, TestGUI.class,
		TestLogin.class })
public class AllTests {

}
