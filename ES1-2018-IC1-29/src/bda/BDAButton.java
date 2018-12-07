package bda;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implementa um botão a que será atribuído uma interface, imagem e
 * funcionalidade
 * 
 * @author Grupo 29
 * @version 4.0
 */

public class BDAButton {

	// Constants
	private final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int buttonWidth = (int) (0.035 * screenDim.width);
	private final int buttonHeight = (int) (0.063 * screenDim.height);

	private JButton button;
	private boolean state;
	private String iconName;
	private Login login;
	private boolean order;
	private boolean logged = false;

	/**
	 * Construtor de um botão que possui uma interface e uma imagem
	 * 
	 * @param i        Interface a que pertence o botão
	 * @param iconName Nome da imagem que possuirá o botão
	 */
	public BDAButton(GUI i, String iconName) {
		this.iconName = iconName;
		login = new Login(i, this);
		button = new JButton(getIconButton(iconName + "Off"));

		button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		addLogOperations();

	}

	/**
	 * Construtor de um botão que possui uma interface, uma imagem e uma ordem
	 * 
	 * @param i        Interface a que pertence o botão
	 * @param iconName Nome da imagem que possuirá o botão
	 * @param order    Ordem pela qual é caracterizado o botão
	 */
	public BDAButton(GUI i, String iconName, boolean order) {
		this.order = order;
		this.iconName = iconName;
		button = new JButton(getIconButton(iconName));
		button.setPreferredSize(new Dimension((int) (buttonWidth * 0.5), (int) (buttonHeight * 0.5)));
		addSortOperations(i);
	}

	/**
	 * Método que devolve uma ImageIcon
	 * 
	 * @param n Nome que representa a imagem
	 * @return ImagemIcon (imagem do botão)
	 */
	public ImageIcon getIconButton(String n) {

		ImageIcon icon = new ImageIcon("images/" + n + ".png");
		Image image = null;
		if (n.equals("recent") || n.equals("old")) {
			image = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		} else {
			image = icon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		}
		icon = new ImageIcon(image);

		return icon;
	}

	/**
	 * Método que altera a imagem do botão conforme o seu estado
	 */
	public void changeImage() {
		if (state) {
			button.setIcon(getIconButton(iconName + "Off"));
		} else {
			button.setIcon(getIconButton(iconName + "On"));
		}
	}

	/**
	 * Método que altera o estado do botão
	 */
	public void changeState() {
		if (state) {
			state = false;
		} else {
			state = true;
		}
	}

	/**
	 * Método que devolve um botão
	 * 
	 * @return JButton (this)
	 */
	public JButton getButton() {
		return button;
	}

	/**
	 * Método que devolve o estado de um botão
	 * 
	 * @return boolean (state)
	 */
	public boolean getState() {
		return state;
	}

	/**
	 * Método que devolve o nome de um botão
	 * 
	 * @return String (iconName)
	 */
	public String getIconName() {
		return iconName;
	}

	/**
	 * Método que adiciona as operações de Login/Termina a sessão
	 */
	private void addLogOperations() {

		button.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evnt) {

				if (SwingUtilities.isLeftMouseButton(evnt)) {
					if (!logged) {
						login.open();
						logged = true;
					} else {
						login.logout();
						logged = false;
					}
				}
				if (SwingUtilities.isRightMouseButton(evnt)) {
					UIManager.put("OptionPane.minimumSize", new Dimension(450, 130));
					String[] possibleValues = { "Terminar sessão", "Alterar dados de autenticação", "Cancelar" };
					if (logged == false) {
						JOptionPane.showMessageDialog(null, "Impossível efetuar operações sem fazer login primeiro",
								"Erro", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int value = JOptionPane.showOptionDialog(null,
							"Tem a certeza que pretende terminar sessão no " + iconName + "?", "Terminar sessão",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
							getIconButton(iconName + "On"), possibleValues, possibleValues[1]);
					if (value == JOptionPane.YES_OPTION) {
						File dir = null;
						switch (iconName) {
						case "Email":
							dir = new File("Resources/Emails");
							break;
						case "Twitter":
							dir = new File("Resources/Tweets");
							break;
						case "Facebook":
							dir = new File("Resources/Posts");
							for (File file : new File("Resources/GroupPosts").listFiles())
								if (!file.getName().equals("Untitled"))
									file.delete();
							break;
						}

						for (File file : dir.listFiles())
							if (!file.getName().equals("Untitled"))
								file.delete();

						login.logout();
						logged = false;
						deleteFromXML(iconName, login.getUsername());
					}
					if (value == JOptionPane.NO_OPTION) {
						UIManager.put("OptionPane.minimumSize", new Dimension(450, 130));
						String[] values;
						if (iconName.equals("Email")) {
							String[] aux = { "Username", "Password", "Token" };
							values = aux;
						} else {
							String[] aux = { "Username", "Password", "Token" };
							values = aux;
						}
						String selectedValue = (String) JOptionPane.showInputDialog(null,
								"Escolha o atributo que pretende alterar.", "Alterar Dados",
								JOptionPane.OK_CANCEL_OPTION, getIconButton(iconName + "On"), values, values[0]);

						String input = JOptionPane.showInputDialog("Insira o novo valor para o " + selectedValue);
						updateXML(iconName, login.getUsername(), selectedValue.toLowerCase(), input);
					}
				}
			}

		});

	}

	/**
	 * Método que ordena cronologicamente a informação da inbox
	 * 
	 * @param i Interface a que pertence o botão
	 */
	private void addSortOperations(GUI i) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Content> a = ((BDATableModel) i.getInboxTable().getModel()).getTableContent();
				if (order) {
					Collections.sort(a, new DateComparator());
				} else {
					Collections.sort(a, Collections.reverseOrder(new DateComparator()));
				}
				((AbstractTableModel) i.getInboxTable().getModel()).fireTableDataChanged();

			}
		});
	}

	/**
	 * Método que remove do ficheiro XML os dados associados a uma determinada conta
	 * do utilizador
	 */
	private void deleteFromXML(String root, String user) {
		Document doc = login.getXMLDoc();

		NodeList nodes = doc.getElementsByTagName(root + "User");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element user1 = (Element) nodes.item(i);
			String username = (String) user1.getElementsByTagName("username").item(0).getTextContent();
			if (username.equals(user)) {
				user1.getParentNode().removeChild(user1);
			}

		}

		login.writeXML(doc);
		JOptionPane.showMessageDialog(null,
				"Todos os dados e ficheiros associados à sua conta de " + root + " foram removidos do sistema.",
				"Sessão terminada", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Método que efetua o update do token/username/password
	 * 
	 * @param root  String(tipo de serviço - Email/Facebook/Twitter)
	 * @param user  String(utilizador)
	 * @param type  String(tipo de dado a alterar - Token/Password/Username)
	 * @param input String(valor da alteração pretendida)
	 */
	private void updateXML(String root, String user, String type, String input) {
		Document doc = login.getXMLDoc();

		NodeList listOfUsers = doc.getElementsByTagName(root + "User");
		for (int i = 0; i < listOfUsers.getLength(); i++) {
			Node search = doc.getElementsByTagName("username").item(i);
			if (search.getTextContent().equals(user)) {
				Node change = doc.getElementsByTagName(type).item(i);
				change.setTextContent(input);
			}
		}

		login.writeXML(doc);

	}

	/**
	 * Método que muda o estado do logIn
	 * 
	 * @param a
	 */
	public void setLogged(boolean a) {
		logged = a;
	}

}
