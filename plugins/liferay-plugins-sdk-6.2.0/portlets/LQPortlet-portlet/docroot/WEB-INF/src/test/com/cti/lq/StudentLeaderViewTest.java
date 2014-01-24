/**
 * 
 */
package test.com.cti.lq;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ResourceBundle;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.util.LQUnitTestDataSourceSetup;

/**
 * @author senthil
 * 
 */
public class StudentLeaderViewTest {

	// This class tests for all details of a leader can be viewable by a student.
	// Leader's userid is passed from "UnitTest.properties" File.
	
	@BeforeClass
	public static void setUpDataSource() throws Exception {
		LQUnitTestDataSourceSetup.setUpDataSource();
	}

	
	@Test
	public void studentLeaderView() {

		LQLeaderService leaderService = new LQLeaderServiceImpl();
		java.util.ResourceBundle rb = ResourceBundle
				.getBundle("test.com.cti.lq.UnitTest");

		try {

			int userId = Integer.valueOf(rb.getString("leader_userid"));
			LeaderBean leaderBean = new LeaderBean();
			leaderBean.setUserid(userId);

			leaderBean = leaderService.getLeaderDetails(leaderBean, null);

			assertTrue(leaderBean.getFirstname() != null
					&& leaderBean.getFirstname().length() > 0);
			assertTrue(leaderBean.getLastname() != null
					&& leaderBean.getLastname().length() > 0);
			assertTrue(leaderBean.getEmailAddress() != null
					&& leaderBean.getEmailAddress().length() > 0);
			assertTrue(leaderBean.getPrimaryPhone() != null
					&& leaderBean.getPrimaryPhone().length() > 0);
			assertTrue(leaderBean.getFacultyRole() != null
					&& leaderBean.getFacultyRole().length() > 0);
			assertTrue(leaderBean.getCountry() != null
					&& leaderBean.getCountry().length() > 0);
			assertTrue(leaderBean.getCity() != null
					&& leaderBean.getCity().length() > 0);
			assertTrue(leaderBean.getBusinessName() != null
					&& leaderBean.getBusinessName().length() > 0);
			assertTrue(leaderBean.getBioStatement() != null
					&& leaderBean.getBioStatement().length() > 0);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
