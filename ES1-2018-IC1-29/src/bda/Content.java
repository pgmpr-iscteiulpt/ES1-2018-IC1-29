package bda;

import java.io.IOException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import twitter4j.Status;

public class Content {

	private Message msg;
	private Status status;
	private String type;

	public Content(Message msg) {
		this.msg = msg;
		this.status = null;
		type = "email";
	}

	public Content(Status status) {
		this.msg = null;
		this.status = status;
		type = "twitter";
	}

	public Object getContent() throws IOException, MessagingException {
		if (type.equals("email"))
			return msg;
		else
			return status;
	}

	public String getType() {
		return type;
	}

	public String getHashCode() throws MessagingException, IOException {
		String hash = "";
		String[] date = null;
		String[] time = null;

		if (type.equals("email"))
			date = ((Message) this.getContent()).getSentDate().toString().split(" ");

		if (type.equals("twitter"))
			date = ((Status) this.getContent()).getCreatedAt().toString().split(" ");

		time = date[3].split(":");
		hash = date[0] + date[1] + date[2] + date[5] + time[0] + time[1] + time[2];

		return hash;
	}

	public String getDate() throws MessagingException, IOException {
		String date = null;
		if (type.equals("email"))
			date = ((Message) this.getContent()).getSentDate().toString();

		if (type.equals("twitter"))
			date = ((Status) this.getContent()).getCreatedAt().toString();

		return date;

	}

	public String getFrom() throws MessagingException, IOException {

		if (type.equals("email")) {
			Address[] a = ((Message) this.getContent()).getFrom();
			return ((InternetAddress) a[0]).getAddress();
		} else
			return ((Status) this.getContent()).getUser().getName();

	}

	public String getSubject() throws MessagingException, IOException {

		if (type.equals("email"))
			return ((Message) this.getContent()).getSubject().toString();

		else
			return ((Status) this.getContent()).getText();

	}

}


