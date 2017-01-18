package com.samples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.swing.plaf.SliderUI;

import org.apache.commons.io.FileUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LatestActionLogFinder {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		HashMap<String, String> uniqueHostLogMap = new HashMap<String, String>();
		
		
		ArrayList<String> allHostList = new ArrayList<String>();
		Iterator<File> fileListIterator = null;
		// String folderLoc =
		// PropertyLoader.getInstance().getProperty("shared_context");
		String folderLoc = "/opt/brms/shared";
		folderLoc += "/logs/";
		// String hostName = "brms-prd1-11";
		// String portNum = "7006";
		FilenameFilter logFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				// System.out.println(lowercaseName);
				if (lowercaseName.endsWith(".log")) {
					return true;
				} else {
					return false;
				}
			}
		};

		File[] fileList = new File(folderLoc).listFiles(logFilter);
		Arrays.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
			}
		});

		List<String[]> logHistoryList = new ArrayList<String[]>();

		for (File auditFile : fileList) {
			if (auditFile.isFile()) {

				Date currentFileDate = new Date(auditFile.lastModified());
				Date montheLaterDate;
				Calendar cal2 = Calendar.getInstance();
				cal2.add(Calendar.MONTH, -1);
				montheLaterDate = cal2.getTime();
				// System.out.println(currentFileDate);

				if (montheLaterDate.before(currentFileDate)) {
					BufferedReader br = null;
					try {
						String sCurrentLine;
						System.out.println(folderLoc + auditFile.getName());
						String logFileName = folderLoc + auditFile.getName();

						File file = new File(logFileName);
						if (file.exists()) {
							List<String> loggedLines = FileUtils.readLines(file);
							for (String loggedLine : loggedLines) {
								if (loggedLine.length() > 0) {
									if (!loggedLine.endsWith("Username")) {
										loggedLine = loggedLine.trim().replaceAll("\\s+", " ");
										System.out.println(loggedLine);
										String[] tokens = loggedLine.split(" ");

										Pattern actionPatern = Pattern
										        .compile("\\b(Start WL|Kill WL|Stop WL|Restart WL|Kill Start WL)\\b");
										Matcher actionMatch = actionPatern.matcher(loggedLine);

										if (actionMatch.find()) {
											// System.err.println(loggedLine);
											String actionTimeStamp1 = tokens[0] + " " + tokens[1] + " " + tokens[2] + " "
											        + tokens[3] + " "+tokens[4]+ " " + tokens[5];
											String actionTimeStamp = tokens[0] + " " + tokens[1] + " " + tokens[2] + " "
											        + tokens[3] + " "+ tokens[5];
											SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy");

											boolean result = tokens[9].matches("brms-.*");
											String loggedHostAndPort;
											String loggedHost;
											String loggedPort;
											String loggedUserAction;

											if (result) {
												loggedUserAction = tokens[6] + " " + tokens[7] + " " + tokens[8];
												//System.out.println("Logged UserAction :" + loggedUserAction);
												loggedHostAndPort = tokens[9] + ":" + tokens[10];
												loggedHost = tokens[9];
												loggedPort = tokens[10];
											} else {
												loggedUserAction = tokens[6] + " " + tokens[7];
												loggedUserAction.trim();
												// System.out.println("******
												// Logged UserAction
												// :"+loggedUserAction);
												loggedHostAndPort = tokens[8] + ":" + tokens[9];
												loggedHost = tokens[8];
												loggedPort = tokens[9];
											}

											allHostList.add(actionTimeStamp1+"|"+loggedHostAndPort+"|"+loggedUserAction);
											
											/*if ((loggedUserAction.matches("Stop WL|Kill WL"))) {
												uniqueHostLogMap.put(loggedHostAndPort,
												        actionTimeStamp + "$" + loggedUserAction);
											}*/

											if ((loggedUserAction.matches("Start WL|Restart WL|Stop WL|Kill WL"))) {
												Date loggedTimeStamp = formatter.parse(actionTimeStamp);
												if (uniqueHostLogMap.containsKey(loggedHostAndPort)) {
													String timeAndActions = uniqueHostLogMap.get(loggedHostAndPort);
													
													String[] timeAndAction = timeAndActions.split("\\$");
													System.out.println("Logged Time Stamp"+loggedTimeStamp);
													System.out.println("Existing"+timeAndAction[0]);
													String[] timeStamp = timeAndAction[0].split(" ");
													String existingTimeStamp = null;
													if(timeStamp[5]!=null){
														existingTimeStamp = timeStamp[0] + " " + timeStamp[1] + " " + timeStamp[2] + " "
													        + timeStamp[3] + " " + timeStamp[5];
													} else {
														existingTimeStamp = timeStamp[0] + " " + timeStamp[1] + " " + timeStamp[2] + " "
														        + timeStamp[3] + " " + timeStamp[4];
													} 
													
													Date recordedTimeStamp1 = formatter.parse(existingTimeStamp);
													
													if(recordedTimeStamp1.before(loggedTimeStamp)) {
														System.out.println(loggedHostAndPort+"$$$$$$$ RECORDED TIMESTAMP :"+existingTimeStamp+"**loggedUserAction**"+loggedUserAction);
														System.out.println(loggedHostAndPort+"$$$$$$$ LOGGED TIMESTAMP :"+loggedTimeStamp+"**loggedUserAction**"+loggedUserAction);
														uniqueHostLogMap.put(loggedHostAndPort,
														        loggedTimeStamp + "$" + loggedUserAction);
													} else {
														System.out.println(loggedHostAndPort+" *****RECORDED TIMESTAMP :"+existingTimeStamp+"**loggedUserAction**"+loggedUserAction);
														System.out.println(loggedHostAndPort+" *****RECORDED TIMESTAMP :"+loggedTimeStamp+"**loggedUserAction**"+loggedUserAction);
														uniqueHostLogMap.put(loggedHostAndPort,
														        loggedTimeStamp + "$" + loggedUserAction);
													}
													//System.out.println("Timestamp"+timeAndAction[0]);
												} else {
													System.out.println(loggedHostAndPort+" *****FRSSHH TIMESTAMP :"+actionTimeStamp1+ "**loggedUserAction**"+loggedUserAction);
													uniqueHostLogMap.put(loggedHostAndPort,
													        actionTimeStamp1 + "$" + loggedUserAction);
												}
												
											}
											// uniqueHostLogMap.put(loggedHostAndPort,
											// actionTimeStamp+"$"+loggedUserAction);
										}

									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException ioe) {
								System.out.println("IOException :" + ioe);
							}
						}
					}
				}
			}
		}
		
		System.out.println("******** All Filtered host :");
		for (Map.Entry<String, String> entry : uniqueHostLogMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String[] timeAndAction = value.split("\\$");
			System.out.println("TimeStamp: "+timeAndAction[0]);
			System.out.println("User Action: "+timeAndAction[1]);
			// System.out.println(key);
			if(value.contains("Restart WL")) {
				
				try {
					System.out.println(key + "|" + value);
					Date currentTimeStamp =  new Date(System.currentTimeMillis());
					System.out.println("time before sleep :"+currentTimeStamp);
					Thread.sleep(1000);
					Date currentTimeStamp1 =  new Date(System.currentTimeMillis());
					System.out.println("time after sleep :"+currentTimeStamp1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (value.contains("Start WL")) {
				System.out.println(key + "|" + value);
			}else {
				System.out.println("Skipping....."+value);
				
				//System.out.println(key + "|" + value);
			} 
		}
		System.out.println("******** All collected host :");
		if(!allHostList.isEmpty()) {
			for (int i = 0; i < allHostList.size(); i++) {
				System.out.println(allHostList.get(i));
			}
			
		}
	}
}

