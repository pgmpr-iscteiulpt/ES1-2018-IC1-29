package bda;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ContentGUI {

	private JFrame frame;
	private final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int width = (int) (0.7 * screenDim.width);
	private final int height = (int) (0.9 * screenDim.height);
	private final int widthLocation = screenDim.width / 2 - width / 2;
	private final int heightLocation = screenDim.height / 2 - height / 2;

	private JPanel north = new JPanel();
	private JPanel center = new JPanel();
	private JPanel south = new JPanel();
	private JButton reply;
	private JLabel sender;
	private JTextArea text;
	private JButton send;

	public ContentGUI(Content content) throws HeadlessException, MessagingException {
		frame = new JFrame(content.getType());
		frame.setSize(width, height);
		frame.setLocation(widthLocation, heightLocation);
		frame.setLayout(new BorderLayout());
		north.setLayout(new BorderLayout());
		north.setPreferredSize(new Dimension(0, 60));

		String from = null;
		try {
			from = content.getFrom();
		} catch (IOException e) {
			from = "Unknown";
		}

		sender = new JLabel("From:   " + from);
		sender.setFont(new Font("Arial", Font.PLAIN, 18));
		north.add(sender, BorderLayout.WEST);
		center.setLayout(new BorderLayout());
		south.setLayout(new BorderLayout());
		south.setPreferredSize(new Dimension(0, 0));

		ImageIcon icon = new ImageIcon("images/" + "res" + ".png");
		Image image = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);

		reply = new JButton("Reply", icon);
		reply.setPreferredSize(new Dimension(50, 50));
		center.add(reply, BorderLayout.SOUTH);
		// south.add(reply , BorderLayout.NORTH);
		addOperations(); // *

		JTextArea t = new JTextArea();
		t.setEditable(false);
		t.setFont(new Font("Arial", Font.PLAIN, 14));
		center.add(t);
		String hash = null;
		try {
			hash = content.getHashCode();
			if (content.getType().equals("email"))
				hash = "Emails\\Email" + hash;
			else
				hash = "Tweets\\Tweet" + hash;
			Scanner scanner = new Scanner(
					new FileReader(new File(System.getProperty("user.dir") + File.separator + hash)));
			String s;
			t.append("\n");
			t.append("\n" + "ASSUNTO:   " + content.getSubject().toString() + "\n" + "\n");
			while (scanner.hasNextLine()) {
				s = scanner.nextLine();
				t.append(" " + s + "\n");
			}
			scanner.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		frame.add(north, BorderLayout.NORTH);
		frame.add(center, BorderLayout.CENTER);
		frame.add(south, BorderLayout.SOUTH); 
		frame.setVisible(true);
	}

	public void addOperations() {
		reply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				south.setPreferredSize(new Dimension(200, 200));

				text = new JTextArea();
				text.setPreferredSize(new Dimension(300, 300));

				ImageIcon icon = new ImageIcon("images/s.png");
				Image image = icon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);

				send = new JButton("Send", icon);
				send.setPreferredSize(new Dimension(200, 50));

				south.add(text, BorderLayout.CENTER);
				JPanel n = new JPanel();
				n.setLayout(new BorderLayout());
				n.setPreferredSize(new Dimension(200, 50));
				n.add(send, BorderLayout.SOUTH);
				south.add(n, BorderLayout.EAST);

				frame.add(south, BorderLayout.SOUTH);
				frame.invalidate();
				frame.validate();
				frame.repaint();
			}
		});
	}

}
