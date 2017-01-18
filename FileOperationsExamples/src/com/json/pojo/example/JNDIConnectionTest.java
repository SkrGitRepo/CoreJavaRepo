package com.json.pojo.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JNDIConnectionTest {

	public static void main(String[] args) throws NamingException, SQLException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		 	String DATASOURCE_CONTEXT = "weblogic.jndi.WLInitialContextFactory";
	        System.out.println("DATASOURCE_CONTEXT.."+DATASOURCE_CONTEXT);
	        Properties env = new Properties( ); 
	        env.put(Context.INITIAL_CONTEXT_FACTORY,                                                                                                "weblogic.jndi.WLInitialContextFactory"); 
	        env.put(Context.PROVIDER_URL,"t3://abc.com:8001");
	        Context initialContext = new InitialContext(env);
	        DataSource datasource =        (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
	        if (datasource != null) {
	            conn = datasource.getConnection();
	        }
	        else{
	        	System.out.println("Failed to lookup datasource.");
	        } 
		

	}

}
