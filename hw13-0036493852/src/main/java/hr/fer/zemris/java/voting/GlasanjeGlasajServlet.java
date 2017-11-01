package hr.fer.zemris.java.voting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that will handle voting and keep a file with number of votes up-to-date.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		
		Map<Integer, Band> bands = (Map<Integer, Band>) req.getSession().getAttribute("bands");
		
		if (!Files.exists(filePath)) {
			Files.createFile(filePath);
			updateFile(filePath, bands);
		}
		
		int voteId = Integer.parseInt((String) req.getParameter("id"));
				
		List<String> data = Files.readAllLines(filePath);
		
		for (String line : data) {
			String[] split = line.trim().split("\\t+");
			int id = Integer.parseInt(split[0]);
			Band band = bands.get(id);
			
			int votes = Integer.parseInt(split[1]);
			if (id == voteId) {
				votes++;
			}			
			band.setVotes(votes);
			bands.put(id, band);			
		}
		
		updateFile(filePath, bands);
		req.getSession().setAttribute("bands", bands);

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Updates the file with the given data
	 * 
	 * @param file File to update
	 * @param bands Map of bands to use
	 * @throws IOException if an I/O error occurs opening or creating the file
	 */
	private void updateFile(Path file, Map<Integer, Band> bands) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
		
		for (Band band : bands.values()) {
			writer.write(band.getId() + "\t" + band.getVotes() +"\r\n");
		}
		
		writer.flush();
		writer.close();		
	}
	
}
