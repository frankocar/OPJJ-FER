package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.pictures.PictureLibrary;

/**
 * Returns a list of all tags available to describe a picture library in a JSON form.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns={"/servlets/tags"})
public class TagsServlet extends HttpServlet {

	/**
	 * Path to the descriptor file
	 */
	private static String DESCRIPTOR = "./src/main/webapp/WEB-INF/opisnik.txt";
	/**
	 * Path to the folder that stores the pictures
	 */
	private static String PICTURE_FOLDER = "./src/main/webapp/WEB-INF/slike/";
	
	/**  */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PictureLibrary lib = PictureLibrary.getInstance();
		lib.init(Paths.get(DESCRIPTOR), Paths.get(PICTURE_FOLDER));
		
		Set<String> allTags = lib.getAllTags();
		String[] array = new String[allTags.size()];
		allTags.toArray(array);
		
		resp.setContentType("application/json;charset=UTF-8"); 
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(array);
		
		resp.getWriter().write(jsonText);
		
		resp.getWriter().flush();
		
	}
}
