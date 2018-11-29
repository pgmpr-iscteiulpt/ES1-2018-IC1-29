package bda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import com.restfb.types.User;

import twitter4j.Status;

public class FetchPosts {

	private String userName;
	private ArrayList<Content> posts = new ArrayList<>();

	public void checkPosts() {
		String accessToken = "EAALFIGPljFgBAGzUGaoJ5bux0MdV831tF9yeExTeWU7ap3xAMWMQnT6J3y8FhZCVXKL3CmXsN8K0TWXXRcWVdXgc0ddZAqdSejiZCwSYqoHoSS7LRcIcFfnR4ltYJQkgpvu4XP57GsflRGZBf5lxPctGQxzzIAhkytSZB6J0OxGZC9mgmHuYTv";
		@SuppressWarnings("deprecation")
		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		User me = fbClient.fetchObject("me", User.class);
		userName = me.getName().toString();

		Connection<Post> result = fbClient.fetchConnection("me/feed", Post.class);
		int counter = 0;
		int counterTotal = 0;
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

//					if (aPost.getMessage() != null && aPost.getMessage().contains("Mestrado")) {
				System.out.println("---- Post " + counter + " ----");
				System.out.println("Id: " + "fb.com/" + aPost.getId());
				System.out.println("Message: " + aPost.getMessage());
				System.out.println("Created: " + aPost.getCreatedTime());
				counter++;
//				}
				counterTotal++;
			}
		}
		System.out.println("-------------\nNº of Results: " + counter + "/" + counterTotal);
	}

	public String getUserName() {
		return userName;
	}

	public ArrayList<Content> getPosts() {
		return posts;
	}
}
