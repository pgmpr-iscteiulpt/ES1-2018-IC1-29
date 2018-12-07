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
import javax.mail.MessagingException;
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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ContentHandlers.FetchEmails;
import ContentHandlers.FetchPosts;
import ContentHandlers.FetchTweets;
import twitter4j.TwitterException;

/**
 * Implementa uma JPanel que está associada a uma interface e um botão
 * 
 * @author Grupo 29
 * @version 4.0
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

	/**
	 * Método que efetua o login e inicia sessão nos respectivos
	 * canais(online/offline)
	 */
	public void login() {

		String dir = null;
		int log = 0;
		switch (type) {
		case "Email":
			dir = "Resources\\Emails";
			log = 3;
			contentHandler = new FetchEmails();
			break;
		case "Twitter":
			dir = "Resources\\Tweets";
			log = 2;
			contentHandler = new FetchTweets();
			break;
		default:
			dir = "Resources\\Posts";
			log = 1;
			contentHandler = new FetchPosts();
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
			if (type.equals("Facebook")) {
				File folder1 = new File("Resources\\GroupPosts");
				File[] directoryListing1 = folder1.listFiles();
				for (File contentFile : directoryListing1) {
					if (!contentFile.getName().equals("Untitled")) {
						content.add(new Content(contentFile));
					}
				}
			}
			if (type.equals("Email"))
				try {
					((FetchEmails) contentHandler).connect(getUsername(), getPassword());
				} catch (MessagingException e) {
					JOptionPane.showMessageDialog(null,
							"Não foi possível ligar ao servico de Email. Algumas funcionalidades poderão não estar disponíveis",
							"Ligação sem sucesso", JOptionPane.INFORMATION_MESSAGE);
				}
			if (type.equals("Facebook") || type.equals("Facebook Group"))

				((FetchPosts) contentHandler).connect(getToken("Facebook", getUsername(), getPassword()));

			if (type.equals("Twitter")) {
				String[] token = getToken("Twitter", getUsername(), getPassword()).split(" ");
				((FetchTweets) contentHandler).connect(token[0], token[1], token[2], token[3]);
			}
			b.setLogged(true);
			i.getLog(log).setText(username);

		} else

		{

			if (type.equals("Email")) {

				try {
					((FetchEmails) contentHandler).connect(getUsername(), getPassword());
					((FetchEmails) contentHandler).checkMail();
					content = ((FetchEmails) contentHandler).getMsgs();
				} catch (Exception E) {
					JOptionPane.showMessageDialog(null, "Não foi possível ligar ao servico de Email",
							"Login sem sucesso", JOptionPane.ERROR_MESSAGE);
					nameField.setText("");
					passwordField.setText("");
					login.dispose();
					return;
				}
				b.setLogged(true);
				addXMLElement("Email", getUsername(), getPassword(), null);
				i.getLog(3).setText(nameField.getText());
			}
			if (type.equals("Twitter")) {
				login.dispose();
				String token = askForToken("Twitter");
				String[] tokens = token.split(" ");
				try {
					((FetchTweets) contentHandler).connect(tokens[0], tokens[1], tokens[2], tokens[3]);
					((FetchTweets) contentHandler).checkTweets();
					content = ((FetchTweets) contentHandler).getStatus();
				} catch (TwitterException | MessagingException | IOException e) {
					JOptionPane.showMessageDialog(null, "Não foi possível ligar ao servico Twitter",
							"Login sem sucesso", JOptionPane.ERROR_MESSAGE);
					return;
				}
				b.setLogged(true);
				addXMLElement("Twitter", getUsername(), getPassword(), token);
				i.getLog(2).setText(((FetchTweets) contentHandler).getUserName());

			}

			if (type.equals("Facebook")) {
				login.dispose();
				String token = askForToken("Facebook");

				try {
					((FetchPosts) contentHandler).connect(token);
					((FetchPosts) contentHandler).checkPosts();
					((FetchPosts) contentHandler).checkGroupPosts();
					content = ((FetchPosts) contentHandler).getPosts();
				} catch (MessagingException | IOException e) {
					JOptionPane.showMessageDialog(null, "Não foi possível ligar ao servico Facebook",
							"Login sem sucesso", JOptionPane.ERROR_MESSAGE);
					return;

				}
				b.setLogged(true);
				addXMLElement("Facebook", getUsername(), getPassword(), token);
				i.getLog(1).setText(((FetchPosts) contentHandler).getUserName());

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
			ArrayList<Content> tempList = new ArrayList<>();
			for (Content c : content) {
				tempList.add(c);
			}
			for (Content c : tempList) {
				((BDATableModel) i.getInboxTable().getModel()).addRow(c);
			}
			((AbstractTableModel) i.getInboxTable().getModel()).fireTableDataChanged();

		}

		ArrayList<Object> handlers = ((BDATableModel) i.getInboxTable().getModel()).getContentHandlers();
		if (!handlers.contains(contentHandler) && contentHandler != null)
			((BDATableModel) i.getInboxTable().getModel()).addContentHandler(contentHandler);
	}

	/**
	 * Método que pede ao utilizador o token correspondente à sua conta
	 * 
	 * @param icon String(icon)
	 * @return token String(token introduzido pelo utilizador)
	 */
	private String askForToken(String icon) {

		String message;
		if (icon.equals("Twitter")) {
			message = "           Introduza os 4 tokens de acesso a esta conta, separados por um espa�o"
					+ System.lineSeparator() + "                   (apenas terá de o fazer a primeira vez).";
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
		System.out.println("enntrei");
		ArrayList<Content> remove = new ArrayList<Content>();

		if (type.equals("Twitter"))
			i.getLog(2).setText("Not Logged In   ");

		if (type.equals("Facebook") || type.equals("Facebook Group"))
			i.getLog(1).setText("Not Logged In   ");

		if (type.equals("Email"))
			i.getLog(3).setText("Not Logged In   ");

		if (!(i.getInboxTable().getModel() instanceof BDATableModel)) {
			JOptionPane.showMessageDialog(null, "Impossível efetuar operações sem fazer login primeiro", "Erro",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			for (Content c : ((BDATableModel) i.getInboxTable().getModel()).getTableContent()) {
				if ((c.getType().equals(type)) || (type.equals("Facebook") && c.getType().equals("Facebook Group")))
					remove.add(c);
			}
			for (Content r : remove) {
				((BDATableModel) i.getInboxTable().getModel()).getTableContent().remove(r);
			}
		}
		remove.clear();
		((AbstractTableModel) i.getInboxTable().getModel()).fireTableDataChanged();

		b.changeImage();
		b.changeState();

	}

	/**
	 * Método que permite confirmar se o utilizador já possui dados de autenticação
	 * guardados
	 * 
	 * @param type String(tipo de serviço)
	 * @param user String(utilizador)
	 * @param pass String(password)
	 * @return boolean confirmação
	 */
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

	/**
	 * Método que adiciona um conjunto de dados relativos a um utilizador ao
	 * ficheiro xml
	 * 
	 * @param root  String(tipo de serviço)
	 * @param user  String(utilizador)
	 * @param pass  String(password)
	 * @param token String(token)
	 */
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

	/**
	 * Método que abre o documento XML e prepara-o para leitura
	 * 
	 * @return Document
	 */
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

	/**
	 * Método que escreve no documento XML
	 * 
	 * @param doc Document
	 */
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

	/**
	 * Método que acede ao documento XML e devolve o token referente ao respectivo
	 * serviço e utilizador
	 * 
	 * @param root String(tipo de serviço)
	 * @param user String(utilizador)
	 * @param pass String(password)
	 * @return String(token)
	 */
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
