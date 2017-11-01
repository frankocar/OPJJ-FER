package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that generates the data to be printed in a table of trigonometric values for sequential angles
 * given as parameters, with the max range being 720.
 * 
 * @author Franko Car
 *
 */
@WebServlet(urlPatterns="/trigonometric")
public class TrigServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * Bean that stores data for each angle value
	 * 
	 * @author Franko Car
	 *
	 */
	public static class NumberWithTrigValues implements Serializable {
		/** */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Angle
		 */
		int number;
		/**
		 * Sine of a stored angle
		 */
		double sine;
		/**
		 * Cosine of a stored angle
		 */
		double cosine;
		
		/**
		 * Default constructor
		 */
		public NumberWithTrigValues() {
		}
		
		/**
		 * Constructor
		 * 
		 * @param number Angle
		 * @param sine Sine value
		 * @param cosine Cosine value
		 */
		public NumberWithTrigValues(int number, double sine, double cosine) {
			this.number = number;
			this.sine = sine;
			this.cosine = cosine;
		}
		
		/**
		 * A getter for angle
		 * 
		 * @return Angle
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * A getter for sine value
		 * 
		 * @return Sine value
		 */
		public double getSine() {
			return sine;
		}

		/**
		 * A getter for cosine value
		 * 
		 * @return Cosine value
		 */
		public double getCosine() {
			return cosine;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = 0, b = 360;
		
		try {
			a = Integer.valueOf(req.getParameter("a"));
		} catch (Exception ignorable){
		}
		
		try {
			b = Integer.valueOf(req.getParameter("b"));
		} catch (Exception ignorable){
		}
		
		if (a > b) {
			Integer tmp = a;
			a = b;
			b = tmp;
		}
		
		if (b > a + 720) {
			b = a + 720;
		}
		
		List<NumberWithTrigValues> list = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			list.add(new NumberWithTrigValues(i, Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i))));
		}
		
		req.setAttribute("listOfTrigValues", list);
		req.getRequestDispatcher("trig").forward(req, resp);
	}
}
