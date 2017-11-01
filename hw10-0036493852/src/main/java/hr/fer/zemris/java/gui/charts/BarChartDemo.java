package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A program that opens a file containing chart description
 * and draws a chart. It takes in path to the file as a command
 * line argument.
 * 
 * @author Franko Car
 *
 */
public class BarChartDemo extends JFrame{
	
	/** Default UID */
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor that requires a model of a chart.
	 * 
	 * @param model
	 */
	public BarChartDemo(BarChart model) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Chart");
		setLocation(20, 20);
		setSize(500, 500);
		
		initGUI(model);
	}
	
	/**
	 * A method to initialize the GUI
	 * 
	 * @param model
	 */
	private void initGUI(BarChart model) {		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(new BarChartComponent(model), BorderLayout.CENTER);
	}

	/**
	 * Main method
	 * 
 	 * @param args none required
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected a single argument, path to the data file");
			System.exit(1);
		}
		
		List<String> input;		
		try {
			input = Files.readAllLines(Paths.get(args[0]));
		} catch (IOException e) {
			System.out.println("Unable to open given file: " + e.getMessage());
			return;
		}
		
		if (input.size() != 6) {
			System.out.println("Invalid file structure");
			System.exit(1);
		}
		
		
		
		SwingUtilities.invokeLater(() -> new BarChartDemo(parseChart(input)).setVisible(true));
	}

	/**
	 * A method to parse a list of appropriate strings as a chart
	 * 
	 * @param input list of strings
	 * @return constructed BarChart object.
	 */
	private static BarChart parseChart(List<String> input) {
		String xAxis = input.get(0);
		String yAxis = input.get(1);
		List<XYValue> xyValues = new ArrayList<>();
		
		for (String xyValue : input.get(2).split("\\s+")) {
			String[] values = xyValue.split(",");
			if (values.length != 2) {
				System.out.println("Invalid xyValue data point");
				System.exit(1);	
			}
			
			try {
				int first = Integer.parseInt(values[0]);
				int second = Integer.parseInt(values[1]);
				xyValues.add(new XYValue(first, second));
			} catch (NumberFormatException ex) {
				System.out.println("Invalid xyValue data point");
				System.exit(1);	
			}						
		}
		
		int minY = Integer.parseInt(input.get(3));
		int maxY = Integer.parseInt(input.get(4));
		int step = Integer.parseInt(input.get(5));
		
		return new BarChart(xyValues, xAxis, yAxis, minY, maxY, step);
	}
	
}