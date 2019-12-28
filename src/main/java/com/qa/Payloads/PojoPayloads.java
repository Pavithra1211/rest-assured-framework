package com.qa.Payloads;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.pojo.Fields;
import com.qa.pojo.IssueType;
import com.qa.pojo.Payload;
import com.qa.pojo.Projects;
import com.fasterxml.jackson.core.JsonProcessingException;

public class PojoPayloads {
	
	
	public String JiraTask() throws JsonProcessingException {
		
		IssueType issue = new IssueType("Bug");
		Projects project = new Projects("Test");
		Fields fields = new Fields("rest assured", "To create a task", project, issue);
		Payload payload = new Payload(fields);		
		
		ObjectMapper objmap = new ObjectMapper(); 		
		
		String mydata = objmap.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
		
		return mydata;
	}

}
 