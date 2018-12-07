package ContentHandlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import bda.Content;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Acede à caixa de entrada de uma conta do Twitter
 * 
 * @author Grupo 29
 * @version 4.0
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

	/**
	 * Método que define as propriedades de acesso à conta do twitter
	 */
	private void setProperties() {
		conf = new ConfigurationBuilder();
		conf.setDebugEnabled(true).setOAuthConsumerKey(token1).setOAuthConsumerSecret(token2)
				.setOAuthAccessToken(token3).setOAuthAccessTokenSecret(token4);

		factory = new TwitterFactory(conf.build());
		twitter = factory.getInstance();
	}

	/**
	 * Método que faz a conecção à conta do twitter usando tokens de acesso
	 * 
	 * @param token1 String
	 * @param token2 String
	 * @param token3 String
	 * @param token4 String
	 */
	public void connect(String token1, String token2, String token3, String token4) {
		this.token1 = token1;
		this.token2 = token2;
		this.token3 = token3;
		this.token4 = token4;
		setProperties();
		try {
			userName = twitter.showUser(twitter.getScreenName()).getName();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível ligar ao servico Twitter. Algumas funcionalidades poderão não estar disponíveis",
					"Ligação sem sucesso", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Método que acede à conta do twitter e guarda os tweets localmente
	 *
	 * @throws IOException
	 * @throws MessagingException
	 * @throws TwitterException
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
			writer.println(s.getUser().getName());
			writer.println(((Status) c.getContent()).getText());
			writer.println(((Status) c.getContent()).getText());
			writer.close();
		}

	}

	/**
	 * Método que permite responder a um tweet
	 * 
	 * @param tweetId      long(tweetId)
	 * @param replyMessage String(mensagem)
	 */
	public void replyTweet(long tweetId, String replyMessage) {
		StatusUpdate statusUpdate = new StatusUpdate(replyMessage);
		statusUpdate.setInReplyToStatusId(tweetId);
		try {
			twitter.updateStatus(statusUpdate);
		} catch (TwitterException e) {
			System.out.println("Falha no envio da resposta");
		}

	}

	/**
	 * Método que permite fazer retweet
	 * 
	 * @param tweetId long(tweetId)
	 */
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

	/**
	 * Método que devolve o nome do user
	 * 
	 * @return username String(user)
	 */
	public String getUserName() {
		return userName;
	}

}
