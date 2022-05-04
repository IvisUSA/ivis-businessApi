package com.ivis.ApplicationModels;

import java.util.ArrayList;

public class CategoriesSubcategoriesOutputModel {

	
	
	
	int catid;
	String catname;
	
	ArrayList<Subcategories> subcategoriesList;

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public ArrayList<Subcategories> getSubcategoriesList() {
		return subcategoriesList;
	}

	public void setSubcategoriesList(ArrayList<Subcategories> subcategoriesList) {
		this.subcategoriesList = subcategoriesList;
	}

	
	
	
}
