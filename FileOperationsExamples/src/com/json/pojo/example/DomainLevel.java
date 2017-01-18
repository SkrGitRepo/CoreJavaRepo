package com.json.pojo.example;

import java.util.List;
import java.util.Map;

public class DomainLevel {
	private Map<String,Hosts> hosts;
	private Map<String,List> app;
	public Map<String, Hosts> getHosts() {
		return hosts;
	}
	public void setHosts(Map<String, Hosts> hosts) {
		this.hosts = hosts;
	}
	public Map<String, List> getApp() {
		return app;
	}
	public void setApp(Map<String, List> app) {
		this.app = app;
	}

	
}
