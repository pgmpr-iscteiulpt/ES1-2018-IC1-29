package bda;

import java.awt.*;
import javax.swing.*;

public class Interface {

	private JFrame frame = new JFrame("BDA");
	private final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int width = (int) (0.7 * screenDim.width);
	private final int height = (int) (0.9 * screenDim.height);
	private final Dimension inboxSize = new Dimension((int) (0.9 * width), (int) (0.7 * height));
	private final Dimension headerSize = new Dimension(width, (int) (0.2 * height));
	private final int widthLocation = screenDim.width / 2 - width / 2;
	private final int heightLocation = screenDim.height / 2 - height / 2;

	// Buttons
	private BDAButton facebookButton = new BDAButton(this, "face");
	private BDAButton mailButton = new BDAButton(this, "email");
	private BDAButton twitterButton = new BDAButton(this, "twitter");
	private BDAButton recent = new BDAButton(this,"recent",true);
	private BDAButton old = new BDAButton(this,"old",false);
	private JTable inboxTable;

	// TextFields
	private JTextField i1 = new JTextField("Not Logged In   ");
	private JTextField i2 = new JTextField("Not Logged In   ");
	private JTextField i3 = new JTextField("Not Logged In   ");

	// Panels
	private JPanel inbox = new JPanel();
	private JPanel header = new JPanel();

	public Interface() {

		// size and centering
		frame.setSize(width, height);
		frame.setLocation(widthLocation, heightLocation);
		frame.setLayout(new BorderLayout());

		// header
		header.setLayout(new BorderLayout());
		header.setPreferredSize(headerSize);
		frame.add(header, BorderLayout.NORTH);

		// subHeader1
		JPanel subheader1 = new JPanel();
		
		ImageIcon logoImage = new ImageIcon("images/" + "logotransparent" + ".png");
		Image image = logoImage.getImage().getScaledInstance(408, 115, java.awt.Image.SCALE_SMOOTH);
		logoImage = new ImageIcon(image);
		JLabel logo = new JLabel(logoImage);	
		subheader1.add(logo, BorderLayout.WEST);
		logo.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 580)); 

		JPanel account = new JPanel();
		account.setLayout(new GridLayout(3, 1));
		i1.setEditable(false);
		i2.setEditable(false);
		i3.setEditable(false);
		ImageIcon face = new ImageIcon("images/" + "faceOn" + ".png");
		Image imageFace = face.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		ImageIcon twitter = new ImageIcon("images/" + "twitterOn" + ".png");
		Image imageTwitter = twitter.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		ImageIcon email = new ImageIcon("images/" + "emailOn" + ".png");
		Image imageMail = email.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);

		JLabel labelFace = new JLabel(new ImageIcon(imageFace));
		account.add(labelFace);
		account.add(i1);
		JLabel labelTwitter = new JLabel(new ImageIcon(imageTwitter));
		account.add(labelTwitter);
		account.add(i2);
		JLabel labelMail = new JLabel(new ImageIcon(imageMail));
		account.add(labelMail);
		account.add(i3);
		account.setPreferredSize( new Dimension( 290, 75 ) );
		subheader1.add(account,BorderLayout.EAST);

		header.add(subheader1, BorderLayout.NORTH);
		
		// subHeader2
		JPanel subheader2 = new JPanel();
		
		JPanel button = new JPanel();
		button.setLayout(new FlowLayout());
		button.add(facebookButton.getButton());
		button.add(mailButton.getButton());
		button.add(twitterButton.getButton());
		subheader2.add(button, BorderLayout.WEST);
		button.setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 800)); 
		
		JPanel chronology = new JPanel();
		chronology.setLayout(new FlowLayout());
		chronology.add(recent.getButton());
		chronology.add(old.getButton());	
		subheader2.add(chronology, BorderLayout.EAST);
		
		header.add(subheader2, BorderLayout.CENTER);

		// Build table
		inboxTable = new JTable();
		JScrollPane scrollInbox = new JScrollPane(inboxTable);
		scrollInbox.setViewportView(inboxTable);
		scrollInbox.setPreferredSize(inboxSize);
		inboxTable.setFillsViewportHeight(true);
		inbox.add(scrollInbox);
		frame.add(inbox, BorderLayout.CENTER);

	}
	
	public void open() {	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public JTextField getI3() {
		return i3;
	}

	public JTable getInboxTable() {
		return inboxTable;
	}

	public static void main(String[] args) {
		Interface i = new Interface();
		i.open();
	}

}
