package hr.fer.zemris.java.voting;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet that generate an .xls workbook containing vote results
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		HSSFWorkbook workbook = generateWorkbook(req);
		
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=Results.xls");
		workbook.write(resp.getOutputStream());
		workbook.close();
		
		
	}
	
	/**
	 * A method that generates a workbook
	 * 
	 * @param req HTTP request
	 * @return HSSFWorkbook containing all data.
	 */
	@SuppressWarnings("unchecked")
	private HSSFWorkbook generateWorkbook(HttpServletRequest req) {		
		Set<Band> results = (Set<Band>) req.getSession().getAttribute("results");
		
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("Results");
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.createCell(1).setCellValue("Band");
		header.createCell(2).setCellValue("Number of votes");
		
		int i = 1;
		for (Band band : results) {
			HSSFRow row = sheet.createRow(i++);
			row.createCell(0).setCellValue(band.getId());
			row.createCell(1).setCellValue(band.getName());
			row.createCell(2).setCellValue(band.getVotes());
		}

		return workbook;
	}

}
