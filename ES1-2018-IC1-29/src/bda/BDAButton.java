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

	public BDAButton(Interface i, String iconName) {
		this.iconName = iconName;
		login = new Login(i, this);
		button = new JButton(getIconButton(iconName + "Off"));

		button.setPreferredSize(new Dimension(70, 70));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				login.open();
			}
		});
	}

	public ImageIcon getIconButton(String n) {

		ImageIcon icon = new ImageIcon("images/" + n + ".png");
		Image image = icon.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
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

}
