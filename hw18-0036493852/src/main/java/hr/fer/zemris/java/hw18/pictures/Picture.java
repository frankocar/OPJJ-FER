package hr.fer.zemris.java.hw18.pictures;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Stores all data relevant to a picture, such as its location,
 * thumbnail, title and tags
 * 
 * @author Franko Car
 *
 */
public class Picture {

	/**
	 * Path to the picture
	 */
	private String picturePath;
	/**
	 * Path to the thumbnail
	 */
	private String thumbPath;
	/**
	 * Tags describing a picture
	 */
	private List<String> tags;
	/**
	 * Picture title
	 */
	private String title;
	/**
	 * Unique picture id
	 */
	private int id;
	
	/**
	 * A constructor
	 * 
	 * @param picturePath Path to the picture
	 * @param tags Tags describing a picture
	 * @param title Picture title
	 * @param id Unique picture id
	 */
	public Picture(Path picturePath, List<String> tags, String title, int id) {
		super();
		this.picturePath = picturePath.toString();
		this.tags = tags;
		this.title = title;
		this.id = id;
		checkThumb(picturePath);
	}
	
	/**
	 * Checks if a thumbnail for a picture on a given path exists
	 * 
	 * @param picturePath Path to the picture
	 */
	private void checkThumb(Path picturePath) {
		Path thumbPath = Paths.get(picturePath.toString().replace("slike", "thumbnails"));
		if (Files.exists(thumbPath)) {
			this.thumbPath = thumbPath.toString();
		}		
	}

	/**
	 * A constructor
	 * 
	 * @param id Unique picture id
	 */
	public Picture(int id) {
		this.id = id;
	}
	
	/**
	 * A getter for picture path
	 * 
	 * @return Picture path
	 */
	public Path getPicturePath() {
		return Paths.get(picturePath);
	}
	
	/**
	 * A getter for picture path
	 * 
	 * @return Thumbnail path
	 */
	public Path getThumbPath() {
		return Paths.get(thumbPath);
	}
	
	/**
	 * A getter for a list of tags
	 * 
	 * @return List of picture tags
	 */
	public List<String> getTags() {
		return tags;
	}
	
	/**
	 * A getter for picture title
	 * 
	 * @return Picture title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * A setter for picture path
	 * 
	 * @param picturePath new picture path
	 */
	public void setPicturePath(Path picturePath) {
		this.picturePath = picturePath.toString();
		checkThumb(picturePath);
	}
	
	/**
	 * A setter for picture thumbnail
	 * 
	 * @param thumbPath new thumbnail path
	 */
	public void setThumbPath(Path thumbPath) {
		this.thumbPath = thumbPath.toString();
	}
	
	/**
	 * A setter for picture tags
	 * 
	 * @param tags new tags
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * A setter for picture title
	 * 
	 * @param title new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * A setter for picture ID
	 * 
	 * @param id new id	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * A getter for picture ID
	 * 
	 * @return picture ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Adds a tag to the picture
	 * 
	 * @param tag new tag
	 */
	public void addTag(String tag) {
		if (tag == null) {
			throw new IllegalArgumentException("Tag can't be null");
		}
		
		tags.add(tag);
	}
	
	/**
	 * Checks if a given tag describes a picture
	 * 
	 * @param tag tag to check
	 * @return true if a picture contains a tag, false otherwise
	 */
	public boolean containsTag(String tag) {
		if (tags == null) {
			return false;
		}
		
		return tags.contains(tag);
	}
	
	/**
	 * Checks if a picture has a thumbnail
	 * 
	 * @return true if thumbnail exists, false otherwise
	 */
	public boolean hasThumbnail() {
		return thumbPath != null;
	}
	
}
