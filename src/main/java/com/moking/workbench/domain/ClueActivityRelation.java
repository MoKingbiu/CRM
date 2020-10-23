package com.moking.workbench.domain;

import org.springframework.stereotype.Component;

@Component
public class ClueActivityRelation {
	
	private String id;

	public ClueActivityRelation() {
	}

	private String clueId;
	private String activityId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClueId() {
		return clueId;
	}
	public void setClueId(String clueId) {
		this.clueId = clueId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	

	
}
