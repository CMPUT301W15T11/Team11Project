package model;

import java.util.ArrayList;

/**
 * Tag list in database:
 * The list used for saving tags(related part haven't done yet)
 * @author Mingtuo
 *
 */
public class TagDataList {
	private ArrayList<Tag> tagDataList;
	
	/**
	 * constructor
	 */
	public TagDataList(){
		this.tagDataList=new ArrayList<Tag>();
	}

	/**
	 * get tagDataList
	 * @return
	 */
	public ArrayList<Tag> getTagDataList() {
		return tagDataList;
	}

	/**
	 * set tagDataList
	 * @param tagDataList
	 */
	public void setTagDataList(ArrayList<Tag> tagDataList) {
		this.tagDataList = tagDataList;
	}
	
}
