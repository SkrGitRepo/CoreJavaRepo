package com.samples;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FileFilterByWildcard {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String folderLoc = "/opt/brms/shared/scripts";
		Collection<File> fileList = new ArrayList<File>();
		Iterator<File> fileListIterator = null;
		String envConfigFile="All";
		String virtualMachine="brms-npr1-dev1";
		File file = null;
		List<String> fileLines = new ArrayList<String>();
		if ("ALL".equals(envConfigFile)) {
			if ("ALL".equals(virtualMachine)) {
				fileList = FileUtils.listFiles(new File(folderLoc),new WildcardFileFilter("*vm_cfg*.txt"), null);
			} else {
				final String fileFilterString = virtualMachine;
				FileFilter fileFilter = new FileFilter() {
					public boolean accept(File file) {
						return file.getName().contains(fileFilterString);
					}
				};
				fileList = Arrays.asList(new File(folderLoc)
						.listFiles(fileFilter));
			}
		} else {
			fileList.add(new File(folderLoc + "/" + envConfigFile));
		}
		
		fileListIterator = fileList.iterator();
		while (fileListIterator.hasNext()) {
			file = (File) fileListIterator.next();
			System.err.println("FileName:"+file.getName());
			//fileLines = FileUtils.readLines(file);
		}
	}

}
