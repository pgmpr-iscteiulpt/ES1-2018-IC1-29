package bda;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Implementa um bot�o a que ser� atribu�do uma interface, imagem e funcionalidade
 * @author Grupo 29
 * @version 1.0
 */

public class BDAButton {

	private JButton button;
	private boolean state;
	private String iconName;
	private Login login;
	private boolean order;
	private boolean notLogged = false;

	/**
	 * Construtor de um bot�o que possui uma interface e uma imagem
	 * @param i Interface a que pertence o bot�o
	 * @param iconName Nome da imagem que possuir� o bot�o
	 */
	public BDAButton(Interface i, String iconName) {
		this.iconName = iconName;
		login = new Login(i, this);
		button = new JButton(getIconButton(iconName + "Off"));

		button.setPreferredSize(new Dimension(60, 60));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!notLogged) {
					login.open();
					notLogged=true;
				} else {
					changeImage();
					changeState();
				}
			}
		});
	}
	
	/**
	 * Construtor de um bot�o que possui uma interface, uma imagem e uma ordem
	 * @param i Interface a que pertence o bot�o
	 * @param iconName Nome da imagem que possuir� o bot�o
	 * @param order Ordem pela qual � caracterizado o bot�o
	 */
	public BDAButton(Interface i, String iconName, boolean order ) {
		this.order = order;
		this.iconName = iconName;
		button = new JButton(getIconButton(iconName));
		button.setPreferredSize(new Dimension(30, 30));
		addOperations();
	}

	/**
	 * M�todo que devolve uma ImageIcon 
	 * @param n Nome que representa a imagem
	 * @return ImagemIcon (bot�o)
	 */
	public ImageIcon getIconButton(String n) {

		ImageIcon icon = new ImageIcon("images/" + n + ".png");
		Image image = null;
		if ( n.equals("recent") || n.equals("old")) {
			image = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		} else {
			image = icon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);		
		}
		icon = new ImageIcon(image);

		return icon;
	}

	/**
	 * M�todo que altera a imagem do bot�o conforme o seu estado
	 */
	public void changeImage() {
		if (state) {
			button.setIcon(getIconButton(iconName + "Off"));
		} else {
			button.setIcon(getIconButton(iconName + "On"));
		}
	}

	/**
	 * M�todo que altera o estado do bot�o
	 */
	public void changeState() {
		if (state) {
			state = false;
		} else {
			state = true;
		}
	}

	/**
	 * M�todo que devolve um bot�o
	 * @return JButton (this)
	 */
	public JButton getButton() {
		return button;
	}
 
	/**
	 * M�todo que ordena cronologicamente a informa��o da inbox
	 */
	public void addOperations() {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (order) {
					//crescente
				} else {
					//decrescente
				}
			}
		});
	}

}
