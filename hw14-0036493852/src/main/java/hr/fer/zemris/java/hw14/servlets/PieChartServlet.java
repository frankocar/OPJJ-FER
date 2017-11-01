package hr.fer.zemris.java.hw14.servlets;

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

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet that generates an image representing a graph of vote results 
 * based on database data for a requested poll-
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servleti/glasanje-grafika")
public class PieChartServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a dataset from database data.
	 * 
	 * @param req HTTP request
	 * @return PieDataset dataset
	 */
	private PieDataset createDataset(HttpServletRequest req) {
		DefaultPieDataset result = new DefaultPieDataset();	
		
		long pollID = (long) req.getSession().getAttribute("pollID");
		List<PollOption> results = DAOProvider.getDao().getPollOptions(pollID);	
		
		results.forEach((v) -> result.setValue(v.getOptionTitle(), v.getVotesCount()));		
		
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
