package bda;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ContentGUI {

	private JFrame frame;
	private final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int width = (int) (0.7 * screenDim.width);
	private final int height = (int) (0.9 * screenDim.height);
	private final int widthLocation = screenDim.width / 2 - width / 2;
	private final int heightLocation = screenDim.height / 2 - height / 2;
	
	private JPanel north = new JPanel(); //*
	private JPanel center = new JPanel(); //*
	private JPanel south = new JPanel(); //*
	private JButton respond; //*
	private JLabel sender; //*
	private JTextArea text; //*
	private JButton send; //*

	public ContentGUI(Content content) throws HeadlessException, MessagingException {
		try {
			frame = new JFrame(content.getSubject().toString());
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		frame.setSize(width, height);
		frame.setLocation(widthLocation, heightLocation);
		
		frame.setLayout(new BorderLayout()); //*
		north.setLayout(new BorderLayout()); //*
		north.setPreferredSize(new Dimension(0, 60)); 
		
		String froms = null;
		try {
			froms = content.getFrom();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} //*
		
		sender = new JLabel("From:   " + froms); //*
		sender.setFont(new Font("Arial", Font.PLAIN, 18));
		north.add(sender, BorderLayout.WEST); //*
		center.setLayout(new BorderLayout()); //*
		south.setLayout(new BorderLayout()); //*
		south.setPreferredSize(new Dimension(0, 0)); //*
		
		ImageIcon icon = new ImageIcon("images/" + "res" + ".png");
		Image image = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
		
		respond = new JButton("Respond" , icon); //*
		respond.setPreferredSize(new Dimension(50,50)); //*
		center.add(respond , BorderLayout.SOUTH); //*
	//	south.add(respond , BorderLayout.NORTH); //*
		
				
		addOperations(); //*
		
		JTextArea t = new JTextArea();
		t.setEditable(false);
		String hash = null;
		try {
			hash = content.getHashCode();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		t.append("\n");//*
		
		try (Scanner scanner = new Scanner(
				new FileReader(new File(System.getProperty("user.dir") + File.separator + "Emails\\Email" + hash)))) {
			String s;
			while (scanner.hasNextLine()) {
				s = scanner.nextLine(); 
				t.append(" "+ s + "\n");
			}
		scanner.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		t.setFont(new Font("Arial", Font.PLAIN, 14));
		center.add(t); //*
		
		frame.add(north, BorderLayout.NORTH); frame.add(center, BorderLayout.CENTER); frame.add(south, BorderLayout.SOUTH); //*
		//frame.add(t);
		frame.setVisible(true);
	}
	
	public void addOperations() {
		respond.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				south.setPreferredSize(new Dimension(200, 200));
				
				text = new JTextArea();
				text.setPreferredSize(new Dimension(300,300));
			
				ImageIcon icon = new ImageIcon("images/s.png");
				Image image = icon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
						
				send = new JButton("Send" , icon);
				send.setPreferredSize(new Dimension(200,50));
				
				south.add(text, BorderLayout.CENTER);
				JPanel n = new JPanel();
				n.setLayout(new BorderLayout());
				n.setPreferredSize(new Dimension(200,50));
				n.add(send, BorderLayout.SOUTH);
				south.add(n, BorderLayout.EAST);
				
				frame.add(south, BorderLayout.SOUTH);
				frame.invalidate();
				frame.validate();
				frame.repaint();
			}
		});
	}

	private String createHashCode(Message m) throws MessagingException {
		String[] date = m.getSentDate().toString().split(" ");
		String[] time = date[3].split(":");
		String hash = date[0] + date[1] + date[2] + date[5] + time[0] + time[1] + time[2];
		return hash;
	}
}
