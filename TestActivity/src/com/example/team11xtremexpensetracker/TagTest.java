package com.example.team11xtremexpensetracker;

import model.ClaimsList;
import model.ExpenseClaim;
import model.Tag;
import junit.framework.TestCase;

public class TagTest extends TestCase {

	public void testAddTag() {
		ExpenseClaim test = new ExpenseClaim();
		test.getTagList().add(new Tag("test"));
		assertEquals("Tag does add", "test", test.getTagList().get(0).getTagName());
	}

	public void testTagFilter() {
		ExpenseClaim test1=new ExpenseClaim();
		ExpenseClaim test2=new ExpenseClaim();
		test1.getTagList().add(new Tag("tag1"));
		test2.getTagList().add(new Tag("tag2"));
		ClaimsList testList = new ClaimsList();
		testList.addClaim(test1);
		testList.addClaim(test2);
		String checkTag="tag1";
		assertEquals("Proper tag is found",checkTag,testList.getClaimsAL().get(0).getTagList().get(0).getTagName().toLowerCase());
	}
}
