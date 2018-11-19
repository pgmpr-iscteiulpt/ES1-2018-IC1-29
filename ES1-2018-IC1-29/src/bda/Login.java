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
import javax.mail.Message;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Implementa uma JPanel que está associada a uma interface e um botão
 * 
 * @author Grupo 29
 * @version 1.0
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
		try {
			FetchEmails emails = new FetchEmails();
			emails.checkMail(getUsername(), getPassword());
			Message[] msgs = emails.getMsgs();
			b.changeImage();
			b.changeState();
			i.getInboxTable().setModel(new BDATableModel(msgs));
			i.getInboxTable().getColumnModel().getColumn(3)
					.setPreferredWidth((int) (i.getInboxTable().getWidth() * 0.4));
			i.getLog3().setText("es1.grupo29@gmail.com");
			login.dispose();
		} catch (Exception E) {
			JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Login",
					JOptionPane.ERROR_MESSAGE);
			nameField.setText("");
			passwordField.setText("");
		}

	}

}
