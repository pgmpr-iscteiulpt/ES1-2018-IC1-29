package ContentHandlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.mail.MessagingException;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Comment;
import com.restfb.types.Comments;
import com.restfb.types.GraphResponse;
import com.restfb.types.Group;
import com.restfb.types.Likes;
import com.restfb.types.Post;
import com.restfb.types.User;

import bda.Content;

/**
 * Acede ao feed de uma conta de Facebook
 * 
 * @author Grupo29
 * @version 4.0
 */
public class FetchPosts {

	private User user;
	private String userName;
	private FacebookClient fbClient;
	private Connection<Post> result;
	private Connection<Group> groupFeed;
	private ArrayList<Content> posts = new ArrayList<>();
	private String accessToken;

	/**
	 * Método que define as propriedades de acesso ao feed do facebook
	 */
	@SuppressWarnings("deprecation")
	private void setProperties() {
		fbClient = new DefaultFacebookClient(accessToken);
		user = fbClient.fetchObject("me", User.class);
		result = fbClient.fetchConnection("me/feed", Post.class);
		groupFeed = fbClient.fetchConnection("me/groups", Group.class);
	}

	/**
	 * Método que faz a conecção à conta de facebook usando um token de acesso
	 * 
	 * @param accessToken String
	 */
	public void connect(String accessToken) {
		this.accessToken = accessToken;
		setProperties();
		userName = user.getName().toString();
	}

	/**
	 * Método que acede ao feed do facebook e guarda os posts localmente
	 */
	public void checkPosts() throws MessagingException, IOException {
		for (List<Post> page : result) {
			for (Post aPost : page) {

				Content p = new Content(aPost, userName);
				posts.add(p);
				String hash;
				hash = p.getHashCode();
				PrintWriter writer = new PrintWriter(
						System.getProperty("user.dir") + File.separator + "Resources\\Posts\\Post" + hash, "UTF-8");
				writer.println(aPost.getId());
				writer.println("Facebook");
				writer.println(userName);
				writer.println(aPost.getCreatedTime());
				writer.println(userName);
				writer.println(aPost.getMessage());
				writer.println(aPost.getMessage());
				writer.close();

			}
		}

	}

	/**
	 * Método que acede ao feed do facebook e guarda os posts de um grupo localmente
	 */
	@SuppressWarnings("deprecation")
	public void checkGroupPosts() {
		long millis = System.currentTimeMillis();
		Date date = new java.util.Date(millis);
		int counter = 0;
		for (List<Group> page : groupFeed) {
			for (Group aGroup : page) {
				Connection<Post> groupFeedPosts = fbClient.fetchConnection(aGroup.getId() + "/feed", Post.class);
				for (List<Post> page1 : groupFeedPosts) {
					for (Post aPost : page1) {
						try {
							date.setSeconds(counter++);
							if (counter == 60)
								counter = 0;
							String date1 = date.toString();
							Content p = new Content(aPost, userName, date1);
							posts.add(p);
							String hash;
							hash = p.getHashCode();
							PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + File.separator
									+ "Resources\\GroupPosts\\Post" + hash, "UTF-8");
							writer.println(aPost.getId() + " " + aGroup.getId());
							writer.println("Facebook Group");
							writer.println(userName);
							writer.println(date);
							writer.println(aGroup.getName());
							writer.println(aPost.getMessage());
							writer.println(aPost.getMessage());
							writer.close();
						} catch (MessagingException | IOException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}

	}

	/**
	 * Método que devolve uma lista de Conteúdos
	 * 
	 * @return posts ArrayList<Content>
	 */
	public ArrayList<Content> getPosts() {
		return posts;
	}

	/**
	 * Método que devolve o nome do user
	 * 
	 * @return username String(user)
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Método que permite publicar posts num grupo
	 * 
	 * @param postID  String(groupID)
	 * @param message String(mensagem)
	 */
	public void replyToPost(String postID, String message) {
		fbClient.publish(postID + "/feed", GraphResponse.class, Parameter.with("message", message));
	}

	/**
	 * Método que permite obter o número de likes de um determinado post
	 * 
	 * @param postID String(postID)
	 * @return long (número de likes)
	 */
	public long getLikesCount(String postID) {
		Likes likes = fbClient.fetchObject(postID + "/likes", Likes.class, Parameter.with("summary", 1),
				Parameter.with("limit", 0));
		return likes.getTotalCount();
	}

	/**
	 * Método que permite obter o número de comentários de um determinado post
	 * 
	 * @param postID String(postID)
	 * @return long (número de comentários)
	 */
	public long getCommentsCount(String postID) {
		Comments comments = fbClient.fetchObject(postID + "/comments", Comments.class, Parameter.with("summary", 1),
				Parameter.with("limit", 0));
		return comments.getTotalCount();

	}

	/**
	 * Método que devolve um HashMap com os comentários de um post e respectivos
	 * autores
	 * 
	 * @param postID String(postID)
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> getComments(String postID) {
		HashMap<String, String> comments = new HashMap<>();
		Connection<Comment> allComments = fbClient.fetchConnection(postID + "/comments", Comment.class,
				Parameter.with("fields", "message,from{name,id}"));
		for (List<Comment> postcomments : allComments) {
			for (Comment comment : postcomments) {
				System.out.println(comment.getMessage());
				comments.put(comment.getMessage().toString(), " "/* comment.getFrom().getName() */);
			}
		}
		return comments;
	}

}
