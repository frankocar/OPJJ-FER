package hr.fer.zemris.java.hw18.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw18.pictures.Picture;
import hr.fer.zemris.java.hw18.pictures.PictureLibrary;

/**
 * Returns pictures with a tag regeusted as "tag" parameter. Also generates
 * thumbnails if a picture doesn't already have one.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns={"/servlets/tagPictures"})
public class TagPictures extends HttpServlet {

	/**  */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter("tag");
		if (tag != null) {
			tag = tag.trim();
			if (tag.isEmpty()) {
				tag = null;
			}
		}
		
		PictureLibrary lib = PictureLibrary.getInstance();
		
		List<Picture> taggedPictures = new LinkedList<>();
		
		for (Picture pic : lib.getPictures().values()) {
			if (pic.containsTag(tag)) {
				taggedPictures.add(pic);
			}
		}		
		
		for (Picture pic : taggedPictures) {
			if (!pic.hasThumbnail()) {
				createThumbnail(pic);
			}
		}
		
		Picture[] array = new Picture[taggedPictures.size()];
		taggedPictures.toArray(array);
		
		resp.setContentType("application/json;charset=UTF-8"); 

		Gson gson = new Gson();
		String jsonText = gson.toJson(array);

		resp.getWriter().write(jsonText);

		resp.getWriter().flush();
		
	}

	/**
	 * Creates a thumbnail for a given {@link Picture}
	 * 
	 * @param pic
	 */
	private void createThumbnail(Picture pic) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(pic.getPicturePath().toFile());
			
			BufferedImage scaled = toBufferedImage(img.getScaledInstance(150, 150, Image.SCALE_DEFAULT));	
			
			Path thumbPath = Paths.get(pic.getPicturePath().toString().replace("slike", "thumbnails"));
			
			if (Files.exists(thumbPath)) {
				pic.setThumbPath(thumbPath);
				return;
			}
			
			if (!Files.exists(thumbPath.getParent())) {
				Files.createDirectories(thumbPath.getParent());
			}
			
			ImageIO.write(scaled, "jpg", thumbPath.toFile());	
			pic.setThumbPath(thumbPath);
		} catch (IOException e) {
			System.out.println("Unable to open the picture");
		}
		
		
	}
	
	/**
	 * Converts an {@link Image} to a {@link BufferedImage}
	 * 
	 * @param img Input Image
	 * @return output BufferedImage
	 */
	private static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		return bimage;
	}
	
}
