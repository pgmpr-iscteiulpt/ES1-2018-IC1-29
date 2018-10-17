package bda;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class FetchEmails {
	
	private Message[] msgs;

	public Message[] getMsgs() {
		return msgs;
	}

	public  void checkMail(String username, String password) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			try {
				store.connect("imap.gmail.com", username, password);
			}catch (Exception e){ //descobrir nome
				System.out.println("invalid user and pass");
				return;
			}
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			msgs = inbox.getMessages();

		} catch (Exception e) {
			System.out.println("invalid");
			e.printStackTrace();
		}
	}

}

	