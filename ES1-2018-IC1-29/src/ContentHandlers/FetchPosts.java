package ContentHandlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

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

public class FetchPosts {

	private User user;
	private String userName;
	private FacebookClient fbClient;
	private Connection<Post> result;
	private Connection<Group> groupFeed;
	private ArrayList<Content> posts = new ArrayList<>();
	private String accessToken;

	@SuppressWarnings("deprecation")
	private void setProperties() {
		fbClient = new DefaultFacebookClient(accessToken);
		user = fbClient.fetchObject("me", User.class);
		result = fbClient.fetchConnection("me/feed", Post.class);
		groupFeed = fbClient.fetchConnection("me/groups", Group.class);
	}

	public void connect(String accessToken) {
		this.accessToken = accessToken;
		setProperties();
		userName = user.getName().toString();
	}

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

	public ArrayList<Content> getPosts() {
		return posts;
	}

	public String getUserName() {
		return userName;
	}

	public void replyToPost(String postID, String message) {
		fbClient.publish(postID + "/feed", GraphResponse.class, Parameter.with("message", message));
	}

	public long getLikesCount(String postID) {
		Likes likes = fbClient.fetchObject(postID + "/likes", Likes.class, Parameter.with("summary", 1),
				Parameter.with("limit", 0));
		return likes.getTotalCount();
	}

	public long getCommentsCount(String postID) {
		Comments comments = fbClient.fetchObject(postID + "/comments", Comments.class, Parameter.with("summary", 1),
				Parameter.with("limit", 0));
		return comments.getTotalCount();

	}

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
