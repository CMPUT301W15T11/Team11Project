package com.example.team11xtremexpensetracker;

/**
 * Tag Object:
 * a data type used for managing claims
 * @author Mingtuo
 *
 */
public class Tag {
	
	private String tagName;
	
	/**
	 * constructor
	 * @param tagName
	 */
	public Tag(String tagName){
		this.tagName=tagName;
	}

	/**
	 * set tag name
	 * @return
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * get tag name
	 * @param tagName
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
}