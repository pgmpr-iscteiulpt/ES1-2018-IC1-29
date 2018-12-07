package bda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import com.restfb.types.Post;
import twitter4j.Status;

/**
 * Implementa um classe que abrange todo o tipo de conteúdos presentes na interface
 * 
 * @author Grupo 29
 * @version 4.0
 */

@SuppressWarnings("serial")
public class Content implements Serializable {

	private Message msg;
	private Status status;
	private Post post;
	private String type;
	private String username;
	private String time;

	private Scanner s = null;
	private String scannedDate;
	private String scannedFrom;
	private String scannedSubject;
	private Boolean scanned = false;

	/**
	 * Construtor de um Conteúdo da inbox refrente a uma mensagem de Email
	 * 
	 * @param msg Message que representa o conteúdo de um email
	 * @param username String referente ao username do utilizador
	 */
	public Content(Message msg, String username) {
		this.msg = msg;
		this.username = username;
		this.status = null;
		this.post = null;
		type = "Email";
	}

	/**
	 * Construtor de um Conteúdo da inbox referente a um tweet do Twitter
	 * 
	 * @param status Status que representa o conteúdo de um tweet
	 * @param username String referente ao username do utilizador
	 */
	public Content(Status status, String username) {
		this.status = status;
		this.username = username;
		this.msg = null;
		this.post = null;
		type = "Twitter";
	}

	/**
	 * Construtor de um Conteúdo da inbox referente a um post do Facebook
	 * 
	 * @param post Post que representa o conteúdo de um post
	 * @param username String referente ao username do utilizador
	 */
	public Content(Post post, String username) {
		this.post = post;
		this.username = username;
		this.msg = null;
		this.status = null;
		type = "Facebook";
	}

	/**
	 * Construtor de um Conteúdo da inbox referente a um post de grupo do Facebook
	 * 
	 * @param post Post que representa o conteúdo de um post num grupo de Facebook
	 * @param username String referente ao username do utilizador
	 * @param time	String refertente à hora a que foi colocado o post no grupo
	 */
	public Content(Post post, String username, String time) {
		this.post = post;
		this.username = username;
		this.time = time;
		this.type = "Facebook Group";
		this.msg = null;
		this.status = null;
	}

	/**
	 * Contrutor de um Conteúdo da inbox a partir de um ficheiro txt (offline)
	 * @param path Ficheiro txt
	 */ 
	public Content(File path) {
		this.post = null;
		this.msg = null;
		this.status = null;
		try {
			s = new Scanner(new FileReader(path));
			s.nextLine();
			type = s.nextLine();
			username = s.nextLine();
			scannedDate = s.nextLine();
			scannedFrom = s.nextLine();
			scannedSubject = s.nextLine();
			scanned = true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método que devolve a informação de um tipo de conteúdo
	 * 
	 * @return Object (de um determinado tipo de conteúdo)
	 * @throws IOException
	 * @throws MessagingException
	 */
	public Object getContent() throws IOException, MessagingException {
		if (type.equals("Email"))
			return msg;
		if (type.equals("Twitter"))
			return status;
		if (type.equals("Facebook") || type.equals("Facebook Group"))
			return post;

		return null;
	}

	/**
	 * Método que devolve o nome do tipo de conteúdo
	 * 
	 * @return String (type)
	 */
	public String getType() {
		return type;
	}

	/**
	 * Método que devolve uma String correspondente a uma formatação específica da
	 * data do conteúdo
	 * 
	 * @return String (hash)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getHashCode() throws MessagingException, IOException {
		String hash = "";
		String[] date = null;
		String[] time = null;

		if (scanned) {
			date = scannedDate.split(" ");
		} else {

			if (type.equals("Email"))
				date = ((Message) this.getContent()).getSentDate().toString().split(" ");

			if (type.equals("Twitter"))
				date = ((Status) this.getContent()).getCreatedAt().toString().split(" ");

			if (type.equals("Facebook"))
				date = ((Post) this.getContent()).getCreatedTime().toString().split(" ");

			if (type.equals("Facebook Group"))
				date = this.time.toString().split(" ");
		}
		time = date[3].split(":");
		hash = date[0] + date[1] + date[2] + date[5] + time[0] + time[1] + time[2];

		return hash;
	}

	/**
	 * Método que devolve uma String correspondente à data do conteúdo
	 * 
	 * @return String (hash)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getDate() throws MessagingException, IOException {
		String date = null;

		if (scanned)
			date = scannedDate;
		else {
			if (type.equals("Email"))
				date = ((Message) this.getContent()).getSentDate().toString();

			if (type.equals("Twitter"))
				date = ((Status) this.getContent()).getCreatedAt().toString();

			if (type.equals("Facebook"))
				date = ((Post) this.getContent()).getCreatedTime().toString();

			if (type.equals("Facebook Group"))
				date = this.time;
		}
		return date;

	}

	/**
	 * Método que retorna uma String com o remetente do conteúdo
	 * 
	 * @return String (nome do remetente)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getFrom() throws MessagingException, IOException {
		if (scanned)
			return scannedFrom;
		if (type.equals("Email")) {
			Address[] a = ((Message) this.getContent()).getFrom();
			return ((InternetAddress) a[0]).getAddress();
		}
		if (type.equals("Twitter")) {
			return ((Status) this.getContent()).getUser().getName();
		}
		if (type.equals("Facebook") || type.equals("Facebook Group")) {
			return username;
		}

		return null;

	}

	/**
	 * Método que retorna uma String com o assunto do conteúdo
	 * 
	 * @return String (assunto do conteúdo)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getSubject() throws MessagingException, IOException {
		if (scanned)
			return scannedSubject;

		if (type.equals("Email"))
			return ((Message) this.getContent()).getSubject().toString();

		if (type.equals("Twitter"))
			return ((Status) this.getContent()).getText();

		if (type.equals("Facebook") || type.equals("Facebook Group"))
			return ((Post) this.getContent()).getMessage();

		return null;

	}

	/**
	 * Método que devolve o nome do utilizador
	 * 
	 * @return	String (nome do utilizador) 
	 */
	public String getUsername() {
		return username;
	}

}
