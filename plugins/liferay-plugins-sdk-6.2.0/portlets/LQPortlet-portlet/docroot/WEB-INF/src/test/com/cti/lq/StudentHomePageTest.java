package test.com.cti.lq;


import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.util.LQUnitTestDataSourceSetup;

public class StudentHomePageTest {
	
	// This class tests for whether a leader is appearing in home page or not.

	@BeforeClass
	public static void setUpDataSource() throws Exception {
		LQUnitTestDataSourceSetup.setUpDataSource();
	}


	@Test
	public void testLeaderExists() {
		LQLeaderService leaderService = new LQLeaderServiceImpl();
		List<LeaderBean> leaderList   = new ArrayList<LeaderBean>();

		try {
			leaderList = leaderService.getLeaderList(leaderList, null);
			System.out.println("leaderList size=" + leaderList.size());
			if (leaderList.size() > 0) {
				// Leader is exists. So Test passes.
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertTrue(leaderList.size() > 0);
	}
	
	
	private static void setUpDBSource() {
		
		try {
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
			InitialContext ic = new InitialContext();
			
			java.util.ResourceBundle rb= ResourceBundle.getBundle("test.com.cti.lq.UnitTest");
			
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
		
	}

}
