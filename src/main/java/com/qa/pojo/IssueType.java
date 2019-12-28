package com.qa.pojo;

public class IssueType {
	
	/*
	 * POJO - Plain Old Java Object
	 * Kind of data structure that will include fields with getter and setter method depends on the payload
	 * 
	 */	
	
	String name;

	public IssueType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
