package bda;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.mail.MessagingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;


import ContentHandlers.FetchEmails;
import ContentHandlers.FetchPosts;
import ContentHandlers.FetchTweets;

/**
 * Cria uma JFrame representativa da interface referente apenas ao conteúdo
 * selecionado
 * 
 * @author Grupo 29
 * @version 4.0
 */
public class ContentGUI {

	private JFrame frame;
	@SuppressWarnings("unused")
	private GUI i;
	private Content content;
	private final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int width = (int) (0.7 * screenDim.width);
	private final int height = (int) (0.9 * screenDim.height);
	private final int widthLocation = screenDim.width / 2 - width / 2;
	private final int heightLocation = screenDim.height / 2 - height / 2;

	private JPanel north = new JPanel();
	private JPanel center = new JPanel();
	private JPanel south = new JPanel();
	private JButton reply;
	private JTextArea text;
	private JButton send;

	private Object id;
	private String groupID;
	private ArrayList<Object> handlers = new ArrayList<>();

	/**
	 * Construtor de uma interface referente ao conteúdo
	 * 
	 * @param content	Content que será exibido na interface
	 * @param i		GUI
	 * @throws HeadlessException
	 * @throws MessagingException
	 */
	public ContentGUI(Content content, GUI i) throws HeadlessException, MessagingException {
		frame = new JFrame(content.getType());
		this.i = i;
		this.content = content;
		handlers = ((BDATableModel) i.getInboxTable().getModel()).getContentHandlers();
		frame.setSize(width, height);
		frame.setLocation(widthLocation, heightLocation);
		frame.setLayout(new BorderLayout());
		north.setLayout(new BorderLayout());
		north.setPreferredSize(new Dimension(0, 60));

		String from = null;
		String date = null;
		try {
			from = content.getFrom();
			date = content.getDate();
		} catch (IOException e) {
			from = "Unknown";
			date = "Unknown";
		}

		JPanel fromPanel = new JPanel();
		JLabel fromTag = new JLabel("  From:  ");
		fromTag.setForeground(new Color(146, 172, 245));
		fromTag.setFont(new Font("SansSerif", Font.PLAIN, 21));
		JLabel fromText = new JLabel(from);
		fromText.setFont(new Font("SansSerif", Font.PLAIN, 21));
		fromPanel.add(fromTag);
		fromPanel.add(fromText);
		JLabel dateLabel = new JLabel(date + "  ");
		dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		north.add(dateLabel, BorderLayout.EAST);
		north.add(fromPanel, BorderLayout.WEST);
		center.setLayout(new BorderLayout());
		south.setLayout(new BorderLayout());
		text = new JTextArea();
		text.setFont(new Font("SansSerif", Font.PLAIN, 19));

		ImageIcon icon = new ImageIcon("images/" + "res" + ".png");
		Image image = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);

		JPanel buttons = new JPanel();
		reply = new JButton("Reply", icon);
		reply.setPreferredSize(new Dimension(50, 50));
		addReplyOperations(from.toString());

		if (content.getType().equals("Twitter")) {

			JButton retweet = new JButton("Retweet");
			reply.setPreferredSize(new Dimension((int) (width * 0.2), (int) (height * 0.05)));
			retweet.setPreferredSize(new Dimension((int) (width * 0.2), (int) (height * 0.05)));
			addRetweetOperation(retweet);
			buttons.add(reply);
			buttons.add(retweet);
			center.add(buttons, BorderLayout.SOUTH);
		} else {
			if (content.getType().equals("Facebook") || content.getType().equals("Facebook Group")) {
				JButton getLikesComments = new JButton("Likes and Comments");
				reply.setPreferredSize(new Dimension((int) (width * 0.2), (int) (height * 0.05)));
				reply.setText("Post");
				getLikesComments.setPreferredSize(new Dimension((int) (width * 0.2), (int) (height * 0.05)));
				buttons.add(reply);
				buttons.add(getLikesComments);
				addFacebookOperations(getLikesComments);
				center.add(buttons, BorderLayout.SOUTH);
				if (content.getType().equals("Facebook"))
					reply.setEnabled(false);
			} else {
				center.add(reply, BorderLayout.SOUTH);
			}

		}

		JPanel message = new JPanel();
		message.setLayout(new BorderLayout());

		JEditorPane t = new JEditorPane();
		t.setEditable(false);
		t.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollInbox = new JScrollPane(t);
		scrollInbox.setViewportView(t);
		scrollInbox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		JPanel subjectPanel = new JPanel();
		JLabel subjectTag = new JLabel("    Assunto:  ");
		subjectTag.setForeground(new Color(146, 172, 245));
		subjectTag.setFont(new Font("SansSerif", Font.PLAIN, 21));
		JLabel subjectText = new JLabel();
		subjectText.setFont(new Font("SansSerif", Font.PLAIN, 21));
		subjectPanel.add(subjectTag);
		subjectPanel.add(subjectText);

		message.add(subjectPanel, BorderLayout.NORTH);
		message.add(scrollInbox, BorderLayout.CENTER);
		center.add(message);
		scrollInbox.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		String hash = null;
		try {
			hash = content.getHashCode();
			if (content.getType().equals("Email"))
				hash = "Resources\\Emails\\Email" + hash;
			if (content.getType().equals("Twitter"))
				hash = "Resources\\Tweets\\Tweet" + hash;
			if (content.getType().equals("Facebook"))
				hash = "Resources\\Posts\\Post" + hash;
			if (content.getType().equals("Facebook Group"))
				hash = "Resources\\GroupPosts\\Post" + hash;

			Scanner scanner = new Scanner(
					new FileReader(new File(System.getProperty("user.dir") + File.separator + hash)));

			if (content.getType().equals("Facebook Group")) {
				String[] line = scanner.nextLine().split(" ");
				id = line[0];
				groupID = line[1];
			} else {
				id = scanner.nextLine();
			}
			scanner.nextLine(); // type
			scanner.nextLine(); // username
			scanner.nextLine(); // date
			scanner.nextLine(); // from
			String s;
			t.setText(t.getText() + "\n");
			subjectText.setText(scanner.nextLine() + "\n" + "\n");
			while (scanner.hasNextLine()) {
				s = scanner.nextLine();
				t.setText(t.getText() + " " + s + "\n");
			}
			scanner.close();
			t.setCaretPosition(0);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		frame.add(north, BorderLayout.NORTH);
		frame.add(center, BorderLayout.CENTER);
		frame.add(south, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	/**
	 * Método que possibilita efetuar um retweet através de um botão
	 * 
	 * @param retweet JButton que permite o retweet
	 */
	private void addRetweetOperation(JButton retweet) {
		retweet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FetchTweets twitter = null;
				for (Object cH : handlers) {
					if (cH instanceof FetchTweets)
						twitter = (FetchTweets) cH;
				}
				if (twitter != null) {
					twitter.retweet(Long.parseLong((String) id));
					showMessage(true);
				} else {
					showMessage(false);
				}

			}
		});

	}

	/**
	 * Método que possiblilita visualizar o número de likes e comentários, 
	 * assim como os respectivos comentários através de um botão
	 * 
	 * @param getLikesComments JButton que permite obter o nº de likes e comnetários
	 */
	private void addFacebookOperations(JButton getLikesComments) {
		getLikesComments.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				south.removeAll();
				south.setPreferredSize(new Dimension(200, 200));

				text.setPreferredSize(new Dimension(300, 300));
				text.setEditable(false);
				south.add(text, BorderLayout.CENTER);

				long likesCount = 0;
				long commentsCount = 0;
				HashMap<String, String> comments = new HashMap<>();
				FetchPosts facebook = null;
				for (Object cH : handlers) {
					if (cH instanceof FetchPosts)
						facebook = (FetchPosts) cH;
				}

				likesCount = facebook.getLikesCount((String) id);
				commentsCount = facebook.getCommentsCount((String) id);
				comments = facebook.getComments((String) id);
				text.setText("Número de likes:  " + likesCount + "\n" + "\n");
				text.append("  Comentários:    " + commentsCount + "\n");

				try {
					Highlighter highlighter = text.getHighlighter();
					HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(186, 210, 243));
					int p0 = text.getText().indexOf("Número de likes:");
					int p1 = p0 + "Número de likes:".length();
					int p2 = text.getText().indexOf("Comentários:");
					int p3 = p2 + "     Comentários: ".length();
					highlighter.addHighlight(p0, p1, painter);
					highlighter.addHighlight(p2-4, p3-3, painter);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

				for (HashMap.Entry<String, String> entry : comments.entrySet()) {
					text.append("\n" + "User Commented:  " + entry.getValue() + "\n");
					text.append(entry.getKey() + "\n" + "\n");
				}
				south.updateUI();
				frame.repaint();

			}
		});

	}

	/**
	 * Método que possibilita a resposta por parte do utilizador para o remetente do
	 * conteúdo
	 * 
	 * @param from String (remetente do conteúdo)
	 */
	private void addReplyOperations(String from) {

		reply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				south.removeAll();
				south.setPreferredSize(new Dimension(200, 200));

				text.setPreferredSize(new Dimension(300, 300));
				text.setEditable(true);
				ImageIcon icon = new ImageIcon("images/s.png");
				Image image = icon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);

				send = new JButton("Send", icon);
				send.setPreferredSize(new Dimension(200, 50));
				send.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (content.getType().equals("Email")) {
							FetchEmails email = null;
							for (Object cH : handlers) {
								if (cH instanceof FetchEmails)
									email = (FetchEmails) cH;
							}
							if (email != null) {
								email.sendEmail(from, "es1.grupo29@gmail.com", "Subject", text.getText());
								showMessage(true);
							} else {
								showMessage(false);
							}

						} else {
							if (content.getType().equals("Twitter")) {
								FetchTweets twitter = null;
								for (Object cH : handlers) {
									if (cH instanceof FetchTweets)
										twitter = (FetchTweets) cH;
								}
								if (twitter != null) {
									twitter.replyTweet(Long.parseLong((String) id), text.getText());
									text.setText("");
									showMessage(true);
								} else {
									showMessage(false);
								}

							} else {
								if (content.getType().equals("Facebook Group")) {
									FetchPosts facebook = null;
									for (Object cH : handlers) {
										if (cH instanceof FetchPosts)
											facebook = (FetchPosts) cH;
									}
									if (facebook != null) {
										facebook.replyToPost(groupID, text.getText());
										showMessage(true);
									} else {
										showMessage(false);
									}
								}
							}
						}
					}
				});

				south.add(text, BorderLayout.CENTER);
				south.updateUI();
				JPanel n = new JPanel();
				n.setLayout(new BorderLayout());
				n.setPreferredSize(new Dimension(200, 50));
				n.add(send, BorderLayout.SOUTH);
				south.add(n, BorderLayout.EAST);

				frame.add(south, BorderLayout.SOUTH);
				frame.repaint();
			}
		});
	}

	/**
	 * Método que mostra uma JOptionPane de sucesso ou insucesso
	 * 
	 * @param success Boolean
	 */
	private void showMessage(boolean success) {
		if (success) {
			JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Operação não concretizada.", "Falha", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
