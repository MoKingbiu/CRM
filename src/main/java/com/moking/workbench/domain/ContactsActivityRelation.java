package com.moking.workbench.domain;

import org.springframework.stereotype.Component;

@Component
public class ContactsActivityRelation {

	private String id;

	public ContactsActivityRelation() {
	}

	private String contactsId;
	private String activityId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContactsId() {
		return contactsId;
	}
	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	
	
}
