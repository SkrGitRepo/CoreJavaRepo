package com.samples;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;


public class ExecutePerlFileForJSON {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String[] command = {"perl","c:/opt/brms/shared/scripts/JsonDataTest.pl"};
		String outputFile = "C:/opt/brms/shared/scripts/JsonOutput.txt";
		File output = new File(outputFile);
			try {
				runCommand(command, output);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void runCommand(final String[] command, final File output) throws IOException, InterruptedException {
     	final ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true); // optional; easier for this case to only
        // handle one stream
        System.out.println(pb.toString());
        pb.redirectOutput(Redirect.to(output));
        final Process p = pb.start();
 
        if (p.waitFor() != 0) {
            // throw an exception / return error message
        	System.err.println("Command Execution failed");
        } else {
        	System.out.println("Command Executed successfully. ");
        }
	}

}
