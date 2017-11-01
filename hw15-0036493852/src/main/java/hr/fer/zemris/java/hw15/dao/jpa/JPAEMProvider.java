package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * JPA entity manager provider that ensures thread safety
 * 
 * @author Franko Car
 *
 */
public class JPAEMProvider {

	/**
	 * Thread pool used
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Gets current thread entity manager
	 * 
	 * @return EntityManger thread entity manager
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if(ldata==null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Closes the entity manager of a current thread
	 * 
	 * @throws DAOException If an error happened
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if(ldata==null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
	/**
	 * Local data class 
	 * 
	 * @author Franko Car
	 *
	 */
	private static class LocalData {
		/**
		 * Entity manager
		 */
		EntityManager em;
	}
	
}