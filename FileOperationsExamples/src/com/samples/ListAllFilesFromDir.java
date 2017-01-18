package com.samples;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class ListAllFilesFromDir {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final File folder = new File("/opt/brms/shared/scripts/");
		listFilesForFolder(folder);
		
		File f = new File("."); // current directory

		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith("*.txt")) {
					return true;
				} else {
					return false;
				}
			}
		};

		File[] files = f.listFiles(textFilter);
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.print("directory:");
			} else {
				System.out.print("     file:");
			}
			System.out.println(file.getCanonicalPath());
		}
	}

	public static void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}

	
	
	
}
