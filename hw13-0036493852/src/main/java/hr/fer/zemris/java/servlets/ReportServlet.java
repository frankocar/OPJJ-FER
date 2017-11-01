package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * A servlet that will generate an image of a pie chat using predetermined data and output it to 
 * the request's output stream.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/reportImage")
public class ReportServlet extends HttpServlet{

	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a piechart dataset.
	 * 
	 * @return
	 */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 22);
		result.setValue("Mac", 26);
		result.setValue("Windows", 52);
		return result;

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JFreeChart chart = ChartFactory.createPieChart3D("OS usage", createDataset(), true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		BufferedImage bim = chart.createBufferedImage(800, 400);
		byte[] image = ChartUtilities.encodeAsPNG(bim);

		resp.setContentType("image/png");
		resp.getOutputStream().write(image);
	}
}
