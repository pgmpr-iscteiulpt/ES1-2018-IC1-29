package bda;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ContentGUI {

	private JFrame frame;
	private final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int width = (int) (0.7 * screenDim.width);
	private final int height = (int) (0.9 * screenDim.height);
	private final int widthLocation = screenDim.width / 2 - width / 2;
	private final int heightLocation = screenDim.height / 2 - height / 2;

	public ContentGUI(Message m) throws HeadlessException, MessagingException {
		frame = new JFrame(m.getSubject().toString());
		frame.setSize(width, height);
		frame.setLocation(widthLocation, heightLocation);

		JTextArea t = new JTextArea();
		t.setEditable(false);
		String hash = createHashCode(m);
		
		try (Scanner scanner = new Scanner(
				new FileReader(new File(System.getProperty("user.dir") + File.separator + "Emails\\Email" + hash)))) {
			String s;
			while (scanner.hasNextLine()) {
				s = scanner.nextLine(); 
				t.append(s + "\n");
			}
		scanner.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frame.add(t);
		frame.setVisible(true);
	}

	private String createHashCode(Message m) throws MessagingException {
		String[] date = m.getSentDate().toString().split(" ");
		String[] time = date[3].split(":");
		String hash = date[0] + date[1] + date[2] + date[5] + time[0] + time[1] + time[2];
		return hash;
	}
}
