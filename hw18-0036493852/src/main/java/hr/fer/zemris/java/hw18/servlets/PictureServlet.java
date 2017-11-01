package hr.fer.zemris.java.hw18.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw18.pictures.Picture;
import hr.fer.zemris.java.hw18.pictures.PictureLibrary;

/**
 * Returns a picture with the id requested as a parameter with the name "id"
 * 
 * @author Franko
 *
 */
@WebServlet(urlPatterns={"/servlets/getPicture"})
public class PictureServlet extends HttpServlet {

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
		
		String type = req.getParameter("type");
		
		int id;
		try {
			id = Integer.parseInt(idParam);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Invalid image id");
		}
		
		PictureLibrary lib = PictureLibrary.getInstance();
		Picture pic = lib.getPictures().get(id);
		
		BufferedImage img = null;
		
		if (type.toLowerCase().trim().equals("thumb")) {
			 img = ImageIO.read(pic.getThumbPath().toFile());
		} else if (type.toLowerCase().trim().equals("pic")) {
			 img = ImageIO.read(pic.getPicturePath().toFile());
		} else {
			throw new IllegalArgumentException("Invalid image");
		}

		resp.setContentType("image/jpeg");
		ImageIO.write(img, "jpg", resp.getOutputStream());
	}
	
}
