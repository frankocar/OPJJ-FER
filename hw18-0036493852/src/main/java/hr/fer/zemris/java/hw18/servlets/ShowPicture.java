package hr.fer.zemris.java.hw18.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.pictures.Picture;
import hr.fer.zemris.java.hw18.pictures.PictureLibrary;

/**
 * Returns picture data with the id requested as a parameter "id" in JSON form
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns={"/servlets/showPicture"})
public class ShowPicture extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idParam = req.getParameter("id");
		if (idParam != null) {
			idParam = idParam.trim();
			if (idParam.isEmpty()) {
				idParam = null;
			}
		}
		
		int id;
		try {
			id = Integer.parseInt(idParam);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Invalid image id");
		}
		
		PictureLibrary lib = PictureLibrary.getInstance();
		
		Picture result = lib.getPictures().get(id);
		
		resp.setContentType("application/json;charset=UTF-8"); 

		Gson gson = new Gson();
		String jsonText = gson.toJson(result);

		resp.getWriter().write(jsonText);

		resp.getWriter().flush();
	}

}
