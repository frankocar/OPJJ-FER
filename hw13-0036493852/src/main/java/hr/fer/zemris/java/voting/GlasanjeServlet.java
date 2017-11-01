package hr.fer.zemris.java.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that will read a file with band names, IDs and song examples into a map,
 * and than forward the data to a page that will display it.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");		
		List<String> input = null;
		try {
			input = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			req.setAttribute("errorMsg", "File not found " + e.getMessage());
			req.getRequestDispatcher("tableError").forward(req, resp);
			return;
		}
		
		Map<Integer, Band> bands = new TreeMap<>();		
		for (String line : input) {
			if (line.isEmpty()) continue;
			
			String[] data = line.split("\\t+");			
			bands.put(Integer.parseInt(data[0]), new Band(Integer.parseInt(data[0]), data[1], data[2], 0));			
		}
		
		req.getSession().setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		
		
	}
	
}
