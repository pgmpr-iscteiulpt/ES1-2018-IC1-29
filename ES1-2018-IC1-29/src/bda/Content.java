package bda;

import java.io.IOException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import twitter4j.Status;

/**
 * Implementa um classe que abrange todo o tipo de conteúdos presentes na interface
 * @author Grupo 29
 * @version 2.0
 */

public class Content {

	private Message msg;
	private Status status;
	private String type;

	/**
	 * Construtor de um Conteúdo da inbox
	 * @param msg Message que representa o conteúdo dum Email
	 */
	public Content(Message msg) {
		this.msg = msg;
		this.status = null;
		type = "email";
	}

	/**
	 * Construtor de um Conteúdo da inbox
	 * @param status Status que representa o conteúdo dum Tweet
	 */
	public Content(Status status) {
		this.msg = null;
		this.status = status;
		type = "twitter";
	}

	/**
	 * Método que devolve a informação de um tipo de conteúdo
	 * 
	 * @return Object (de um determinado tipo de conteúdo)
	 * @throws IOException
	 * @throws MessagingException
	 */
	public Object getContent() throws IOException, MessagingException {
		if (type.equals("email"))
			return msg;
		else
			return status;
	}

	/**
	 * Método que devolve o nome do tipo de conteúdo
	 * @return String (type)
	 */
	public String getType() {
		return type;
	}

	/**
	 * Método que devolve uma String correspondente a uma formatação específica da data do conteúdo
	 * @return String (hash)
	 * @throws MessagingException
	 * @throws IOException
	 */
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

	/**
	 * Método que devolve uma String correspondente à data do conteúdo
	 * @return String (hash)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getDate() throws MessagingException, IOException {
		String date = null;
		if (type.equals("email"))
			date = ((Message) this.getContent()).getSentDate().toString();

		if (type.equals("twitter"))
			date = ((Status) this.getContent()).getCreatedAt().toString();

		return date;

	}

	/**
	 * Método que retorna uma String com o remetente do conteúdo
	 * @return String (nome do remetente)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getFrom() throws MessagingException, IOException {

		if (type.equals("email")) {
			Address[] a = ((Message) this.getContent()).getFrom();
			return ((InternetAddress) a[0]).getAddress();
		} else
			return ((Status) this.getContent()).getUser().getName();

	}

	/**
	* Método que retorna uma String com o assunto do conteúdo
	 * @return String (assunto do conteúdo)
	 * @throws MessagingException
	 * @throws IOException
	 */
	public String getSubject() throws MessagingException, IOException {

		if (type.equals("email"))
			return ((Message) this.getContent()).getSubject().toString();

		else
			return ((Status) this.getContent()).getText();

	}
}
