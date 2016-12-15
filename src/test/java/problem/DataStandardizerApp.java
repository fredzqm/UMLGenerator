package problem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The DataStandardizer class standardizes the Business Intelligence data
 * provided by Google and Microsoft to a common format.
 * 
 * @author Chandan R. Rupakheti
 * @author Mark Hays
 */
public class DataStandardizerApp {
	private JFrame frame;

	private JPanel topPanel;
	private JTextField txtField;
	private JButton button;

	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JLabel label;
	private DataStandardizer standardizer;
	
	public DataStandardizerApp(DataStandardizer standardizer) {
		this.standardizer = standardizer;
	}
	
	protected void createAndShowGUI() {
		frame = new JFrame("Data Unifier");

		topPanel = new JPanel();
		txtField = new JTextField("./input_output/io.gogl");
		txtField.setPreferredSize(new Dimension(200, 25));
		topPanel.add(txtField);

		button = new JButton("Unify!");
		topPanel.add(button);

		// Add the top panel to the top of the window
		frame.add(topPanel, BorderLayout.NORTH);

		textArea = new JTextArea(5, 60);
		textArea.setPreferredSize(new Dimension(300, 200));
		scrollPane = new JScrollPane(textArea);

		// Add the scroll pane to the center of the window
		frame.add(scrollPane, BorderLayout.CENTER);

		// Add the label as status
		label = new JLabel("<No Data>");
		frame.add(label, BorderLayout.SOUTH);

		// Add action listener to the button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Parse the source file
				standardizer.parse(txtField.getText());
				label.setText("Company: " + standardizer.getCompany());
				textArea.setText(standardizer.getData());
			}
		});

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void execute() {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Basically, shows up the GUI.
				createAndShowGUI();
			}
		});
	}

	// The main method
	public static void main(String[] args) {
		DataStandardizer standardizer = new DataStandardizer();
		DataStandardizerApp unifier = new DataStandardizerApp(standardizer);
		unifier.execute();
	}
}
