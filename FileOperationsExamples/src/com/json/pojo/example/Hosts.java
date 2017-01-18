package com.json.pojo.example;

import java.util.List;
import java.util.Map;

public class Hosts {
	private Map<String,Object> hosts;
	
	
	public Map<String, Object> getHosts() {
		return hosts;
	}

	public void setHosts(Map<String, Object> hosts) {
		this.hosts = hosts;
	}

	private List<String>  hostPort;

	public List<String> getHostPort() {
		return hostPort;
	}

	public void setHostPort(List<String> hostPort) {
		this.hostPort = hostPort;
	}

}
