package bda;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

/**
 * Implementa um botão a que será atribuído uma interface, imagem e
 * funcionalidade
 * 
 * @author Grupo 29
 * @version 1.0
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
	private boolean notLogged = false;

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
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!notLogged) {
					login.open();
					notLogged = true;
				} else {
					login.logout();
					changeImage();
					changeState();
					notLogged = false;
				}
			}
		});
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
		addOperations(i);
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

	public String getIconName() {
		return iconName;
	}

	/**
	 * Método que ordena cronologicamente a informação da inbox
	 */
	public void addOperations(GUI i) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Content> a = ((BDATableModel) i.getInboxTable().getModel()).getTableContent();
				if (order) {
		//			Collections.sort(a, new DateComparator());
				} else {
		//			Collections.sort(a, Collections.reverseOrder(new DateComparator()));
				}
				((AbstractTableModel) i.getInboxTable().getModel()).fireTableDataChanged();

			}
		});
	}

}
