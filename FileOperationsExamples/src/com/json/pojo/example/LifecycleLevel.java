package com.json.pojo.example;

import java.util.Map;

public class LifecycleLevel {
	private String lifecycle;
	private String status;
	private String dateTime;
	private Map<String,DomainLevel> domains;
	
	public String getLifecycle() {
		return lifecycle;
	}
	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public Map<String, DomainLevel> getDomains() {
		return domains;
	}
	public void setDomains(Map<String, DomainLevel> domains) {
		this.domains = domains;
	}

	
	

	
}


