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
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import com.restfb.types.Post;

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

		if (type.equals("Email")) {
			try {
				FetchEmails email = new FetchEmails();
				email.checkMail(getUsername(), getPassword());
				content = email.getMsgs();
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
			FetchTweets twitter = new FetchTweets();
			twitter.checkTweets();
			content = twitter.getStatus();
			i.getLog(2).setText("@Grupo29");
			contentHandler = twitter;
		}
		
		if (type.equals("Facebook")) {
			FetchPosts facebook = new FetchPosts();
			facebook.checkPosts();
			content = facebook.getPosts();
			i.getLog(1).setText(facebook.getUserName());
			contentHandler = facebook;
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
	
	/**
	 * Método que permite o utilizador fazer Loggout
	 */
	public void logout() {
		ArrayList<Content> remove = new ArrayList<Content>();

		if (type.equals("Email"))
			i.getLog(3).setText("Not Logged In   ");

		if (type.equals("Twitter"))
			i.getLog(2).setText("Not Logged In   ");

		for (Content c : ((BDATableModel) i.getInboxTable().getModel()).getTableContent()) {
			if (c.getType().equals(type))
				remove.add(c);
		}

		for (Content r : remove) {
			((BDATableModel) i.getInboxTable().getModel()).getTableContent().remove(r);
		}
		remove.clear();
		((AbstractTableModel) i.getInboxTable().getModel()).fireTableDataChanged();

	}

}

