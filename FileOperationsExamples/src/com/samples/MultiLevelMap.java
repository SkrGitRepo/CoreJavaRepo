package com.samples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.json.pojo.example.LifecycleLevel;


public class MultiLevelMap {
	public static void main(String args[]) throws IOException {
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		HashMap<String, String> map4 = new HashMap<String, String>();
		HashMap<String, String> map5 = new HashMap<String, String>();
		Map<String, String> outerMap = new HashMap<String, String>();
		HashMap<String, String> statusMap = new HashMap<String, String>();
		HashMap<String, ArrayList<String>> innerMap = new HashMap<String, ArrayList<String>>();
		
		
		HashMap lifeCycle = new HashMap();
		
		
		String folderLoc = "/opt/brms/shared/scripts/";
		Collection<File> fileList = new ArrayList<File>();
		Iterator<File> fileListIterator = null;
		String searchDomain = null;
		String searchApp = null;
		String vdc = "PRD1";
		String env = "PRD";
		final String fileFilterString = "brms_vm_cfg_" + vdc + "_*.txt";
		fileList = FileUtils.listFiles(new File(folderLoc), new WildcardFileFilter(fileFilterString), null);
		File file;
		// host = "brms-prd1-13";
		// port ="7005";
		String output = null;
		fileListIterator = fileList.iterator();
		while (fileListIterator.hasNext()) {
			file = (File) fileListIterator.next();
			System.out.println("-----------------------------------------------");
			System.out.println("Read config file");
			System.out.println("-----------------------------------------------");
			System.out.println(file.getName());
			if (file.exists()) {
				List<String> lines = FileUtils.readLines(file);
				for (String line : lines) {
					if (!line.contains("CONTEXT")) {
						if (!line.contains("#")) {
							// if(line.contains(searchDomain)) {
							String[] tokens = line.split(",");
							/*
							 * System.out.println("TOKEN LENGTH ::" +
							 * tokens.length); System.out.println(tokens[0]);
							 * System.out.println(tokens[3]);
							 * 
							 * System.out.println(tokens[0]);
							 * System.out.println(tokens[3]);
							 * System.out.println(tokens[9]);
							 * System.out.println(tokens[10]);
							 */

							String[] domainPath = tokens[9].split("/");
							ArrayList<String> appList = new ArrayList<String>();
							String domainApp =  null;
							if (domainPath.length == 3) {
								appList.add(domainPath[2]);
								domainApp = domainPath[2];
							} else {
								appList.add("NA");
							}
							// System.out.println("Domain is "+domainPath[1]);

							output = tokens[10] + "|" + domainPath[1] + "|" + tokens[0] + ":" + tokens[3];
							//System.out.println("-----------------------------------------------");
							// System.out.println("Final Output :");
							// System.out.println(output);
							//System.out.println("-----------------------------------------------");
							//System.out.println("All Output :");
							
							String allOutput = tokens[10] + "|" + tokens[0] + "|" + tokens[1] + "|" + tokens[2] + "|"
							        + tokens[3] + "|" + tokens[4] + "|" + tokens[5] + "|" + tokens[6] + "|" + tokens[7]
							        + "|" + tokens[8] + "|" + tokens[9];
							//System.out.println(allOutput);
							//HashMap lifeCycle = new HashMap();
							HashMap domain = new HashMap();
							HashMap existLifeCycle = null;
							if(lifeCycle.containsKey(tokens[10])){
								existLifeCycle = (HashMap) lifeCycle.get(tokens[10]);
								HashMap existDomain = null;
								if(existLifeCycle.containsKey(domainPath[1])) {
									existDomain = (HashMap) existLifeCycle.get(domainPath[1]); 
									HashMap existApplication = null;
									if(existDomain.containsKey(domainPath[2])) {
										//existApplication = (HashMap)existDomain.get(domainPath[2]);
										//List hostList = (ArrayList) existApplication.get(domainPath[2]);
										List hostList = (ArrayList) existApplication.get(domainPath[2]);
										hostList.add(tokens[0]);
//										existApplication.put(domainPath[2], hostList)
										
									} else {
										List hostList = null;
										if(domain.containsKey("Hosts")) {
											 hostList = (ArrayList) domain.get("Hosts");
											hostList.add(tokens[0]);
										} else {
											hostList = new ArrayList();
											hostList.add(tokens[0]);
										}
										domain.put("Hosts", hostList);
									}
									
									
								} else {

									HashMap application = new HashMap();
									ArrayList hostList = new ArrayList();
									appList.add(tokens[0]);
									if(domainApp != null) {
										application.put(domainApp, appList);
										domain.put(domainPath[1], application);
									} else {
										domain.put("hosts", appList);
									}
									lifeCycle.put(tokens[10], domain);
								}
								
							} else {
								
								HashMap application = new HashMap();
								ArrayList hostList = new ArrayList();
								appList.add(tokens[0]);
								if(domainApp != null) {
									application.put(domainApp, appList);
									domain.put(domainPath[1], application);
								} else {
									domain.put("hosts", appList);
								}
								lifeCycle.put(tokens[10], domain);
							}
							//lifeCycle.get(tokens[10];
							
							//if (innerMap.containsKey(domainPath[1])) {
								//arrAppHostPort.getadd(tokens[0] + "|" + tokens[3]);
								//innerMap.get(domainPath[1]).add(tokens[0]);
								//innerMap.get(domainPath[1]);
								//statusMap.put("Status", "UP");
								//innerMap.get(domainPath[1]).add(statusMap);
								//innerMap.put(domainPath[1], arrAppHostPort);
							//} else{ 
								//ArrayList<String> arrAppHostPort =  new ArrayList<String>();
								//arrAppHostPort.add(tokens[0]);
								//innerMap.put(domainPath[1], arrAppHostPort);
							}
							//outerMap.put(tokens[10] + "_" + domainPath[1],innerMap.toString());
					}
				}
			}
		}
		
		
		System.out.println(lifeCycle.toString());
		//Iterator it = .entrySet().iterator();
		/* while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + "{\n" + pair.getValue());
			System.out.println();
			System.out.println("}");
			// it.remove(); // avoids a ConcurrentModificationException
		}
		*/
		/*Iterator innerIt = innerMap.entrySet().iterator();
		while (innerIt.hasNext()) {
			Map.Entry pair = (Map.Entry) innerIt.next();
			//System.out.println(pair.getKey() + " = " + pair.getValue());
			// it.remove(); // avoids a ConcurrentModificationException
		}*/
	}
}
