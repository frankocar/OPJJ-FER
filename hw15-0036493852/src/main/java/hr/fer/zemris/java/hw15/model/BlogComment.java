package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A class that stores blog comments
 * 
 * @author Franko Car
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Comment id
	 */
	private Long id;
	/**
	 * BlogEntry on which the comment was made
	 */
	private BlogEntry blogEntry;
	/**
	 * Email of a user that made a comment
	 */
	private String usersEMail;
	/**
	 * Comment text
	 */
	private String message;
	/**
	 * Date on which the comment was posted
	 */
	private Date postedOn;
	
	/**
	 * A getter for comment id
	 * 
	 * @return long comment id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * A getter for comment id
	 * 
	 * @param id comment id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * A getter for BlogEntry
	 * 
	 * @return BlogEntry blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * A setter for blog entry
	 * 
	 * @param blogEntry new blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * A getter for user's email
	 * 
	 * @return String user's email
	 */
	@Column(nullable=false, length=50)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * A setter for user's email
	 * 
	 * @param usersEMail new email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}
	
	/**
	 * A getter for comment message
	 * 
	 * @return String comment message
	 */
	@Column(nullable=false, length=4096)
	public String getMessage() {
		return message;
	}

	/**
	 * A setter form comment message
	 * 
	 * @param message new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * A getter for comment posted on date
	 * 
	 * @return Date date on which the comment was posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * A setter for comments posted on date
	 * 
	 * @param postedOn date on which the comment was posted
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}