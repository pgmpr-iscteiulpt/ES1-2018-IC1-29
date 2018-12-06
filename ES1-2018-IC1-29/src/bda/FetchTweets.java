package bda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

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
	private String token1;
	private String token2;
	private String token3;
	private String token4;

	private void setProperties() {
		conf = new ConfigurationBuilder();
		conf.setDebugEnabled(true).setOAuthConsumerKey(token1).setOAuthConsumerSecret(token2)
				.setOAuthAccessToken(token3).setOAuthAccessTokenSecret(token4);

		factory = new TwitterFactory(conf.build());
		twitter = factory.getInstance();
	}

	public void connect(String token1, String token2, String token3, String token4) {
		this.token1 = token1;
		this.token2 = token2;
		this.token3 = token3;
		this.token4 = token4;
		setProperties();
		try {
			userName = twitter.showUser(twitter.getScreenName()).getName();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Método que confirma se o username e password fornecidos estão corretos e
	 * acede à conta do twitter
	 * 
	 * @param username String (username do utilizador)
	 * @param password String (password do utilizador)
	 * @throws TwitterException
	 * @throws IOException 
	 * @throws MessagingException 
	 * @throws IllegalStateException
	 */
	public void checkTweets() throws TwitterException, MessagingException, IOException {
		List<Status> statuses = twitter.getHomeTimeline();

		for (Status s : statuses) {
			Content c = new Content(s, userName);
			status.add(c);
			String hash = c.getHashCode();
			PrintWriter writer = new PrintWriter(
					System.getProperty("user.dir") + File.separator + "Resources\\Tweets\\Tweet" + hash, "UTF-8");
			writer.println(s.getId());
			writer.println("Twitter");
			writer.println(userName);
			writer.println(s.getCreatedAt().toString());
			writer.println(userName);
			writer.println(((Status) c.getContent()).getText());
			writer.println(((Status) c.getContent()).getText());
			writer.close();
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
