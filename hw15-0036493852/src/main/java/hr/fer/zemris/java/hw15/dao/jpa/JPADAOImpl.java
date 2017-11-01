package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A concrete implementation of a DAO subsystem using JPA to be used with blog database.
 * 
 * @author Franko Car
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public List<BlogUser> getUsers() throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> list = (List<BlogUser>) JPAEMProvider.getEntityManager().createQuery("select b from BlogUser b")
				.getResultList();
		JPAEMProvider.close();
		return list;
	}

	@Override
	public BlogUser getUser(String nick) throws DAOException {
		try {
			BlogUser user = (BlogUser) JPAEMProvider.getEntityManager()
					.createQuery("select u from BlogUser u where u.nick=:nick ").setParameter("nick", nick)
					.getSingleResult();
			JPAEMProvider.close();
			return user;
		} catch (NoResultException ignorable) {}
		return null;
	}

	@Override
	public void addNewUser(BlogUser newUser) throws DAOException {
		dbUpdate(newUser);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getUserEntries(BlogUser user) throws DAOException {
		List<BlogEntry> list = null;
		try {
			list = (List<BlogEntry>) JPAEMProvider.getEntityManager()
					.createQuery("select b from BlogEntry b where b.creator=:creator").setParameter("creator", user)
					.getResultList();
		} catch (NoResultException ignorable) {
		}
		JPAEMProvider.close();
		return list;
	}

	@Override
	public void addEntry(BlogEntry entry) throws DAOException {
		dbUpdate(entry);		
	}
	
	@Override
	public BlogUser getUser(Long id) throws DAOException {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return blogUser;
	}
	
	@Override
	public void addComment(BlogComment comment) throws DAOException {
		dbUpdate(comment);	
	}
	
	/**
	 * A method to add an object to the database
	 * 
	 * @param object object to add
	 */
	private void dbUpdate(Object object) {
		JPAEMProvider.getEntityManager().persist(object);		
	}

}