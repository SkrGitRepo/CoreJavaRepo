package com.json.pojo.example;

import java.util.List;

public class AppLevel {
	private String status;
	private String dataTime;
	private String appName;
	private List<String> appHostList; 
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	public List<String> getAppHostList() {
		return appHostList;
	}
	public void setAppHostList(List<String> appHostList) {
		this.appHostList = appHostList;
	}
	
	//private DomainLevel dLevel;
	//private LifecycleLevel lLevel;

}
