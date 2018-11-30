package bda;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Acede à caixa de entrada de uma conta do twitter tendo em conta que o
 * username e password fornecidos são corretos
 * 
 * @author Grupo 29
 * @version 2.0
 */

public class FetchTweets {

	private ArrayList<Content> status = new ArrayList<Content>();
	private String userName;
	private ConfigurationBuilder conf;
	private TwitterFactory factory;
	private Twitter twitter;

	private void setProperties() {
		conf = new ConfigurationBuilder();
		conf.setDebugEnabled(true).setOAuthConsumerKey("AWYJQX6EFgYwtAj0mvND4mAB4")
				.setOAuthConsumerSecret("6mTebovEjo5NB5DCkoXNgv6ftylM4vLgcI1POtED5JlMH3H1s8")
				.setOAuthAccessToken("1064175567191330817-iJzBvZrNu4nVTccmXDK4wZDbuzaKx3")
				.setOAuthAccessTokenSecret("mGdzd4dgGZgP2atSUfjBXaKigI0N1PCTQPD1faurpR4HH");

		factory = new TwitterFactory(conf.build());
		twitter = factory.getInstance();
	}

	/**
	 * Método que confirma se o username e password fornecidos estão corretos e
	 * acede à conta do twitter
	 * 
	 * @param username String (username do utilizador)
	 * @param password String (password do utilizador)
	 * @throws TwitterException
	 * @throws IllegalStateException
	 */
	public void checkTweets() {
		setProperties();
		try {
			userName = twitter.showUser(twitter.getScreenName()).getName();
			List<Status> statuses = twitter.getHomeTimeline();

			for (Status s : statuses) {
				Content c = new Content(s, userName);
				status.add(c);
				String hash = c.getHashCode();
				PrintWriter writer = new PrintWriter(
						System.getProperty("user.dir") + File.separator + "Resources\\Tweets\\Tweet" + hash, "UTF-8");
				writer.println(s.getId());
				writer.println(((Status) c.getContent()).getText());
				writer.close();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void replyTweet(long tweetId, String replyMessage) {
		StatusUpdate statusUpdate = new StatusUpdate(replyMessage);
		statusUpdate.setInReplyToStatusId(tweetId);
		try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			System.out.println("Falha no envio da resposta");
		}

	}

	public void retweet(long tweetId) {
		try {
			twitter.retweetStatus(tweetId);
		} catch (TwitterException e) {
			System.out.println("Falha no retweet");
		}
	}

	/**
	 * Método que devolve um arraylist de Conteúdos
	 * 
	 * @return status ArrayList<Content> (tweets que constam no twitter do
	 *         utilizador)
	 */
	public ArrayList<Content> getStatus() {
		return status;

	}

	public String getUserName() {
		return userName;
	}

}
