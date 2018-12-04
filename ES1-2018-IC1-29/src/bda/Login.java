package bda;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Implementa uma JPanel que está associada a uma interface e um botão
 * 
 * @author Grupo 29
 * @version 2.0
 */

@SuppressWarnings("serial")
public class Login extends JDialog {

	// Constants
	private final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int width = (int) (screenDim.width * 0.18);
	private final int height = (int) (screenDim.height * 0.18);
	private final int widthLocation = screenDim.width / 2 - width / 2;
	private final int heightLocation = screenDim.height / 2 - height / 2;

	// Componentes da janela
	private JDialog login;
	private JLabel nameLabel = new JLabel("Name : ");
	private JTextField nameField = new JTextField();
	private JLabel passwordLabel = new JLabel("Password : ");
	private JPasswordField passwordField = new JPasswordField();
	private JButton okButton = new JButton("Ok");
	private JButton cancelButton = new JButton("Cancel");
	private String type;
	private Object contentHandler;
	private ArrayList<Content> content = new ArrayList<Content>();

	private GUI i;
	private BDAButton b;

	/**
	 * Construtor de uma Janela Login que possui uma interface e um respectivo botão
	 * 
	 * @param i Interface a que pertence a Janela Login
	 * @param b Botão que faz aparecer a Janela Login
	 */
	public Login(GUI i, BDAButton b) {
		this.i = i;
		this.b = b;
		type = b.getIconName();
		login = new JDialog();
		login.setLocation(widthLocation, heightLocation);
		login.setTitle("Login");
		addLoginDialogContent();
		addLoginDialogListeners();
		login.pack();
		login.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * Método que permite a visualização da Janela Login
	 */
	public void open() {
		login.setSize(width, height);
		login.setVisible(true);
	}

	// Criação da janela
	/**
	 * Método que permite criar o design da Janela Login
	 */
	private void addLoginDialogContent() {

		JPanel loginPanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(4, 4, 4, 4);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		loginPanel.add(nameLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		loginPanel.add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		loginPanel.add(passwordLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		loginPanel.add(passwordField, gbc);

		login.add(loginPanel);
		login.add(buttonPanel, BorderLayout.SOUTH);

	}

	/**
	 * Método que devolve o nome do utilizador
	 * 
	 * @return String (mail do utilizador que pretende fazer o Login)
	 */
	public String getUsername() {
		return nameField.getText().trim();
	}

	/**
	 * Método que devolve a password do utilizador
	 * 
	 * @return String (password do utilizador que pretende fazer o Login)
	 */
	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	/**
	 * Método que implementa as ações para cada ativação dos botões
	 */
	private void addLoginDialogListeners() {

		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login.dispose();
			}
		});
	}

	// Login nos canais
	/**
	 * Método que permite averiguar se o email e password dados pelo utilizador são
	 * válidos para efetuar o Login
	 */
	public void login() {

		String dir = null;
		int log = 0;
		switch (type) {
		case "Email":
			dir = "Resources\\Emails";
			log = 3;
			break;
		case "Twitter":
			dir = "Resources\\Tweets";
			log = 2;
			break;
		case "Facebook":
			dir = "Resources\\Posts";
			log = 1;
			break;
		}

		if (checkOfflineCredentials(type, getUsername(), getPassword())) {
			File folder = new File(dir);
			File[] directoryListing = folder.listFiles();
			String username = null;
			boolean first = true;
			for (File contentFile : directoryListing) {
				if (!contentFile.getName().equals("Untitled")) {
					content.add(new Content(contentFile));
					if (first) {
						username = new Content(contentFile).getUsername();
						first = false;
					}
				}
			}
			i.getLog(log).setText(username);

		} else {

			if (type.equals("Email")) {
				try {
					FetchEmails email = new FetchEmails();
					email.checkMail(getUsername(), getPassword());
					content = email.getMsgs();
					addXMLElement("Email", getUsername(), getPassword(), null);
					i.getLog(3).setText(nameField.getText());
					contentHandler = email;
				} catch (Exception E) {
					JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Login",
							JOptionPane.ERROR_MESSAGE);
					nameField.setText("");
					passwordField.setText("");
				}
			}
			if (type.equals("Twitter")) {
				login.dispose();
				String token = askForToken("Twitter");
				String[] tokens = token.split(" ");
				FetchTweets twitter = new FetchTweets();
				twitter.checkTweets(tokens[0], tokens[1], tokens[2], tokens[3]);
				addXMLElement("Twitter", getUsername(), getPassword(), token);
				content = twitter.getStatus();
				i.getLog(2).setText(twitter.getUserName());
				contentHandler = twitter;
			}

			if (type.equals("Facebook")) {
				login.dispose();
				String token = askForToken("Facebook");
				FetchPosts facebook = new FetchPosts();
				facebook.checkPosts(token);
				addXMLElement("Facebook", getUsername(), getPassword(), token);
				content = facebook.getPosts();
				i.getLog(1).setText(facebook.getUserName());
				contentHandler = facebook;
			}
		}
		b.changeImage();
		b.changeState();
		login.dispose();

		if (!content.isEmpty() && i.getInboxTable().getModel().getClass() != BDATableModel.class) {
			i.getInboxTable().setModel(new BDATableModel(content));
			i.getInboxTable().getColumnModel().getColumn(3)
					.setPreferredWidth((int) (i.getInboxTable().getWidth() * 0.4));
		} else {
			for (Content c : content) {
				((BDATableModel) i.getInboxTable().getModel()).addRow(c);
				((AbstractTableModel) i.getInboxTable().getModel()).fireTableDataChanged();
			}
		}

		ArrayList<Object> handlers = ((BDATableModel) i.getInboxTable().getModel()).getContentHandlers();
		if (!handlers.contains(contentHandler))
			((BDATableModel) i.getInboxTable().getModel()).addContentHandler(contentHandler);
	}

	private String askForToken(String icon) {

		String message;
		if (icon.equals("Twitter")) {
			message = "           Introduza os 4 tokens de acesso a esta conta, separados por um espaço"
					+ System.lineSeparator() + "                   (apenas o terá de fazer a primeira vez).";
		} else {
			message = "                 Introduza o token de acesso a esta conta" + System.lineSeparator()
					+ "                   (apenas o terá de fazer a primeira vez).";
		}
		UIManager.put("OptionPane.minimumSize", new Dimension(450, 130));
		String token = (String) JOptionPane.showInputDialog(null, message, "Novo Utilizador",
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(new ImageIcon("images/" + icon + "On.png").getImage()
						.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)),
				null, null);

		return token;

	}

	/**
	 * Método que permite o utilizador fazer Loggout
	 */
	public void logout() {
		ArrayList<Content> remove = new ArrayList<Content>();

		if (type.equals("Twitter"))
			i.getLog(2).setText("Not Logged In   ");

		if (type.equals("Facebook"))
			i.getLog(1).setText("Not Logged In   ");

		if (type.equals("Email"))
			i.getLog(3).setText("Not Logged In   ");

		for (Content c : ((BDATableModel) i.getInboxTable().getModel()).getTableContent()) {
			if (c.getType().equals(type))
				remove.add(c);
		}

		for (Content r : remove) {
			((BDATableModel) i.getInboxTable().getModel()).getTableContent().remove(r);
		}
		remove.clear();
		((AbstractTableModel) i.getInboxTable().getModel()).fireTableDataChanged();

		b.changeImage();
		b.changeState();

	}

	private boolean checkOfflineCredentials(String type, String user, String pass) {
		Document doc = getXMLDoc();

		NodeList listOfUsers = doc.getElementsByTagName(type + "User");
		for (int i = 0; i < listOfUsers.getLength(); i++) {
			Node userNode = listOfUsers.item(i);
			if (userNode.getNodeType() == Node.ELEMENT_NODE) {
				Element usernameElement = (Element) ((Element) userNode).getElementsByTagName("username").item(0);
				String username = usernameElement.getChildNodes().item(0).getNodeValue().trim();
				if (username.equals(user)) {
					Element passwordElement = (Element) ((Element) userNode).getElementsByTagName("password").item(0);
					String password = passwordElement.getChildNodes().item(0).getNodeValue().trim();
					if (password.equals(pass)) {
						return true;
					}
				}
			}

		}
		return false;

	}



	private void addXMLElement(String root, String user, String pass, String token) {
		Document doc = getXMLDoc();

		Element credentials = doc.getDocumentElement();
		Element userTag = doc.createElement(root + "User");
		credentials.appendChild(userTag);
		Element username = doc.createElement("username");
		Element password = doc.createElement("password");
		userTag.appendChild(username);
		userTag.appendChild(password);
		username.appendChild(doc.createTextNode(user));
		password.appendChild(doc.createTextNode(pass));

		if (token != null) {
			Element token1 = doc.createElement("token");
			userTag.appendChild(token1);
			token1.appendChild(doc.createTextNode(token));
		}

		writeXML(doc);
	}

	public Document getXMLDoc() {
		Document doc = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File("config.xml"));
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

	public void writeXML(Document doc) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	private String getToken(String root, String user, String pass) {
		Document doc = getXMLDoc();

		NodeList listOfUsers = doc.getElementsByTagName(type + "User");
		for (int i = 0; i < listOfUsers.getLength(); i++) {
			Node userNode = listOfUsers.item(i);
			if (userNode.getNodeType() == Node.ELEMENT_NODE) {
				Element usernameElement = (Element) ((Element) userNode).getElementsByTagName("username").item(0);
				String username = usernameElement.getChildNodes().item(0).getNodeValue().trim();
				if (username.equals(user)) {
					Element passwordElement = (Element) ((Element) userNode).getElementsByTagName("password").item(0);
					String password = passwordElement.getChildNodes().item(0).getNodeValue().trim();
					if (password.equals(pass)) {
						Element tokenElement = (Element) ((Element) userNode).getElementsByTagName("token").item(0);
						return tokenElement.getChildNodes().item(0).getNodeValue().trim();
					}
				}
			}
		}

		return null;

	}

}
