package com.samples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompareTime {
	public static void main(String[] args) {
		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy");
		String dateInString = "Wed May 25 20:40:12 2016";
		//String dateInString = dateTimeNew;
		try {
			Date loggedTimeStamp = formatter.parse(dateInString);
			Date currentTimeStamp =  new Date(System.currentTimeMillis());
			Date thirtyMinuteBeforeTimeStamp = new Date(System.currentTimeMillis()-30*60*1000);
			System.out.println("loggedTimeStamp: "+loggedTimeStamp);
			System.out.println("currentTimeStamp: "+currentTimeStamp);
			System.out.println("30 Minute before TimeStamp: "+thirtyMinuteBeforeTimeStamp);
			
			if(loggedTimeStamp.after(thirtyMinuteBeforeTimeStamp)) {
				System.err.println("Logged TimeStamp "+loggedTimeStamp+ "is before than last 30 minute timestamp :"+thirtyMinuteBeforeTimeStamp);
			} else {
				System.out.println("Logged TimeStamp "+loggedTimeStamp + "is from last 30 minutes ");
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
