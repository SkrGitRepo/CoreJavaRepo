package com.samples;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MultilevelJSON {

	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		
		//String lifecycles = new Array ["DEV","STAGE","PROD"];
		JSONObject object = new JSONObject();
		//object.put("name", "sample");
		JSONArray array = new JSONArray();
		Map<String, String> hostmp = new HashMap<String,String>();
		JSONObject arrayElementOne = new JSONObject();
		arrayElementOne.put("setId", 1);
		JSONArray arrayElementOneArray = new JSONArray();

		JSONObject arrayElementOneArrayElementOne = new JSONObject();
		hostmp.put("Staus", "up");
		hostmp.put("TIMESTAMP", "2010-15-21");
		arrayElementOneArrayElementOne.put("Domain", "cvc").append("brms-prd1-23",hostmp).append("brms-prd1-24",hostmp);
		arrayElementOneArrayElementOne.put("Domain", "gssc").append("brms-prd1-23",hostmp).append("brms-prd1-24",hostmp);
		//arrayElementOneArrayElementOne.put("APP", "score").append("brms-prd1-23","7005").append("brms-prd1-24","7005");
		arrayElementOneArrayElementOne.put("type1", "STRING");
		arrayElementOneArrayElementOne.put("name", "ABC");
		arrayElementOneArrayElementOne.put("type", "STRING");
		
		JSONObject arrayElementOneArrayElementTwo = new JSONObject();
		arrayElementOneArrayElementTwo.put("APPS", "XYZ").append("brms-prd1-23","7005").append("brms-prd1-24","7005");
		arrayElementOneArrayElementTwo.put("APPS", "XYZ").append("brms-prd1-23","7005").append("brms-prd1-24","7005");
		arrayElementOneArrayElementTwo.put("type", "STRING");

		arrayElementOneArray.put(arrayElementOneArrayElementOne);
		arrayElementOneArray.put(arrayElementOneArrayElementTwo);

		arrayElementOne.put("CVC", arrayElementOneArray);
		array.put(arrayElementOne);
		object.put("PROD", array);
		
		System.out.println(object);
	}

}
