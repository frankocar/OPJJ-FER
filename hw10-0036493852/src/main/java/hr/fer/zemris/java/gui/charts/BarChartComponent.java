package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * A component representing a resizable barchart with
 * fixed axis markings in respect to the parent container
 * 
 * @author Franko Car
 *
 */
public class BarChartComponent extends JComponent {

	/** Default serial version UID */
	private static final long serialVersionUID = 1L;
	
	/** Chart to draw */
	private BarChart chart;
	
	/** Table overhang over the last lines */
	private static final int OVERHANG = 8;
	/** Gap from axis description to number markings */
	private static final int TEXT_NUMBER_GAP = 20;
	/** Gap from container border to axis description */
	private static final int BORDER_TEXT_GAP = 10;
	/** Gap from container border to chart form top and right */
	private static final int BORDER_CHART_GAP = 30;
	/** Gap from number markings to chart */
	private static final int NUMBER_CHART_GAP = 10;
	/** Size factor of arrows on axis end */
	private static final int ARROW_SIZE = 6;
	
	/** Font for axis description */
	private static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 14);
	/** Font for number markings */
	private static final Font NUMBER_FONT = new Font("Arial", Font.BOLD, 15);
	
	/** Color for bars */
	private static final Color BAR_COLOR = new Color(244, 119, 72);
	/** Color for bar outlines */
	private static final Color OUTLINE_COLOR = Color.WHITE;
	/** Color for bar shadows */
	private static final Color SHADOW_COLOR = Color.LIGHT_GRAY;
	/** Color for background grid */
	private static final Color GRID_COLOR = new Color(240, 225, 196);
	/** Color for main axis */
	private static final Color MAIN_AXIS_COLOR = Color.GRAY;
	/** Color for text */
	private static final Color TEXT_COLOR = Color.BLACK;
	/** Color for background */
	private static final Color BACKGROUND_COLOR = Color.WHITE;	
	
	/** Enable or disable shadows */
	private static final boolean SHOW_SHADOW = true;
	
	/**
	 * A constructor that requires a reference to chart
	 * 
	 * @param chart to draw
	 */
	public BarChartComponent(BarChart chart) {
		if (chart == null) {
			throw new IllegalArgumentException("Chart can't be null");
		}
		
		this.chart = chart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		Dimension dimension = getSize();
		
		int columns = chart.getValues().size();
		int rows = (chart.getMaxY() - chart.getMinY()) / chart.getStep();
		
		FontMetrics fm = graphics.getFontMetrics(TEXT_FONT);
		if (isOpaque()) {
			graphics.setColor(BACKGROUND_COLOR);
			graphics.fillRect(0, 0, dimension.width, dimension.height);
		}
		
		int originX = fm.stringWidth(Integer.toString(chart.getMaxY())) + fm.getAscent() + TEXT_NUMBER_GAP + BORDER_TEXT_GAP + NUMBER_CHART_GAP;
		int originY = dimension.height - fm.getAscent() - TEXT_NUMBER_GAP - BORDER_TEXT_GAP - NUMBER_CHART_GAP;
		
		int endX = dimension.width - BORDER_CHART_GAP;
		int endY = BORDER_CHART_GAP;
		
		int colWidth = (endX - originX) / columns;
		int rowHeight = (originY - endY) / rows;
		
		drawText(graphics, originX, originY, endX, endY);
		drawTable(graphics, columns, rows, originX, originY, endX, endY, colWidth, rowHeight);
		drawData(graphics, columns, rows, originX, originY, endX, endY, colWidth, rowHeight);
		drawArrows(graphics, originX, originY, endX, endY);
		
	}

	/**
	 * A method to draw axis descriptions
	 * 
	 * @param graphics graphics to use
	 * @param originX x coordinate of origin
	 * @param originY y coordinate of origin
	 * @param endX x coordinate of chart right end
	 * @param endY y coordinate of chart top
	 */
	private void drawText(Graphics2D graphics, int originX, int originY, int endX, int endY) {
		graphics.setFont(TEXT_FONT);
		graphics.setColor(TEXT_COLOR);
		
		FontMetrics fm = graphics.getFontMetrics();
		graphics.drawString(chart.getxDescription(),
				((endX - originX) / 2), 
				originY + NUMBER_CHART_GAP + TEXT_NUMBER_GAP + fm.getMaxAscent());	
		
		Graphics2D g2 = (Graphics2D) graphics.create(); //clone current graphics so they aren't affected by rotation
		AffineTransform transform = AffineTransform.getQuadrantRotateInstance(3);
		g2.setTransform(transform);
		g2.drawString(chart.getyDescription(),
				-(((originY-endY) / 2) + fm.stringWidth(chart.getyDescription()) / 2),
				BORDER_TEXT_GAP + fm.getAscent());		
	}
	
	/**
	 * A method to draw arrows on axis ends
	 * 
	 * @param graphics graphics to use
	 * @param originX x coordinate of origin
	 * @param originY y coordinate of origin
	 * @param endX x coordinate of chart right end
	 * @param endY y coordinate of chart top
	 */
	private void drawArrows(Graphics2D graphics, int originX, int originY, int endX, int endY) {
		graphics.setColor(MAIN_AXIS_COLOR);
		graphics.fillPolygon( //yAxis arrow
				new int[] {endX + OVERHANG, endX + OVERHANG + ARROW_SIZE, endX + OVERHANG}, 
				new int[] {originY - ARROW_SIZE, originY, originY + ARROW_SIZE}, 
				3);
		graphics.fillPolygon( //xAxis arrow
				new int[] {originX - ARROW_SIZE, originX + ARROW_SIZE, originX}, 
				new int[] {endY - OVERHANG, endY - OVERHANG, endY - OVERHANG - ARROW_SIZE}, 
				3);
		
	}

	/**
	 * A method to draw chart bars
	 * 
	 * @param graphics graphics to use
	 * @param columns number of columns
	 * @param rows number of rows in the chart
	 * @param originX x coordinate of origin
	 * @param originY y coordinate of origin
	 * @param endX x coordinate of chart right end
	 * @param endY y coordinate of chart top
	 * @param colWidth width of a column
	 * @param rowHeight height of a row
	 */
	private void drawData(Graphics2D graphics, int columns, int rows, int originX, int originY, int endX, int endY,
			int colWidth, int rowHeight) {
		List<XYValue> values = chart.getValues();
		
		for (int i = 0; i < values.size(); i++) {
			int ySize = values.get(i).getY();
			if (ySize < chart.getMinY()) {
				ySize = chart.getMinY();
			}
			
			int barOriginX = originX + colWidth * i + 1;
			
			int barEndY = originY + (chart.getMinY() / chart.getStep() * rowHeight) - (ySize * rowHeight / chart.getStep());			
			
			if (barEndY > originY) {
				barEndY = originY;
			}
			
			//shadows
			if (SHOW_SHADOW) { 
				graphics.setColor(SHADOW_COLOR);
				int shadowSize = colWidth / 15 >= 8 ? 8 : colWidth / 15;
				graphics.fillRect(barOriginX, barEndY + shadowSize, colWidth + shadowSize,
						originY + chart.getMinY() * rowHeight / chart.getStep() - barEndY - shadowSize - 1);
			}
			
			//bars
			graphics.setColor(BAR_COLOR);
			graphics.fillRect(barOriginX, barEndY, colWidth, originY + chart.getMinY() * rowHeight / chart.getStep() - barEndY - 1);
			
			//outlines
			graphics.setColor(OUTLINE_COLOR);
			graphics.drawRect(barOriginX, barEndY, colWidth, originY + chart.getMinY() * rowHeight / chart.getStep() - barEndY - 1);
			
		}
		
	}

	/**
	 * A method to draw background table
	 * 
	 * @param graphics graphics to use
	 * @param columns number of columns
	 * @param rows number of rows in the chart
	 * @param originX x coordinate of origin
	 * @param originY y coordinate of origin
	 * @param endX x coordinate of chart right end
	 * @param endY y coordinate of chart top
	 * @param colWidth width of a column
	 * @param rowHeight height of a row
	 */
	private void drawTable(Graphics2D graphics, int columns, int rows, int originX, int originY, int endX, int endY, int colWidth, int rowHeight) {
		
		int minY = chart.getMinY();
		int step = chart.getStep();
		List<XYValue> values = chart.getValues();
		
		graphics.setFont(NUMBER_FONT);
		FontMetrics fm = graphics.getFontMetrics();
		
		//horizontal lines
		for (int j = originY, n = 0; n <= rows; j -= rowHeight, n++) {
			graphics.setColor(n == 0 ? MAIN_AXIS_COLOR : GRID_COLOR);
			graphics.drawLine(originX - OVERHANG, j, endX + OVERHANG, j);
			
			String yValue = Integer.toString(minY);
			minY += step;
			graphics.setColor(TEXT_COLOR);
			graphics.drawString(yValue, originX - NUMBER_CHART_GAP - fm.stringWidth(yValue), j + (fm.getMaxAscent() / 2));			
		}
		
		//vertical lines
		for (int i = originX, n = 0; n <= columns; i += colWidth, n++) {
			graphics.setColor(n == 0 ? MAIN_AXIS_COLOR : GRID_COLOR);
			graphics.drawLine(i, originY + OVERHANG, i, endY - OVERHANG);
			
			if (n < values.size()) {
				String xValue = Integer.toString(values.get(n).getX());
				graphics.setColor(TEXT_COLOR);
				graphics.drawString(xValue,
						originX + n * colWidth + colWidth / 2 - fm.stringWidth(xValue) / 2,
						originY + NUMBER_CHART_GAP + fm.getMaxAscent());	
			}
		}
		
	}
	
}
