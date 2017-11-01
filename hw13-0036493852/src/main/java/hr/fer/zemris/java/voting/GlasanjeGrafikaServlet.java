package hr.fer.zemris.java.voting;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

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
 * Servlet that generates an image representing a graph of vote results
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a dataset from given parameters
	 * 
	 * @param req HTTP request
	 * @return PieDataset dataset
	 */
	@SuppressWarnings("unchecked")
	private PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset result = new DefaultPieDataset();		
		List<Band> results = (List<Band>) req.getSession().getAttribute("results");		
		results.forEach((v) -> result.setValue(v.getName(), v.getVotes()));		
		return result;

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JFreeChart chart = ChartFactory.createPieChart3D("Rezultat", createDataset(req), true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(45);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.9f);
		plot.setIgnoreNullValues(true);
		plot.setIgnoreZeroValues(true);
		Font font = new Font(Font.SANS_SERIF, plot.getLabelFont().getStyle(), plot.getLabelFont().getSize());
		plot.setLabelFont(font);
		
		BufferedImage bim = chart.createBufferedImage(600, 400);
		byte[] image = ChartUtilities.encodeAsPNG(bim);

		resp.setContentType("image/png");
		resp.getOutputStream().write(image);
	}
}
