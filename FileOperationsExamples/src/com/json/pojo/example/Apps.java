package com.json.pojo.example;

import java.util.List;
import java.util.Map;

public class Apps {
	private Map<String,Map<String, List>> hosts;

	public Map<String, Map<String, List>> getHosts() {
		return hosts;
	}

	public void setHosts(Map<String, Map<String, List>> hosts) {
		this.hosts = hosts;
	}

}
