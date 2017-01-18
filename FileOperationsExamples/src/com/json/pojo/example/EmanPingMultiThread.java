package com.json.pojo.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import com.json.pojo.example.MutltiTreadFileDataMapCreation.MyRunnable;

import jdk.nashorn.internal.parser.TokenKind;

public class EmanPingMultiThread {
	private static final int MYTHREADS = 30;
	private static ConcurrentHashMap<String, String> domainStatusMap = new ConcurrentHashMap<String, String>();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		String folderLoc = "/opt/brms/shared/scripts/";
		Collection<File> fileList = new ArrayList<File>();
		Iterator<File> fileListIterator = null;
		// String[] lifecycle = { "DEV","PRD","LT","STG","POC" };
		String[] lifecycle = { "PRD","DEV" };
		//String[] lifecycle = { "PRD"};

		for (int i = 0; i < lifecycle.length; i++) {

			String lc = lifecycle[i];

			final String fileFilterString = "brms_vm_cfg_*" + lc + ".txt";
			fileList = FileUtils.listFiles(new File(folderLoc), new WildcardFileFilter(fileFilterString), null);
			File file;
			fileListIterator = fileList.iterator();
			while (fileListIterator.hasNext()) {
				file = (File) fileListIterator.next();
				System.out.println("-----------------------------------------------");
				System.out.println("Read config file" + file.getName());
				System.out.println("-----------------------------------------------");

				if (file.exists()) {
					List<String> lines = FileUtils.readLines(file);
					for (String line : lines) {
						if ((!line.contains("CONTEXT")) || (!line.contains(""))) {
							if (!line.contains("#")) {
								String[] tokens = line.split(",");
								String[] domainPath = tokens[9].split("/");
								String allOutput;
								if(tokens.length == 16) {
									//domainPath = tokens[9].split("/");
									String[] domainAppPath = new String[3];
									allOutput = tokens[10] + "|" + tokens[0] + "|" + tokens[1] + "|" + tokens[2]
								        + "|" + tokens[3] + "|" + tokens[4] + "|" + tokens[5] + "|" + tokens[6] + "|"
								        + tokens[7] + "|" + tokens[8] + "|" + tokens[9] + "|" + tokens[14] + "|" + tokens[15];
									String [] appList = tokens[15].split("\\:");
									for (int j = 0; j < appList.length; j++) {
										//System.out.println("Domain/App:: "+tokens[9]+"/"+appList[j]);
										if (domainPath.length == 3) {
											domainAppPath[1] =  domainPath[1].replace("/", "");
											domainAppPath[2] = appList[j];
										} else {
											domainAppPath[1] = tokens[9].replace("/", "");
											domainAppPath[2] = appList[j];
										}
										System.out.println("Domain/App:: "+domainAppPath[1]+"/"+domainAppPath[2]);
										System.out.println("DOMAIN PATH LENGTH"+domainAppPath.length);
										System.out.println("DOMIAN PATH 1ts POS: "+domainAppPath[1]);
										System.out.println("DOMIAN PATH 2ND POS: "+domainAppPath[2]);
										
										Runnable worker = new MyRunnable(tokens[10], tokens[0], tokens[3], domainAppPath);
										executor.execute(worker);
										
									}
									
								} /*else if(tokens.length >= 15) {
									//domainPath = tokens[9].split("/");
									allOutput = tokens[10] + "|" + tokens[0] + "|" + tokens[1] + "|" + tokens[2]
								        + "|" + tokens[3] + "|" + tokens[4] + "|" + tokens[5] + "|" + tokens[6] + "|"
								        + tokens[7] + "|" + tokens[8] + "|" + tokens[9] + "|" + tokens[14];
									
									Runnable worker = new MyRunnable(tokens[10], tokens[0], tokens[3], domainPath);
									executor.execute(worker);
								} */
								else {
									//domainPath = tokens[9].split("/");
									allOutput = tokens[10] + "|" + tokens[0] + "|" + tokens[1] + "|" + tokens[2]
									        + "|" + tokens[3] + "|" + tokens[4] + "|" + tokens[5] + "|" + tokens[6] + "|"
									        + tokens[7] + "|" + tokens[8] + "|" + tokens[9];
									
									Runnable worker = new MyRunnable(tokens[10], tokens[0], tokens[3], domainPath);
									executor.execute(worker);
									
								}
								//System.out.println("All input");
								//System.out.println(allOutput);

								

							}
						}
					}
				} else {
					System.out.println("No matching file exists to read...!");
				}
				
			}
		}
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
			
		}
		System.out.println("\nFinished all threads");
		System.out.println(domainStatusMap);
		System.out.println("");
		
	}

	public static class MyRunnable implements Runnable {
		private String lifecycle;
		private String host;
		private String port;
		private String domain;
		private String app;

		MyRunnable(String lifecycle, String hostname, String port, String[] domainPath) {
			this.lifecycle = lifecycle;
			this.host = hostname;
			this.port = port;
			if (domainPath.length == 3) {
				//String domain = domainPath[1].replace("/", "");
				this.domain = domainPath[1];
				this.app = domainPath[2];
			} else {
				this.domain = domainPath[1];
				this.app = null;
			}
		}

		@Override
		public void run() {
			String key;
			
			if(app == null) {
				key = lifecycle+"_"+domain;
			} else {
				key = lifecycle+"_"+domain+"_"+app;
			}
			String emanResult = emanStatusResponse(lifecycle, domain, app);
			//System.out.println("THREAD NAME" + Thread.currentThread().getName());
			//System.out.println(" EMAN RESULT :" + lifecycle + "|" + domain + "|" + app + "|" + emanResult);
			domainStatusMap.put(key, emanResult);
		}
	}

	private static String emanStatusResponse(String lifecycle, String domain, String dApp) {
		// TODO Auto-generated method stub
		String webPage = null;
		String resMessage = null;
		//domain = domain.replace("/", "");
		if (lifecycle.equals("dev")) {
			if (dApp == null) {
				webPage = "https://ibpm-dev.cisco.com/" + domain + "/brmsadmin/eman";
			} else {
				webPage = "https://ibpm-dev.cisco.com/" + domain + "/" + dApp + "/brmsadmin/eman";
			}
		} else if (lifecycle.equals("stage")) {
			if (dApp == null) {
				webPage = "https://ibpm-stage.cisco.com/" + domain + "/brmsadmin/eman";
			} else {
				webPage = "https://ibpm-stage.cisco.com/" + domain + "/" + dApp + "/brmsadmin/eman";
			}
		} else if (lifecycle.equals("lt")) {
			if (dApp == null) {
				webPage = "https://ibpm-lt.cisco.com/" + domain + "/brmsadmin/eman";
			} else {
				webPage = "https://ibpm-lt.cisco.com/" + domain + "/" + dApp + "/brmsadmin/eman";
			}
		} else if (lifecycle.equals("poc")) {
			if (dApp == null) {
				webPage = "https://ibpm-poc.cisco.com/" + domain + "/brmsadmin/eman";
			} else {
				webPage = "https://ibpm-poc.cisco.com/" + domain + "/" + dApp + "/brmsadmin/eman";
			}
		} else if (lifecycle.equals("prod")) {
			if (dApp == null) {
				webPage = "https://ibpm.cisco.com/" + domain + "/brmsadmin/eman";
			} else {
				webPage = "https://ibpm.cisco.com/" + domain + "/" + dApp + "/brmsadmin/eman";
			}
		}
		// webPage="https://ibpm.cisco.com/gssc/brmsadmin/eman";

		try {
			System.out.println("INPUT EMAN URL TO CHECK::" + webPage);
			URL url = new URL(webPage);

			/*HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			// httpCon.setRequestProperty("Authorization", "Basic " +
			// authStringEnc);
			int statusCode = httpCon.getResponseCode();
			InputStream is = httpCon.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			// String resContent = http.getContentEncoding().toString();

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String emanResponse = sb.toString();
			
			// System.out.println(" ******* URL RESPONSE IS : *****");
			// System.out.println("URL : "+webPage); System.out.println(
			// "Response STATUS CODE is :"+statusCode);
			 

			System.out.println(emanResponse);
			if (emanResponse.contains("EMAN OK")) {
				// System.out.println(emanResponse);
				System.out.println("SUCCESS EMAN Response" + emanResponse);
				resMessage = "Up";
			} else {
				System.out.println("Failure EMAN Response" + emanResponse);
				resMessage = "Down";
			}*/
			// System.out.println(" *******************************");
			resMessage = "Up";

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (resMessage == null) {
				resMessage = "Down";
			}
		}
		return resMessage;
	}
}
