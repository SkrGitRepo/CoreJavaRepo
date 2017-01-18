package com.samples;

import java.util.HashMap;
import java.util.Map;

public class ManipulateHashMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HashMap<String,String> hmap = new HashMap<String, String>();
		
		for (int i = 0; i < 2; i++) {
			hmap.put("eon-rtp3-1-l:3002", "Kill WL");
			hmap.put("brms-test-5:7031", "Stop WL");
			hmap.put("brms-test-5:7031", "Start WL");
			
			
			if(hmap.containsKey("brms-test-5:7031")) {
				System.out.println("Keys eixst.");
				hmap.replace("brms-test-5:7031","Start CLL" );
			}
		}
		
		for (Map.Entry<String, String> entry : hmap.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println(key);
		    System.out.println(key+"|"+value);
		}
		

	}

}
