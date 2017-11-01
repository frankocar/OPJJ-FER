package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * A servlet listener that will store the server's start time as a session attribute
 * 
 * @author Franko Car
 *
 */
@WebListener
public class InfoListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
