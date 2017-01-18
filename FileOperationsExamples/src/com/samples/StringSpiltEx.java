package com.samples;

public class StringSpiltEx {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String env = "PRD_PaaS";
		if (env.equalsIgnoreCase("PRD_PaaS"))
		{
			String[] lines = env.split("_");
			System.out.println("env from String :"+lines[0]);
			System.out.println("domain from String :"+lines[1]);
		} else {
			System.out.println("String can not be parsed :"+env);
		}
		System.out.println("Main String :: "+env);
	}

}
