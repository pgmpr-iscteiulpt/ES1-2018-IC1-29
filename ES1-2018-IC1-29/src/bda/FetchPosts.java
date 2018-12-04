package bda;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.mail.MessagingException;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Comment;
import com.restfb.types.Comments;
import com.restfb.types.Likes;
import com.restfb.types.Post;
import com.restfb.types.User;

public class FetchPosts {

	private User user;
	private String userName;
	private FacebookClient fbClient;
	private Connection<Post> result;
	private ArrayList<Content> posts = new ArrayList<>();
	private String accessToken;

	@SuppressWarnings("deprecation")
	private void setProperties() {
		fbClient = new DefaultFacebookClient(accessToken);
		user = fbClient.fetchObject("me", User.class);
		result = fbClient.fetchConnection("me/feed", Post.class);
	}

	public void checkPosts(String token) {
		this.accessToken = token;
		setProperties();
		userName = user.getName().toString();
		for (List<Post> page : result) {
			for (Post aPost : page) {
				try {
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
				} catch (MessagingException | IOException e) {
					e.printStackTrace();
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
		Connection<Comment> allComments = fbClient.fetchConnection(postID + "/comments", Comment.class);
		for (List<Comment> postcomments : allComments) {
			for (Comment comment : postcomments) {
				comments.put(comment.getMessage().toString(), comment.getFrom().getName());
			}
		}
		return comments;
	}
}
