package bda;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import com.restfb.types.User;

public class FetchPosts {

	private User user;
	private String userName;
	FacebookClient a;
	private Connection<Post> result;
	private ArrayList<Content> posts = new ArrayList<>();

	private void setProperties() {
		String accessToken = "EAALFIGPljFgBAGzUGaoJ5bux0MdV831tF9yeExTeWU7ap3xAMWMQnT6J3y8FhZCVXKL3CmXsN8K0TWXXRcWVdXgc0ddZAqdSejiZCwSYqoHoSS7LRcIcFfnR4ltYJQkgpvu4XP57GsflRGZBf5lxPctGQxzzIAhkytSZB6J0OxGZC9mgmHuYTv";
		@SuppressWarnings("deprecation")
		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		a = fbClient;
		user = fbClient.fetchObject("me", User.class);

		result = fbClient.fetchConnection("me/feed", Post.class);

	}

	public void checkPosts() {
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
}

//static AccessToken accessToken = new DefaultFacebookClient().obtainExtendedAccessToken("<my app id>", "<my app secret>");
//   public LoggedInFacebookClient(String appId, String appSecret) {
//AccessToken accessToken = this.obtainAppAccessToken(appId, appSecret);
//this.accessToken = accessToken.getAccessToken();
//}