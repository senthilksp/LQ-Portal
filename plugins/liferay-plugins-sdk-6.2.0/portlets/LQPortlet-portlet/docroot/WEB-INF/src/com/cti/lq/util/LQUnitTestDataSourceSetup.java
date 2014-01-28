package com.cti.lq.util;

import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

public class LQUnitTestDataSourceSetup {

	public static InitialContext setUpDataSource() throws Exception {
		
		
		InitialContext ic = null;
		
		try {
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
			
			
			java.util.ResourceBundle rb= ResourceBundle.getBundle("test.com.cti.lq.UnitTest");
		    ic = new InitialContext();
			ic.createSubcontext("java:");
			ic.createSubcontext("java:comp");
			ic.createSubcontext("java:comp/env");
			ic.createSubcontext("java:comp/env/jdbc");
			
			Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();
			source.setDataSourceName(rb.getString("DataSource_Name"));
			source.setServerName(rb.getString("DataSource_ServerName"));
			source.setDatabaseName(rb.getString("DataSource_DatabaseName"));
			source.setUser(rb.getString("DataSource_UserName"));
			source.setPassword(rb.getString("DataSource_Password"));
			source.setMaxConnections(Integer.valueOf(rb.getString("DataSource_Connections")));
			
			ic.bind("java:comp/env/jdbc/LQDatabase", source);

		} catch (NamingException ex) {
			ex.printStackTrace();

		}
		return ic;

	}

}
