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
