package com.qa.pojo;

public class Fields {
	
	String summary;
	String description;
	Projects projects;
	IssueType issueType;
	
	
	public Fields(String summary, String description, Projects projects, IssueType issueType) 
	{	
		this.summary = summary;
		this.description = description;
		this.projects = projects;
		this.issueType = issueType;
	}
	
	public String getsummary() {
		return summary;
	}
	public void setsummary(String summary) {
		this.summary = summary;
	}
	public String getdescription() {
		return description;
	}
	public void setdescription(String description) {
		this.description = description;
	}
	public Projects getProjects() {
		return projects;
	}
	public void setProjects(Projects projects) {
		this.projects = projects;
	}
	public IssueType getIssueType() {
		return issueType;
	}
	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}
	
	

}
