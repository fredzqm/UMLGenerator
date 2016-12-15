package runner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * A GraphViz Process Runner.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizRunner implements IRunner {
	private static final String OUTPUT_FILE_EXTENSION = "dot";

	public void execute(IRunnerConfiguration config, String dotString) throws IOException, InterruptedException {
		String outputFilePath = config.getOutputDirectory() + "/" + config.getFileName();
		String outputFilePathDot = outputFilePath + "." + OUTPUT_FILE_EXTENSION;
		String outputFilePathImage = outputFilePath + "." + config.getOutputFormat();

		// write dot string into the file system
		File outputDir = new File(config.getOutputDirectory());
		outputDir.mkdirs();
		try {
			FileWriter writer = new FileWriter(outputFilePathDot);
			writer.write(dotString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("[ ERROR ]: Unable to create file.");
		}

		// execute command to create the image file
		// Create command "<execPath> -T<format> <input> -o <output>
		String command = String.format("%s -T%s %s -o %s", config.getExecutablePath(), config.getOutputFormat(),
				outputFilePathDot, outputFilePathImage);
		Process process = Runtime.getRuntime().exec(command.toString());
		process.waitFor();

		// Display
		JFrame frame = new Display(outputFilePathImage);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings("serial")
	public static class Display extends JFrame {
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

	}

}
