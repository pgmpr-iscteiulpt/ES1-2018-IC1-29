package bda;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	// Buttons
	private boolean f = true;
	private boolean em = true;
	private boolean t = true;
	private JButton facebook = new JButton(getIconButton("faceOn"));
	private JButton mail = new JButton(getIconButton("emailOn"));
	private JButton twitter = new JButton(getIconButton("twitterOn"));
	private JPanel subheader = new JPanel();

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


		subheader.setLayout(new FlowLayout());
		//		JButton button1 = new JButton("Facebook");
		//		JButton button2 = new JButton("Twitter");
		//		JButton button3 = new JButton("Email");
		//		subheader.add(button1);
		//		subheader.add(button2);
		//		subheader.add(button3);

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

		// Methods 
		addButtons();
		addLoginDialogListeners();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

	}


	private void addButtons() {
		facebook.setPreferredSize(new Dimension(70, 70));
		mail.setPreferredSize(new Dimension(70, 70));
		twitter.setPreferredSize(new Dimension(70, 70));

		subheader.add(facebook);
		subheader.add(mail);
		subheader.add(twitter);
	}

	private void addLoginDialogListeners() {
		facebook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeImage(facebook, f, "face");
				changeStateF();
			}
		});

		mail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeImage(mail, em, "email");
				changeStateE();
			}
		});

		twitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeImage(twitter, t, "twitter");
				changeStateT();
			}
		});
	}

	public void changeImage(JButton j , boolean b , String n) {
		if (b) {
			j.setIcon(getIconButton(n + "Off"));
		} else {
			j.setIcon(getIconButton(n + "On"));
		}
	}

	public void changeStateF() {
		if (f) {
			f = false;
		} else {
			f = true;
		}
	}
	public void changeStateE() {
		if (em) {
			em = false;
		} else {
			em = true;
		}
	}
	public void changeStateT() {
		if (t) {
			t = false;
		} else {
			t = true;
		}
	}

	public ImageIcon getIconButton (String name) {

		ImageIcon icon = new ImageIcon("images/" + name + ".png");
		Image image = icon.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);;
		icon = new ImageIcon(image);

		return icon;

	}

	public static void main(String[] args) {
		new Interface();

	}

}
