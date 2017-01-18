package com.samples;

import java.util.ArrayList;

public class AddToArraylist {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<String>  hostsToDisable = new ArrayList<String>();
		hostsToDisable.add("PRD_PaaS|cvcbv|brms-prd2-02.cisco.com:7005");
		hostsToDisable.add("PRD_PaaS|cvcbv|brms-prd2-03.cisco.com:7005");
		hostsToDisable.add("PRD_PaaS|cvcbv|brms-prd2-04.cisco.com:7005");
		
		
		
		
		for (int i = 0; i < hostsToDisable.size(); i++) {
			String hostLine = hostsToDisable.get(i);
			System.out.println(hostLine);
			String[] envline = hostLine.split("\\|");
			//System.out.println(envline[0]);
			//String newEnv = envline[0];
			String[] nEnv = envline[0].split("_");
			//String newHostToDisable = newEnv+"|"+envline[1]+"|"+envline[2];
			System.out.println(nEnv[0]+"|"+envline[1]+"|"+envline[2]);
		}
 
	}

}
