package bda;

import java.util.List;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import com.restfb.types.User;

public class FetchPosts {

	public static void main(String[] args) {
		String accessToken = "EAALFIGPljFgBAGzUGaoJ5bux0MdV831tF9yeExTeWU7ap3xAMWMQnT6J3y8FhZCVXKL3CmXsN8K0TWXXRcWVdXgc0ddZAqdSejiZCwSYqoHoSS7LRcIcFfnR4ltYJQkgpvu4XP57GsflRGZBf5lxPctGQxzzIAhkytSZB6J0OxGZC9mgmHuYTv";
		@SuppressWarnings("deprecation")
		FacebookClient fbClient = new DefaultFacebookClient(accessToken);
		User me = fbClient.fetchObject("me", User.class);
		System.out.println("Facebook:");
		System.out.println("Id: " + me.getId());
		System.out.println("Name: " + me.getName());

		Connection<Post> result = fbClient.fetchConnection("me/feed", Post.class);
		System.out.println("\nPosts:");
		int counter = 0;
		int counterTotal = 0;
		for (List<Post> page : result) {
			for (Post aPost : page) {
				// Filters only posts that contain the word "Inform"
//				if (aPost.getMessage() != null && aPost.getMessage().contains("Mestrado")) {
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

}
