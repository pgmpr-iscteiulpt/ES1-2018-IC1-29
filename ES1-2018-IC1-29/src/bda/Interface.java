package bda;

import java.awt.*;
import javax.swing.*;

public class Interface {
	
	private JFrame frame = new JFrame("BDA");
	private final Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	private final int width = (int) (0.7 * screenDim.width);
	private final int height = (int) (0.9 * screenDim.height);

	public Interface() {

		// size and centering
		frame.setSize(width, height);
		frame.setLocation(screenDim.width / 2 - width / 2, screenDim.height / 2 - height / 2);
		frame.setLayout(new BorderLayout());

		frame.setVisible(true);

	}

	public static void main(String[] args) {
		new Interface();
		
	
	}

}
