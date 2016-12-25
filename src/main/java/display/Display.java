package display;

import runner.IRunnerConfiguration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class Display extends JFrame {
	public static final Set<String> SUPPORT_EXTENSIONS;

	static {
		SUPPORT_EXTENSIONS = new HashSet<>();
		SUPPORT_EXTENSIONS.add("jpg");
		SUPPORT_EXTENSIONS.add("png");
	}

	private BufferedImage image;
	private JPanel canvas;

	public Display(String imagePath) {
		try {
			this.image = ImageIO.read(new File(imagePath));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		this.canvas = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, null);
			}
		};
		canvas.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		JScrollPane sp = new JScrollPane(canvas);
		setLayout(new BorderLayout());
		add(sp, BorderLayout.CENTER);
	}

	public static void showWindow(IRunnerConfiguration config) {
		if (!SUPPORT_EXTENSIONS.contains(config.getOutputFormat())) {
			System.err.print("Currently unsupported image format " + config.getOutputFormat());
			return;
		}
		String outputFilePathImage = config.getOutputDirectory() + "/" + config.getFileName() + "."
				+ config.getOutputFormat();

		JFrame frame = new Display(outputFilePathImage);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}