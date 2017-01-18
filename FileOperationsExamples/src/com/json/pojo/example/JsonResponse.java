package com.json.pojo.example;

import java.util.Map;

public class JsonResponse {

	private Map<String, Object> lifecycle;
	private Map<String, Object> domain;
	private Map<String, Object> app;
	private Map<String, Object> host;

	public Map<String, Object> getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(Map<String, Object> lifecycle) {
		this.lifecycle = lifecycle;
	}

	public Map<String, Object> getDomain() {
		return domain;
	}

	public void setDomain(Map<String, Object> domain) {
		this.domain = domain;
	}

	public Map<String, Object> getApp() {
		return app;
	}

	public void setApp(Map<String, Object> app) {
		this.app = app;
	}

	public Map<String, Object> getHost() {
		return host;
	}

	public void setHost(Map<String, Object> host) {
		this.host = host;
	}

	
}
