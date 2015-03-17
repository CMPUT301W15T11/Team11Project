package com.example.team11xtremexpensetracker;

import java.util.Calendar;

import junit.framework.TestCase;

public class ItemTests extends TestCase {
	
	
	public void test4_1_1(){
		Item test = new Item();
		Calendar itemDate = Calendar.getInstance();
		test.setItem("Itemname");
		test.setAmount("10");
		test.setUnit("USD");
		test.setDescription("test");
		test.setCategory("Air Fare");
		test.setDate(itemDate);
		assertTrue("Item name",test.getItem()=="Itemname");
		assertTrue("Item amount",test.getAmount()=="10");
		assertTrue("Item unit",test.getUnit()=="USD");
		//assertTrue("Item Description",test.getDescription()=="test");
		assertTrue("Item category",test.getCategory()=="Air Fare");
		assertTrue("Item date",test.getDate()==itemDate);

	}
	public void test4_2_1(){
		Item test = new Item();

		test.setCategory("Air Fare");
		assertTrue("Item category",test.getCategory()=="Air Fare");
		test.setCategory("ground transport");
		assertTrue("Item category",test.getCategory()=="ground transport");
		test.setCategory("vehicle rental");
		assertTrue("Item category",test.getCategory()=="vehicle rental");
		test.setCategory("private automobile");
		assertTrue("Item category",test.getCategory()=="private automobile");
		test.setCategory("fuel");
		assertTrue("Item category",test.getCategory()=="fuel");
		test.setCategory("parking");
		assertTrue("Item category",test.getCategory()=="parking");
		test.setCategory("registration");
		assertTrue("Item category",test.getCategory()=="registration");
		test.setCategory("accommodation");
		assertTrue("Item category",test.getCategory()=="accommodation");
		test.setCategory("meal");
		assertTrue("Item category",test.getCategory()=="meal");
		test.setCategory("supplies");
		assertTrue("Item category",test.getCategory()=="supplies");
	}
	
	public void test4_3_1(){
		Item test = new Item();
		test.setUnit("CAD, USD, EUR, GBP, CHF, JPY, CNY");
		assertTrue("Item unit",test.getUnit()=="CAD, USD, EUR, GBP, CHF, JPY, CNY");
	}
	
	public void test4_4_1(){
		Item test = new Item();
		test.setIndecator(false);
		assertTrue(test.getIndecator()==false);
	}
	
	public void test4_6_1(){
		ClaimsList claims = new ClaimsList();
		Item item = new Item();
		claims.setEditable(true);
		ExpenseClaim test = new ExpenseClaim();
		test.addItem(item);
		assertTrue(test.getItemlist().size()!=0);
		

		
		}
		
	public void test4_7_1(){
		ClaimsList claims = new ClaimsList();
		Item item = new Item();
		claims.setEditable(true);
		ExpenseClaim test = new ExpenseClaim();
		test.addItem(item);
		test.removeItem(0);
		assertTrue(test.getItemlist().size()==0);
		
		
		
		}
		
	
	
	public void testAdditemAmount(){
		Item test = new Item();
		test.setAmount("10");
		assertTrue("Item amount",test.getAmount()=="10");
	}
	


	public void testAdditemDescription(){
		Item test = new Item();
		test.setDescription("10");
		assertTrue("Item Description",test.getDescription()=="10");
	}


	protected void setUp() throws Exception {
		super.setUp();
	}

}
