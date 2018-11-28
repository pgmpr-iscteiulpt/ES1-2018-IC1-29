package bda;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.swing.*;

/**
 * Cria uma JFrame representativa da interface de um software dedicado à
 * integração de informação académica com origem em vários sistemas
 * 
 * @author Grupo 29
 * @version 2.0
 */

public class GUI {

	// Constants
	private JFrame frame = new JFrame("BDA");
	private final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int width = (int) (0.7 * screenDim.width);
	private final int height = (int) (0.9 * screenDim.height);
	private final Dimension inboxSize = new Dimension((int) (0.9 * width), (int) (0.7 * height));
	private final Dimension headerSize = new Dimension(width, (int) (0.2 * height));
	private final Dimension accountsSize = new Dimension((int) (0.2 * width), (int) (0.2 * height));
	private final Dimension buttonsSize = new Dimension((int) (width * 0.5), (int) (0.08 * height));
	private final Dimension logSize = new Dimension((int) (0.15 * width), (int) (0.03 * height));
	private final Dimension logImagesSize = new Dimension((int) (0.02 * width), (int) (0.025 * height));
	private final int widthLocation = screenDim.width / 2 - width / 2;
	private final int heightLocation = screenDim.height / 2 - height / 2;

	// Buttons and table
	private BDAButton facebookButton = new BDAButton(this, "Facebook");
	private BDAButton mailButton = new BDAButton(this, "Email");
	private BDAButton twitterButton = new BDAButton(this, "Twitter");
	private BDAButton recent = new BDAButton(this, "recent", true);
	private BDAButton old = new BDAButton(this, "old", false);
	private JTable inboxTable;

	// TextFields
	private JTextField log1 = new JTextField("Not Logged In   ");
	private JTextField log2 = new JTextField("Not Logged In   ");
	private JTextField log3 = new JTextField("Not Logged In   ");

	// Panels
	private JPanel inbox = new JPanel();
	private JPanel header = new JPanel();
	private JPanel accountsPanel = new JPanel();
	private JPanel buttonsPanel = new JPanel();
	private JLabel logo;

	/**
	 * Construtor de uma Janela que possui toda a interface gráfica do software
	 */
	public GUI() {

		// size and centering
		frame.setSize(width, height);
		frame.setLocation(widthLocation, heightLocation);
		frame.setLayout(new BorderLayout());

		// header
		header.setLayout(new BorderLayout());
		header.setPreferredSize(headerSize);
		frame.add(header, BorderLayout.NORTH);

		// logo
		ImageIcon logoImage = new ImageIcon("images/logotransparent.png");
		Image image = logoImage.getImage().getScaledInstance((int) (0.3 * width), (int) (height * 0.1),
				java.awt.Image.SCALE_SMOOTH);
		logoImage = new ImageIcon(image);
		logo = new JLabel(logoImage);
		header.add(logo, BorderLayout.WEST);

		// accounts
		accountsPanel.setLayout(new GridLayout(3, 2));
		accountsPanel.setPreferredSize(accountsSize);
		log1.setEditable(false);
		log2.setEditable(false);
		log3.setEditable(false);
		log1.setPreferredSize(logSize);
		log2.setPreferredSize(logSize);
		log3.setPreferredSize(logSize);
		header.add(accountsPanel, BorderLayout.EAST);

		Image imageFace = new ImageIcon("images/FacebookOn.png").getImage().getScaledInstance(logImagesSize.width,
				logImagesSize.height, java.awt.Image.SCALE_SMOOTH);
		JPanel a = new JPanel();
		a.add(new JLabel(new ImageIcon(imageFace)));
		a.add(log1);
		accountsPanel.add(a);

		Image imageTwitter = new ImageIcon("images/TwitterOn.png").getImage().getScaledInstance(logImagesSize.width,
				logImagesSize.height, java.awt.Image.SCALE_SMOOTH);
		JPanel b = new JPanel();
		b.add(new JLabel(new ImageIcon(imageTwitter)));
		b.add(log2);
		accountsPanel.add(b);

		Image imageMail = new ImageIcon("images/EmailOn.png").getImage().getScaledInstance(logImagesSize.width,
				logImagesSize.height, java.awt.Image.SCALE_SMOOTH);
		JPanel c = new JPanel();
		c.add(new JLabel(new ImageIcon(imageMail)));
		c.add(log3);
		accountsPanel.add(c);

		// buttons
		buttonsPanel.setPreferredSize(buttonsSize);
		buttonsPanel.setLayout(new BorderLayout());
		header.add(buttonsPanel, BorderLayout.SOUTH);
		JPanel social = new JPanel();
		social.add(facebookButton.getButton());
		social.add(mailButton.getButton());
		social.add(twitterButton.getButton());
		buttonsPanel.add(social, BorderLayout.WEST);
		social.setBorder(BorderFactory.createEmptyBorder(0, (int) (width * 0.048), 0, 0));

		JPanel chronology = new JPanel();
		chronology.add(recent.getButton());
		chronology.add(old.getButton());
		buttonsPanel.add(chronology, BorderLayout.EAST);
		chronology.setBorder(BorderFactory.createEmptyBorder((int) (height * 0.04), 0, 0, (int) (width * 0.05)));

		// Build table
		inboxTable = new JTable();
		addTableListener(inboxTable);
		JScrollPane scrollInbox = new JScrollPane(inboxTable);
		scrollInbox.setViewportView(inboxTable);
		scrollInbox.setPreferredSize(inboxSize);
		inboxTable.setFillsViewportHeight(true);
		inbox.add(scrollInbox);
		frame.add(inbox, BorderLayout.CENTER);

	}


	/**
	 * Método que permite a visualização de uma nova Janela 
	 * referente apenas ao conteúdo selecionado
	 * @param t JTable
	 */
	private void addTableListener(JTable t) {
		t.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				BDATableModel model = (BDATableModel) t.getModel();
				try {
					new ContentGUI((Content) model.getMessageAt(t.getSelectedRow()));
				} catch (HeadlessException | MessagingException | IOException e) {
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * Método que devolve uma JTextField
	 * 
	 * @return JTextField (conta do utilizador)
	 */
	public JTextField getLog(int i) {
		switch (i) {
		case 1:
			return log1;
		case 2:
			return log2;
		case 3:
			return log3;
		default:
			return null;
		}

	}

	/**
	 * Método que devolve uma JTable
	 * 
	 * @return JTable (local onde se disponibiliza toda a informação do software)
	 */
	public JTable getInboxTable() {
		return inboxTable;
	}

	/**
	 * Método que permite a visualização da Janela
	 */
	public void open() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		GUI g = new GUI();
		g.open();
	}

}
