package com.json.pojo.example;

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
import com.google.gson.Gson;

public class NewJsonGenerator {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String folderLoc = "/opt/brms/shared/scripts/";
		Collection<File> fileList = new ArrayList<File>();
		Iterator<File> fileListIterator = null;
		String searchDomain = null;
		String searchApp = null;
		List<Map<String, Object>> superJsonResponse = new ArrayList<Map<String, Object>>();
		
		String [] lcList =  {"DEV","PRD","LT","STG","POC"};

		for (String lc : lcList) {
			String env = lc;
			final String fileFilterString = "brms_vm_cfg_*" + env + ".txt";
			fileList = FileUtils.listFiles(new File(folderLoc), new WildcardFileFilter(fileFilterString), null);
			File file;

			JsonResponse jsonResponse = new JsonResponse();
			
			List<String> hostList = null;
			Map<String, Object> appMap = new HashMap<String, Object>();
			Map<String, Object> tempMap = new HashMap<String, Object>();
			Map<String, Object> domainMap = new HashMap<String, Object>();
			Map<String, Object> lifecycleMap = new HashMap<String, Object>();
			Map<String, Object> finalJsonMap = new HashMap<String, Object>();
			Map<String, Object> hostMap = null;
			
			fileListIterator = fileList.iterator();
		
			while (fileListIterator.hasNext()) {
				file = (File) fileListIterator.next();
				System.out.println("-----------------------------------------------");
				System.out.println("Read config file"+file.getName());
				System.out.println("-----------------------------------------------");

				if (file.exists()) {
					List<String> lines = FileUtils.readLines(file);
					for (String line : lines) {
						if ( (!line.contains("CONTEXT")) || (!line.contains("#")) || (!line.contains(""))) {
								String[] tokens = line.split(",");
								String[] domainPath = tokens[9].split("/");

								String allOutput = tokens[10] + "|" + tokens[0] + "|" + tokens[1] + "|" + tokens[2]
								        + "|" + tokens[3] + "|" + tokens[4] + "|" + tokens[5] + "|" + tokens[6] + "|"
								        + tokens[7] + "|" + tokens[8] + "|" + tokens[9];
								System.out.println("All input line:(lc,host,)"+allOutput);
								
								if ( (domainPath[1].equals(searchDomain)) && ((domainPath.length >= 2)) && (searchApp == null) ) {
									finalJsonMap = createJson(jsonResponse, domainPath, hostList, tokens, hostMap,
									        domainMap, lifecycleMap, appMap, tempMap);
								} else if ( ( domainPath[1].equals(searchDomain)) && (domainPath.length == 3) && (domainPath[2].equals(searchApp)) ) {
									finalJsonMap = createJson(jsonResponse, domainPath, hostList, tokens, hostMap,
									        domainMap, lifecycleMap, appMap, tempMap);
								} else if ( (searchDomain == null) && (searchApp == null) ){
									finalJsonMap = createJson(jsonResponse, domainPath, hostList, tokens, hostMap,
									        domainMap, lifecycleMap, appMap, tempMap);
								}
						}
					}
				}
			}
			if (!finalJsonMap.isEmpty()) {
				superJsonResponse.add(finalJsonMap);
			}
		}
		if (superJsonResponse != null) {
			String outputJson = new Gson().toJson(superJsonResponse);
			System.out.println(outputJson);
		}
	}

	public static Map<String, Object> createJson(JsonResponse jsonResponse, String[] domainPath, List<String> hostList,
	        String[] tokens, Map<String, Object> hostMap, Map<String, Object> domainMap,
	        Map<String, Object> lifecycleMap, Map<String, Object> appMap, Map<String, Object> tempMap) {
		if (!(null == jsonResponse.getDomain()) && jsonResponse.getDomain().containsKey(domainPath[1])) {

			if (domainPath.length == 3) {

				@SuppressWarnings("unchecked")
				Map<String, Object> domainLevelApps = (Map<String, Object>) jsonResponse.getDomain().get(domainPath[1]);
				@SuppressWarnings("unchecked")
				Map<String, Object> apps = (Map<String, Object>) domainLevelApps.get("APPS");
				if (!domainLevelApps.containsKey("APPS")) {

					hostList = new ArrayList<String>();
					hostList.add(tokens[0] + ":" + tokens[3]);
					hostMap = new HashMap<String, Object>();
					hostMap.put("HOSTS", hostList);
					Map<String, Object> newApp = new HashMap<String, Object>();
					newApp.put(domainPath[2], hostMap);
					domainLevelApps.put("APPS", newApp);

					jsonResponse.setApp(domainLevelApps);

					domainMap.put(domainPath[1], domainLevelApps);

					jsonResponse.setDomain(domainMap);

				} else if (!apps.containsKey(domainPath[2])) {

					hostList = new ArrayList<>();
					hostList.add(tokens[0] + ":" + tokens[3]);

					hostMap = new HashMap<String, Object>();
					hostMap.put("HOSTS", hostList);

					@SuppressWarnings("unchecked")
					Map<String, Object> existingAppMap = (Map<String, Object>) domainLevelApps.get("APPS");

					existingAppMap.put(domainPath[2], hostMap);
					domainLevelApps.put("APPS", existingAppMap);
					jsonResponse.setApp(domainLevelApps);

					domainMap.put(domainPath[1], domainLevelApps);

					jsonResponse.setDomain(domainMap);

				} else if (apps.containsKey(domainPath[2])) {

					@SuppressWarnings("unchecked")
					Map<String, Object> existingAppMap = (Map<String, Object>) domainLevelApps.get("APPS");

					@SuppressWarnings("unchecked")
					Map<String, Object> value = (Map<String, Object>) existingAppMap.get(domainPath[2]);

					@SuppressWarnings("unchecked")
					List<String> existingHosts = (List<String>) value.get("HOSTS");

					existingHosts.add(tokens[0] + ":" + tokens[3]);
					hostMap = new HashMap<String, Object>();
					hostMap.put("HOSTS", existingHosts);

					existingAppMap.put(domainPath[2], hostMap);
					domainLevelApps.put("APPS", existingAppMap);
					jsonResponse.setApp(domainLevelApps);

					domainMap.put(domainPath[1], domainLevelApps);

					jsonResponse.setDomain(domainMap);
				}
			} else {

				@SuppressWarnings("unchecked")
				Map<String, Object> dHosts = (Map<String, Object>) jsonResponse.getDomain().get(domainPath[1]);
				if (!dHosts.containsKey("HOSTS")) {

					hostList = new ArrayList<>();
					hostList.add(tokens[0] + ":" + tokens[3]);

					@SuppressWarnings("unchecked")
					Map<String, Object> map = (Map<String, Object>) jsonResponse.getDomain().get(domainPath[1]);

					map.put("HOSTS", hostList);

					jsonResponse.setHost(map);

					domainMap.put(domainPath[1], map);

					jsonResponse.setDomain(domainMap);

				} else {

					@SuppressWarnings("unchecked")
					Map<String, Object> domMap = (Map<String, Object>) jsonResponse.getDomain().get(domainPath[1]);

					@SuppressWarnings("unchecked")
					List<String> existingHosts = (List<String>) domMap.get("HOSTS");

					existingHosts.add(tokens[0] + ":" + tokens[3]);

					hostMap = new HashMap<String, Object>();

					hostMap.put("HOSTS", existingHosts);

					hostMap.putAll(domMap);

					jsonResponse.setHost(hostMap);

					domainMap.put(domainPath[1], hostMap);

					jsonResponse.setDomain(domainMap);

				}
			}

			lifecycleMap.put(tokens[10], domainMap);
			jsonResponse.setLifecycle(lifecycleMap);

		} else {

			if (!(null == jsonResponse.getLifecycle()) && jsonResponse.getLifecycle().containsKey(tokens[10])) {

				@SuppressWarnings("unchecked")
				Map<String, Object> lifeCycleMap = (Map<String, Object>) jsonResponse.getLifecycle().get(tokens[10]);

				if (domainPath.length == 3) {

					hostList = new ArrayList<String>();
					hostList.add(tokens[0] + ":" + tokens[3]);
					hostMap = new HashMap<String, Object>();
					hostMap.put("HOSTS", hostList);

					appMap.put(domainPath[2], hostMap);
					jsonResponse.setApp(appMap);

					tempMap.put("APPS", appMap);

					lifeCycleMap.put(domainPath[1], tempMap);

					jsonResponse.setDomain(lifeCycleMap);

				} else {

					hostList = new ArrayList<>();
					hostList.add(tokens[0] + ":" + tokens[3]);

					hostMap = new HashMap<String, Object>();
					hostMap.put("HOSTS", hostList);

					lifeCycleMap.put(domainPath[1], hostMap);

					jsonResponse.setDomain(lifeCycleMap);

				}
				lifecycleMap.put(tokens[10], lifeCycleMap);
				jsonResponse.setLifecycle(lifecycleMap);
			} else {

				if (domainPath.length == 3) {
					hostList = new ArrayList<String>();
					hostList.add(tokens[0] + ":" + tokens[3]);
					hostMap = new HashMap<String, Object>();
					hostMap.put("HOSTS", hostList);

					appMap.put(domainPath[2], hostMap);
					jsonResponse.setApp(appMap);

					tempMap.put("APPS", appMap);

					domainMap.put(domainPath[1], tempMap);

					jsonResponse.setDomain(domainMap);

				} else {

					hostList = new ArrayList<>();
					hostList.add(tokens[0] + ":" + tokens[3]);

					hostMap = new HashMap<String, Object>();
					hostMap.put("HOSTS", hostList);

					domainMap.put(domainPath[1], hostMap);

					jsonResponse.setDomain(domainMap);

				}
				lifecycleMap.put(tokens[10], domainMap);
				jsonResponse.setLifecycle(lifecycleMap);
			}

		}
		return lifecycleMap;
	}
}

