package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A class to store every blog entry
 * 
 * @author Franko Car
 *
 */
@Cacheable(true)
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name="blog_entries")
public class BlogEntry {

	/**
	 * Blog entry id
	 */
	private Long id;
	/**
	 * A list of comments made on an entry
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * User that made the entry
	 */
	private BlogUser creator;
	/**
	 * Date on which the entry was created on
	 */
	private Date createdAt;
	/**
	 * Date on which the entry was last modified on
	 */
	private Date lastModifiedAt;
	/**
	 * Entry title
	 */
	private String title;
	/**
	 * Entry text
	 */
	private String text;
	
	/**
	 * Blog entry ID
	 * 
	 * @return entry ID
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Entry id setter
	 * 
	 * @param id new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for a list of comments made on this entry
	 *  
	 * @return List<BlogComments> list of comments
	 */
	@OneToMany(mappedBy="blogEntry", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Setter for list of comments
	 * 
	 * @param comments new list of comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * A getter for comment's date of creation
	 * 
	 * @return Date date of creation
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for creation date
	 * 
	 * @param createdAt changed date of creation
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * Getter for entry creator
	 * 
	 * @return BlogUser entry creator
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * A setter for entry creator
	 * 
	 * @param creator new creator
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * ¸Getter for date of last modification
	 * 
	 * @return Date date on which the entry was last modified on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for last modified date
	 * 
	 * @param lastModifiedAt new date of modificaton
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	
	/**
	 * Getter for entry title
	 * 
	 * @return String entry title
	 */
	@Column(nullable=false, length=200)
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for entry title
	 * 
	 * @param title new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter for entry text
	 * 
	 * @return String entry text
	 */
	@Column(nullable=false, length=4096)
	public String getText() {
		return text;
	}

	/**
	 * Setter for entry text
	 * 
	 * @param text new text
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}