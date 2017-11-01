package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Singleton class that returns an object acting as a
 * JPAEMF subsystem provider.
 * 
 * @author Franko Car
 *
 */
public class JPAEMFProvider {

	/**
	 * Instance of EntityMangerFactor used
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * A getter for a JPA EMF instance.
	 * 
	 * @return {@link EntityManagerFactory} instance
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * EMF setter
	 * 
	 * @param emf new EMF
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}