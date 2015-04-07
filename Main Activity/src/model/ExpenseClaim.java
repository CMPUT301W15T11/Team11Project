package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Expense claim object: Use to store expense items.
 * 
 * @author Xindong
 * 
 */
public class ExpenseClaim {

	private String claimantName;
	private String approverName;
	private String name;
	private String comments;

	private Calendar startDate;
	private Calendar endDate;
	private Calendar commentsDate;
	private ArrayList<Destination> destinations;
	private String status;
	private ArrayList<Tag> tagList;
	private Boolean isEditable;
	private double totalCurrency;
	private ArrayList<Item> Itemlist;

	/**
	 * get total currency
	 */
	public String getSpendStr() {

		ArrayList<Integer> sumList = new ArrayList<Integer>();
		ArrayList<String> unitList = new ArrayList<String>();

		for (int x = 0; x < (this.Itemlist.size()); x++) {
			// initialize
			String currentUnit = this.Itemlist.get(x).getUnit();
			Integer currentAmount;

			if ((this.Itemlist.get(x).getAmount() != null) && (this.Itemlist.get(x).getAmount() != "")) {
				currentAmount = Integer.parseInt(this.Itemlist.get(x).getAmount());
			} else {
				currentAmount = 0;
			}
			Boolean flag = false;
			// adding item to result
			if (unitList.isEmpty() != true) {
				for (int y = 0; y < (unitList.size()); y++) {
					String currentULU = unitList.get(y);
					if (currentUnit.equals(currentULU)) {
						int currentULS = sumList.get(y);
						sumList.set(y, currentULS + currentAmount);
						flag = true;
					}

				}
				if (flag == false) {
					unitList.add(currentUnit);
					sumList.add(currentAmount);
				}

			} else {
				unitList.add(currentUnit);
				sumList.add(currentAmount);

			}

		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sumList.size(); i++) {
			sb.append(sumList.get(i).toString());
			sb.append(" ");
			sb.append(unitList.get(i).toUpperCase(Locale.ENGLISH));
			if (i != (sumList.size() - 1)) {
				sb.append("; ");

			}

		}

		return sb.toString();
	}

	// getters and setters
	/**
	 * Constructor of Expense Claim
	 */
	public ExpenseClaim() {
		approverName = "";
		status = "In progress";
		isEditable = true;
		Calendar cc = Calendar.getInstance();
		commentsDate = cc;
		destinations = new ArrayList<Destination>();
		Itemlist = new ArrayList<Item>();
		tagList = new ArrayList<Tag>();
	}

	// item list
	/**
	 * Get item list.
	 * @return Itemlist
	 */
	public ArrayList<Item> getItemlist() {
		return Itemlist;
	}

	/**
	 * Set item list.
	 * 
	 * @param itemlist
	 */
	public void setItemlist(ArrayList<Item> itemlist) {
		Itemlist = itemlist;
	}

	/**
	 * Add item to item list.
	 * 
	 * @param newItem
	 */
	public void addItem(Item newItem) {
		if (isEditable) {
			this.Itemlist.add(newItem);
		}
	}

	/**
	 * Remove item form item list.
	 * 
	 * @param id
	 */
	public void removeItem(int id) {
		if (isEditable) {
			this.Itemlist.remove(id);
		}
	}

	/**
	 * Get item by id from item list.
	 * 
	 * @param ID
	 * @return Itemlist.get(ID)
	 */
	public Item getItemById(int ID) {
		return Itemlist.get(ID);
	}

	// ClaimantName

	/**
	 * Get claimant name.
	 * 
	 * @return claimantName
	 */
	public String getClaimantName() {
		return claimantName;
	}

	/**
	 * Set claimant name.
	 * 
	 * @param claimantName
	 */
	public void setClaimantName(String claimantName) {
		this.claimantName = claimantName;
	}

	// Name
	/**
	 * Get name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	// startDate
	/**
	 * Get start date.
<<<<<<< HEAD
	 * 
	 * @return
=======
	 * @return startDate
>>>>>>> 94234a816e20a22e94c146a07eb455e3ad11d5dc
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * Set start date.
	 * 
	 * @param startDate
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	// endDate
	/**
	 * Get end date
	 * 
	 * @return endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * Set end date
	 * 
	 * @param endDate
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	/**
	 * Get date range as a string.
	 * 
	 * @return returnString
	 */
	public String getDateRange() {
		// should return a string
		// of format "startDate - EndDate
		int month1 = this.startDate.get(Calendar.MONTH);
		int day1 = this.startDate.get(Calendar.DAY_OF_MONTH);
		int year1 = this.startDate.get(Calendar.YEAR);
		int month2 = this.endDate.get(Calendar.MONTH);
		int day2 = this.endDate.get(Calendar.DAY_OF_MONTH);
		int year2 = this.endDate.get(Calendar.YEAR);

		String returnString = String.format("%d/%d/%d - %d/%d/%d", month1, day1, year1, month2, day2, year2);
		return returnString;
		// return this.startDate.set(startYear, startMonthOfYear+1,
		// startDayOfMonth).toString() + " - " + this.endDate.set(startYear,
		// startMonthOfYear+1, startDayOfMonth).toString();
	}

	/**
	 * get date string
	 * 
	 * @return
	 */
	public String getCommentsDateString() {

		int monthC = this.commentsDate.get(Calendar.MONTH);
		int dayC = this.commentsDate.get(Calendar.DAY_OF_MONTH);
		int yearC = this.commentsDate.get(Calendar.YEAR);

		String outputString = String.format("%d - %d - %d", monthC, dayC, yearC);
		return outputString;
	}

	/**
	 * Get destination list.
	 * 
	 * @return destinations
	 */

	// destinations
	public ArrayList<Destination> getDestinations() {
		return destinations;
	}

	/**
	 * Set destinations list.
	 * 
	 * @param destinations
	 */
	public void setDestinations(ArrayList<Destination> destinations) {
		this.destinations = destinations;
	}

	// status
	/**
	 * Get status.
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set status.
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get tag list.
	 * 
	 * @return tagList
	 */
	// tags
	public ArrayList<Tag> getTagList() {
		return tagList;
	}

	/**
	 * Set tag list.
	 * 
	 * @param tagList
	 */
	public void setTagList(ArrayList<Tag> tagList) {
		this.tagList = tagList;
	}

	/**
	 * get comments
	 * @return comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * set comments
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * get comments date
	 * @return commentsDate
	 */
	public Calendar getCommentsDate() {
		return commentsDate;
	}
	/**
	 * set comments date
	 * @param commentsDate
	 */

	public void setCommentsDate(Calendar commentsDate) {
		this.commentsDate = commentsDate;
	}

	/**
	 * Check if the claim is editable.
	 * 
	 * @return totalCurrency
	 */
	// edit able
	public Boolean getIsEditable() {
		return isEditable;
	}

	/**
	 * Set if the claim is editable.
	 * 
	 * @param isEditable
	 */
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	// approver name
	/**
	 * Get approve name.
	 * 
	 * @return
	 */
	public String getApproverName() {
		return approverName;
	}

	/**
	 * Set approve name.
	 * 
	 * @param approverName
	 */
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	// Used to display information in list view
	/**
	 * Return the object string as its name.
	 */
	public String toString() {
		// TODO the structure is weird, it's hard to manage with only claimList,
		// we may need to add userList as well
		return name;
	}

}
