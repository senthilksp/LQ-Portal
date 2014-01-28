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
		if(LQUnitTestDataSourceSetup.setUpDataSource() != null) {
			LQUnitTestDataSourceSetup.setUpDataSource();
		};
	}


	@Test
	public void testGetLeaderList() {
		LQLeaderService leaderService = new LQLeaderServiceImpl();
		List<LeaderBean> leaderList   = new ArrayList<LeaderBean>();

		try {
			leaderList = leaderService.getLeaderList(leaderList, null);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertTrue(leaderList.size() > 0);
	}

}
