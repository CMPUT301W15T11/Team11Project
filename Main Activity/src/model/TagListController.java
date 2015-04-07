package model;

import java.util.ArrayList;

/**
 * 
 * The controller used to save all tags download from online database
 * @author Mingtuo
 *
 */
public class TagListController {
	private static ArrayList<String> availableTagList;

	public static void setAvailableTagList(ArrayList<ExpenseClaim> claimList) {
		availableTagList = new ArrayList<String>();
		for (int i = 0; i < claimList.size(); i++) {
			for (int j = 0; j < claimList.get(i).getTagList().size(); j++) {
				availableTagList.add(claimList.get(i).getTagList().get(j).getTagName());
			}
		}
	}

	public static ArrayList<String> getAvailableTagList() {
		return availableTagList;
	}
}
