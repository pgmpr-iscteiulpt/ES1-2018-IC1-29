package bda;

import java.io.IOException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import com.restfb.types.Post;

import twitter4j.Status;

/**
 * Implementa um classe que abrange todo o tipo de conte�dos presentes na
 * interface
 * 
 * @author Grupo 29
 * @version 2.0
 */

public class Content {

	private Message msg;
	private Status status;
	private Post post;
	private String type;
	private String fbUserName;

	/**
	 * Construtor de um Conte�do da inbox
	 * 
	 * @param msg Message que representa o conte�do dum Email
	 */
	public Content(Message msg) {
		this.msg = msg;
		this.status = null;
		this.post = null;
		type = "Email";
	}

	/**
	 * Construtor de um Conte�do da inbox
	 * 
	 * @param status Status que representa o conte�do dum Tweet
	 */
	public Content(Status status) {
		this.status = status;
		this.msg = null;
		this.post = null;
		type = "Twitter";
	}

	public Content(Post post, String fbUserName) {
		this.post = post;
		this.fbUserName = fbUserName;
		this.msg = null;
		this.status = null;
		type = "Facebook";
	}

	/**
	 * M�todo que devolve a informa��o de um tipo de conte�do
	 * 
	 * @return Object (de um determinado tipo de conte�do)
	 * @throws IOException
	 * @throws MessagingException
	 */
	public Object getContent() throws IOException, MessagingException {
		switch (type) {
		case "Email":
			return msg;
		case "Twitter":
			return status;
		case "Facebook":
			return post;
		}
		return null;
	}

	/**
	 * M�todo que devolve o nome do tipo de conte�do
	 * 
	 * @return String (type)
	 */
	public String getType() {
		return type;
	}

	/**
	 * M�todo que devolve uma String correspondente a uma formata��o espec�fica da
	 * data do conte�do
	 * 
	 * @return String (hash)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getHashCode() throws MessagingException, IOException {
		String hash = "";
		String[] date = null;
		String[] time = null;

		if (type.equals("Email"))
			date = ((Message) this.getContent()).getSentDate().toString().split(" ");

		if (type.equals("Twitter"))
			date = ((Status) this.getContent()).getCreatedAt().toString().split(" ");
		
		if (type.equals("Facebook")) 
			date = ((Post) this.getContent()).getCreatedTime().toString().split(" ");
			
		
		time = date[3].split(":");
		hash = date[0] + date[1] + date[2] + date[5] + time[0] + time[1] + time[2];

		return hash;
	}

	/**
	 * M�todo que devolve uma String correspondente � data do conte�do
	 * 
	 * @return String (hash)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getDate() throws MessagingException, IOException {
		String date = null;
		if (type.equals("Email"))
			date = ((Message) this.getContent()).getSentDate().toString();

		if (type.equals("Twitter"))
			date = ((Status) this.getContent()).getCreatedAt().toString();
		
		if (type.equals("Facebook"))
			date = ((Post) this.getContent()).getCreatedTime().toString();


		return date;

	}

	/**
	 * M�todo que retorna uma String com o remetente do conte�do
	 * 
	 * @return String (nome do remetente)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getFrom() throws MessagingException, IOException {

		if (type.equals("Email")) {
			Address[] a = ((Message) this.getContent()).getFrom();
			return ((InternetAddress) a[0]).getAddress();
		}
		if (type.equals("Twitter")) {
			return ((Status) this.getContent()).getUser().getName();
		} 
		if (type.equals("Facebook")) {
			return fbUserName;
		}
		return null; 
			

	}

	/**
	 * M�todo que retorna uma String com o assunto do conte�do
	 * 
	 * @return String (assunto do conte�do)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getSubject() throws MessagingException, IOException {

		if (type.equals("Email"))
			return ((Message) this.getContent()).getSubject().toString();
		
		if (type.equals("Twitter"))
			return ((Status) this.getContent()).getText();

		if (type.equals("Facebook"))
			return ((Post) this.getContent()).getMessage();
		
		return null;

	}
}
