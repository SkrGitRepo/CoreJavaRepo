package com.samples;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


public class JSONParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
						
				Map<String, Long> map = new HashMap<String, Long>();
				map.put("A", 10L);
				map.put("B", 20L);
				map.put("C", 30L);
				JSONObject json = new JSONObject();
				json.accumulateAll(map);
				System.out.println(json.toString());
				List<String> list = new ArrayList<String>();
				list.add("Sunday");
				list.add("Monday");
				list.add("Tuesday");
				
				json.accumulate("weekdays", list);
				System.out.println(json.toString());
			}

}