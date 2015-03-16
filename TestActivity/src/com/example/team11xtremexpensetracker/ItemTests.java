package com.example.team11xtremexpensetracker;

import junit.framework.TestCase;

public class ItemTests extends TestCase {
	
	
	public void testAdditemName(){
		Item test = new Item();
		test.setItem("Itemname");
		assertTrue("Item name",test.getItem()=="Itemname");
	}
	
	public void testAdditemAmount(){
		Item test = new Item();
		test.setAmount("10");
		assertTrue("Item amount",test.getAmount()=="10");
	}
	
	public void testAdditemUnit(){
		Item test = new Item();
		test.setUnit("USD");
		assertTrue("Item unit",test.getUnit()=="USD");
	}

	public void testAdditemDescription(){
		Item test = new Item();
		test.setDescription("10");
		assertTrue("Item Description",test.getDescription()=="10");
	}
	public void testAdditemDate(){
		Item test = new Item();
		test.setDate("2015-3-16");
		assertTrue("Item date",test.getDate()=="2015-3-16");
	}
	public void testAdditemCategoty(){
		Item test = new Item();
		test.setCategory("Air Fare");
		assertTrue("Item category",test.getCategory()=="Air Fare");
		
	}
	
	

	protected void setUp() throws Exception {
		super.setUp();
	}

}
