package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Singleton class that returns an object acting as a
 * DAO subsystem provider.
 * 
 * @author Franko Car
 *
 */
public class DAOProvider {

	/**
	 * DAO instance
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Getter for DAO instance
	 * 
	 * @return DAO instance
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}