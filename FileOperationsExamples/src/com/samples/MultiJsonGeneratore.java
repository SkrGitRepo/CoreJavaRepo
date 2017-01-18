package com.samples;

import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

public class MultiJsonGeneratore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FileWriter writer = null;
	    try {
	        writer = new FileWriter("C:\\Users\\sumkuma2\\Downloads\\jsontext.txt");
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    JsonGenerator gen = Json.createGenerator(writer);

	    gen.writeStartObject().write("name", "sample")
	        .writeStartArray("DEV")
	          .writeStartObject().write("Domain","CVC").write("STATUS","UP")
	             .writeStartArray("APPS")
	                .writeStartObject().write("name", "SCORE").write("STATUS", "UP")
	                .writeEnd()
	                .writeStartObject().write("name", "SDG").write("STATUS", "UP")
	                .writeEnd()
	            .writeEnd()
	            .writeStartArray("HOSTS")
                	.writeStartObject().write("name", "brms-prd1-2").write("PORT", "7034")
                	.writeEnd()
                	.writeStartObject().write("name", "brms-prd1-2").write("PORT", "7034")
                	.writeEnd()
                .writeEnd()
	          .writeEnd()
	            .writeStartObject().write("DOMAIN", "GSCC")
	              .writeStartArray("setDef")
	                .writeStartObject().write("name", "abc").write("type", "STRING")
	                .writeEnd()
	                .writeStartObject().write("name", "xyz").write("type", "STRING")
	                .writeEnd()
	              .writeEnd()
	            .writeEnd()
	          .writeEnd()
	        .writeEnd();

	    gen.close();

	}
}
