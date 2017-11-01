package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * A servlet that generates an xls workbook containing n sheets with values between
 * a and b. a, b and n are given as request parameters. Each sheet will contain 2 columns
 * and b-a rows. Left row will show a number, and the right sheet will show its n-th power.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/powers")
public class TableServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a;
		int b;
		int n;
		
		try {
			a = Integer.valueOf(req.getParameter("a"));
			b = Integer.valueOf(req.getParameter("b"));
			n = Integer.valueOf(req.getParameter("n"));
		} catch (NumberFormatException | NullPointerException ex){
			errorPage(ex.getMessage(), req, resp);
			return;
		}
		
		if (a < -100 || a > 100) {
			errorPage("Value a should be in range [-100, 100], given value is " + a, req, resp);
			return;
		}
		
		if (b < -100 || b > 100) {
			errorPage("Value b should be in range [-100, 100], given value is " + b, req, resp);
			return;
		}
		
		if (n < 1 || n > 5) {
			errorPage("Value n should be in range [1, 5], given value is " + n, req, resp);
			return;
		}
		
		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		HSSFWorkbook workbook = generateWorkbook(a, b, n);
		
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=Powers.xls");
		workbook.write(resp.getOutputStream());
		workbook.close();
		
		
	}
	
	/**
	 * Generates a workbook
	 * 
	 * @param a Starting number
	 * @param b Ending number
	 * @param n Number of powers
	 * @return Filled workbook
	 */ 
	private HSSFWorkbook generateWorkbook(int a, int b, int n) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = workbook.createSheet("Exponent " + i);
			
			HSSFRow header = sheet.createRow(0);
			header.createCell(0).setCellValue("Number");
			header.createCell(1).setCellValue("To the power " + i);
			
			for (int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow(j - a + 1);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
			
		}
		
		
		return workbook;
	}

	/**
	 * Sends an error page with a given message 
	 * 
	 * @param msg Error message
	 * @param req HTTP request
	 * @param resp HTTP response
	 * @throws ServletException if the target resource throws this exception
	 * @throws IOException if the target resource throws this exception
	 */
	private void errorPage(String msg, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("errorMsg", msg);
		req.getRequestDispatcher("tableError").forward(req, resp);
	}
	
}
