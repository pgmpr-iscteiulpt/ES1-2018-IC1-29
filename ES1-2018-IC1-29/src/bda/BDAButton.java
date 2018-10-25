package bda;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BDAButton {

	private JButton button;
	private boolean state;
	private String iconName;
	private Login login;
	private boolean order;
	private boolean notLogged = false;

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

	public BDAButton(Interface i, String iconName, boolean order ) {
		this.order = order;
		this.iconName = iconName;
		button = new JButton(getIconButton(iconName));
		button.setPreferredSize(new Dimension(30, 30));
		addOperations();
	}

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

	public void changeImage() {
		if (state) {
			button.setIcon(getIconButton(iconName + "Off"));
		} else {
			button.setIcon(getIconButton(iconName + "On"));
		}
	}

	public void changeState() {
		if (state) {
			state = false;
		} else {
			state = true;
		}
	}

	public JButton getButton() {
		return button;
	}

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
