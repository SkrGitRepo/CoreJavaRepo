package com.samples;


import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.print.attribute.standard.Chromaticity;

import org.apache.commons.io.FileUtils;


public class MatchFileTimeStamp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	
		String dir = "C:/opt/brms/shared/logs";
		File latestUserActivityFile = lastFileModified(dir);
		Calendar now = Calendar.getInstance();
		File file = new File(latestUserActivityFile.toString());
		String userAction = null;
		String hostNamePort = null;
		if(file.exists()){
			List<String> lines = FileUtils.readLines(file);
			for(String line:lines){
				if (line.length() >0 ){
					if(!line.endsWith("Username")) {
						line = line.trim().replaceAll("\\s+", " ");
						//System.out.println(line);
						String[] lineDatas = line.split(" ");
						
						//for (String data : lineDatas ){
							//System.out.println(data);
						//}
						String dateTime  = lineDatas[0]+" "+lineDatas[1]+" "+lineDatas[2]+" "+lineDatas[3]+" "+lineDatas[4]+" "+lineDatas[5];
						String dateTimeNew  = lineDatas[0]+" "+lineDatas[1]+" "+lineDatas[2]+" "+lineDatas[3]+" "+lineDatas[5];
						String time = lineDatas[3]+" "+lineDatas[4];
						boolean result = lineDatas[9].matches("brms-.*");
						if (result){	
							userAction = lineDatas[6]+" "+lineDatas[7]+" "+lineDatas[8];
							hostNamePort = lineDatas[9]+":"+lineDatas[10];
						} else {
							userAction = lineDatas[6]+" "+lineDatas[7];
							hostNamePort = lineDatas[8]+":"+lineDatas[9];
						}
						
						SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy");
						//String dateInString = "Fri June 7 17:15:12 2013";
						String dateInString = dateTimeNew;
							
						try {
							Date date = formatter.parse(dateInString);
							System.out.println("-----------------------------------------------");
							System.err.println("time .."+date);
							System.err.println(formatter.format(date));
							System.out.println("-----------------------------------------------");

						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						String host = lineDatas[8];
						String port = lineDatas[9];
						System.out.println("-----------------------------------------------");
						System.out.println("TIME -> "+time);
						System.err.println("Logged DATE AND TIME: "+dateTime);
						String currentDateTime = new Date(System.currentTimeMillis()).toString();
						System.out.println("CURRENT DATE AND TIME: "+currentDateTime);
						System.out.println("userAction -> "+userAction);
						System.out.println("Host:Port -> "+hostNamePort);
						System.out.println("-----------------------------------------------");
						
						//convert_time_zone(time);
						read_vm_config_file(host,port);
						
					}
				}
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			Date date = new Date();
			System.out.println("-----------------------------------------------");
			System.err.println(dateFormat.format(date));
			System.out.println( new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) );
			System.out.println("-----------------------------------------------");
			//create Calendar instance
		    
			now = Calendar.getInstance();
		    now.add(Calendar.MINUTE, -30);
		    System.out.println("-----------------------------------------------");
		    System.out.println("Time before 30 minutes : " + now.get(Calendar.HOUR_OF_DAY)
		                      + ":"
		                      + now.get(Calendar.MINUTE)
		                      + ":"
		                      + now.get(Calendar.SECOND));
			
			System.err.println( new Date(System.currentTimeMillis()) );
			System.err.println(new Date(System.currentTimeMillis()-30*60*1000));
			System.out.println("-----------------------------------------------");
		} else {
			System.out.println("Required file does not exist... !");
		}
	}
	
	private static void read_vm_config_file(String host, String port) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("-----------------------------------------------");
		System.out.println("Read config file");
		System.out.println("-----------------------------------------------");
		File file = new File("C:/opt/brms/shared/scripts/brms_vm_cfg_PRD1_PRD.txt");
		
		host = "brms-prd1-13";
		port ="7005";
		if(file.exists()){
			List<String> lines = FileUtils.readLines(file);
			for(String line:lines){
				if(!line.contains("CONTEXT")){
					String[] tokens=line.split(",");
					//System.out.println(tokens[0]);
					//System.out.println(tokens[3]);
					if (host.equals(tokens[0]) && port.equals(tokens[3])) {
						System.out.println(tokens[0]);
						System.out.println(tokens[3]);
						System.out.println(tokens[9]);
						System.out.println(tokens[10]);
						
						String[] domainPath = tokens[9].split("/"); 
						
						System.out.println("Domain is "+domainPath[1]);
						
						
						String output = tokens[10]+"|"+domainPath[1]+"|"+tokens[0]+":"+tokens[3];
						System.out.println("-----------------------------------------------");
						System.out.println("Final Output :");
						System.out.println(output);
						
					} else {
						//System.out.println("Host : "+host+" with PORT "+port+" not found.");
					}
				}
			}
		} else {
			System.out.println("File does not exist");
		}
	}

	private static void convert_time_zone(String time) {
		// TODO Auto-generated method stub
		
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Date and time in Madrid: " + df.format(date));
		
		
		//String tzid = "EST";
		String tzid = "EDT";
	    TimeZone tz = TimeZone.getTimeZone(tzid);

	    long utc = System.currentTimeMillis();  // supply your timestamp here
	    Date d = new Date(utc);

	    // timezone symbol (z) included in the format pattern for debug
	    DateFormat format = new SimpleDateFormat("yy/M/dd hh:mm a z");

	    // format date in default timezone
	    System.err.println(format.format(d));

	    // format date in target timezone
	    format.setTimeZone(tz);
	    System.err.println(format.format(d));
		
	}

	public static File lastFileModified(String dir) {
	    File fl = new File(dir);
	    File[] files = fl.listFiles(new FileFilter() {          
	        public boolean accept(File file) {
	            return file.isFile();
	        }
	    });
	    long lastMod = Long.MIN_VALUE;
	    File choice = null;
	    for (File file : files) {
	        if (file.lastModified() > lastMod) {
	            choice = file;
	            lastMod = file.lastModified();
	        }
	    }
	    return choice;
	}

}
