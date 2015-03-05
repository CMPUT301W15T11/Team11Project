package com.example.team11xtremexpensetracker;

import java.util.ArrayList;

public class Itemlist {
	private ArrayList<Item>Itemlist;
	public Itemlist(){
		this.Itemlist = new ArrayList<Item>();
	}
	public ArrayList<Item> getItemlist() {
		return Itemlist;
	}
	public void setItemlist(ArrayList<Item> itemlist) {
		Itemlist = itemlist;
	}
	
	public void additem(Item newItem){
		this.Itemlist.add(newItem);
	}
	

}
