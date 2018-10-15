package bda;

import java.awt.*;
import javax.mail.Message;
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
	private final int subjectColumnWidth = (int) (0.36 * width); // 0.4 *
																	// inboxSize.width

	// Panels
	private JPanel inbox = new JPanel();
	private JPanel header = new JPanel();

	// Email authentication
	private String username = "es1.grupo29@gmail.com";
	private String password = "tenhosono";

	public Interface() {

		// size and centering
		frame.setSize(width, height);
		frame.setLocation(widthLocation, heightLocation);
		frame.setLayout(new BorderLayout());
		
		//header
		header.setLayout(new BorderLayout());
		
		JTextField c1 = new JTextField("Bom Dia Academia");
		c1.setEditable(false);
		c1.setHorizontalAlignment(JTextField.CENTER);
		header.add(c1, BorderLayout.NORTH);
		
		JPanel subheader = new JPanel();	
		subheader.setLayout(new FlowLayout());
		JButton button1 = new JButton("Facebook");
		JButton button2 = new JButton("Twitter");
		JButton button3 = new JButton("Email");
		subheader.add(button1);
		subheader.add(button2);
		subheader.add(button3);
		
		header.add(subheader, BorderLayout.CENTER);
		
		JPanel subheader2 = new JPanel();
		subheader2.setLayout(new GridLayout(3,1));
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

		// Fetch emails
		FetchEmails emails = new FetchEmails();
		emails.checkMail(username, password);
		Message[] msgs = emails.getMsgs();

		// Build table
		JTable inboxTable = new JTable(new BDATableModel(msgs));
		JScrollPane scrollInbox = new JScrollPane(inboxTable);
		scrollInbox.setViewportView(inboxTable);
		scrollInbox.setPreferredSize(inboxSize);
		inboxTable.setFillsViewportHeight(true);
		inboxTable.getColumnModel().getColumn(3).setPreferredWidth(subjectColumnWidth);

		inbox.add(scrollInbox);
		header.setPreferredSize(headerSize);

		frame.add(inbox, BorderLayout.CENTER);
		frame.add(header, BorderLayout.NORTH);

		frame.setVisible(true);

	}

	public static void main(String[] args) {
		new Interface();

	}

}
