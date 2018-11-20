package bda;

import java.util.ArrayList;
import java.util.List;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class FetchTweets {

	private ArrayList<Content> status = new ArrayList<Content>();

	public FetchTweets() {
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey("AWYJQX6EFgYwtAj0mvND4mAB4")
					.setOAuthConsumerSecret("6mTebovEjo5NB5DCkoXNgv6ftylM4vLgcI1POtED5JlMH3H1s8")
					.setOAuthAccessToken("1064175567191330817-iJzBvZrNu4nVTccmXDK4wZDbuzaKx3")
					.setOAuthAccessTokenSecret("mGdzd4dgGZgP2atSUfjBXaKigI0N1PCTQPD1faurpR4HH");

			TwitterFactory tf = new TwitterFactory(cb.build());
			twitter4j.Twitter twitter = tf.getInstance();

			List<Status> statuses = twitter.getHomeTimeline();
			int counter = 0;
			int counterTotal = 0;
			for (Status s : statuses) {
				status.add(new Content(s));
//FILTRO		if (s.getUser().getName() != null && s.getUser().getName().contains("ISCTE")) {
				counter++;
//			}
				counterTotal++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public ArrayList<Content> getStatus() {
		return status;

	}
}

