package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A class to store blog users
 * 
 * @author Franko Car
 *
 */
@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser {
	
	/**
	 * User id
	 */
	private Long id;
	/**
	 * User's first name
	 */
	private String firstName;
	/**
	 * User's last name
	 */
	private String lastName;
	/**
	 * User's nickname
	 */
	private String nick;
	/**
	 * User's email
	 */
	private String email;
	/**
	 * User's hashed password
	 */
	private String passwordHash;
	/**
	 * List of entries made by a user
	 */
	private List<BlogEntry> entries = new ArrayList<>();
	
	/**
	 * Getter for user id
	 * 
	 * @return long user id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Setter for user id
	 * 
	 * @param id new id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Getter for fist name
	 * 
	 * @return String first name
	 */
	@Column(nullable=false, length=25)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Setter for user's first name
	 * 
	 * @param firstName new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Getter for last name
	 * 
	 * @return String last name
	 */
	@Column(nullable=false, length=30)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for last name
	 * 
	 * @param lastName new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for nickname 
	 * 
	 * @return String nickname
	 */
	@Column(nullable=false, unique=true, length=30)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Setter for nickname
	 * 
	 * @param nick new nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Getter for email
	 * 
	 * @return String email
	 */
	@Column(nullable=false, unique=true, length=50)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter for email
	 * 
	 * @param email new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter for password hash
	 * 
	 * @return String password hash
	 */
	@Column(nullable=false, length=40)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Setter for password hash
	 * 
	 * @param passwordHash new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Getter for list of entries made by a user
	 * 
	 * @return List<BlogEntry> list of entries
	 */
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	public List<BlogEntry> getEntries() {
		return entries;
	}
	
	/**
	 * Setter for list of user entries
	 * 
	 * @param entries new list of entries made by a user
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BlogUser))
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
