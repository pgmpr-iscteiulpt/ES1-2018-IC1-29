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
	private JTable inboxTable;

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
		JTextField c1 = new JTextField("Bom Dia Academia");
		c1.setEditable(false);
		c1.setHorizontalAlignment(JTextField.CENTER);
		header.add(c1, BorderLayout.NORTH);
		frame.add(header, BorderLayout.NORTH);

		// subHeader
		JPanel subheader = new JPanel();
		subheader.setLayout(new FlowLayout());
		subheader.add(facebookButton.getButton());
		subheader.add(mailButton.getButton());
		subheader.add(twitterButton.getButton());
		header.add(subheader, BorderLayout.CENTER);

		// subHeader2
		JPanel subheader2 = new JPanel();
		subheader2.setLayout(new GridLayout(3, 1));
		JTextField i1 = new JTextField("Fb: es29");
		JTextField i2 = new JTextField("Tw: ES29");
		JTextField i3 = new JTextField("Em: es29@gmail.com");
		i1.setEditable(false);
		i2.setEditable(false);
		i3.setEditable(false);
		subheader2.add(i1);
		subheader2.add(i2);
		subheader2.add(i3);
		header.add(subheader2, BorderLayout.EAST);

		// Build table
		inboxTable = new JTable();
		JScrollPane scrollInbox = new JScrollPane(inboxTable);
		scrollInbox.setViewportView(inboxTable);
		scrollInbox.setPreferredSize(inboxSize);
		inboxTable.setFillsViewportHeight(true);
		inbox.add(scrollInbox);
		frame.add(inbox, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	public JTable getInboxTable() {
		return inboxTable;
	}

	public static void main(String[] args) {
		new Interface();

	}

}
