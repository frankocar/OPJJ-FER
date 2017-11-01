package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet that generate an .xls workbook containing vote results
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/servleti/glasanje-xls")
public class XLSServlet extends HttpServlet {

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
	 * A method that generates a workbook based on database data
	 * 
	 * @param req HTTP request
	 * @return HSSFWorkbook containing all data.
	 */
	private HSSFWorkbook generateWorkbook(HttpServletRequest req) {				
		long pollID = (long) req.getSession().getAttribute("pollID");
		List<PollOption> results = DAOProvider.getDao().getPollOptions(pollID);
		results.sort((x, y) -> Long.compare(y.getVotesCount(), x.getVotesCount()));
		
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("Results");
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("ID");
		header.createCell(1).setCellValue("Option");
		header.createCell(2).setCellValue("Number of votes");
		header.createCell(3).setCellValue("Example");
		
		int i = 1;
		for (PollOption option : results) {
			HSSFRow row = sheet.createRow(i++);
			row.createCell(0).setCellValue(option.getId());
			row.createCell(1).setCellValue(option.getOptionTitle());
			row.createCell(2).setCellValue(option.getVotesCount());
			row.createCell(3).setCellValue(option.getOptionLink());
		}

		return workbook;
	}

}
