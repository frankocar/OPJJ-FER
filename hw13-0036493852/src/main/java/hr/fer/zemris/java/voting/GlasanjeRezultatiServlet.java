package hr.fer.zemris.java.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles voting results and finds the top bands
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		
		if (!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		
		Map<Integer, Band> bands = (Map<Integer, Band>) req.getSession().getAttribute("bands");
		if (bands == null) { //if this page is loaded before the map has been initialized by the index page
			resp.sendRedirect(req.getContextPath() + "/glasanje");
			return;
		}
		
		List<String> data = Files.readAllLines(filePath);
		for (String line : data) {
			String[] split = line.trim().split("\\t+");
			int id = Integer.parseInt(split[0]);
			Band band = bands.get(id);			
			int votes = Integer.parseInt(split[1]);
			band.setVotes(votes);
			bands.put(id, band);			
		}
		
		List<Band> results = new ArrayList<>();
		
		bands.forEach((k, v) -> results.add(v));
		Collections.sort(results, (y, x) -> {
			int result = Integer.compare(x.getVotes(), y.getVotes());
			if (result == 0) {
				result = y.getName().compareTo(x.getName());
			}
			return result;
		});
		
		
		List<Band> theBest = new ArrayList<>();
		if (results.size() > 0) {
			theBest.add(results.get(0));
			int i = 1;
			while (i < results.size() && theBest.get(0).getVotes() == results.get(i).getVotes()) {
				theBest.add(results.get(i++));
			} 
		}
		
		req.getSession().setAttribute("results", results);
		req.getSession().setAttribute("theBest", theBest);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
}
