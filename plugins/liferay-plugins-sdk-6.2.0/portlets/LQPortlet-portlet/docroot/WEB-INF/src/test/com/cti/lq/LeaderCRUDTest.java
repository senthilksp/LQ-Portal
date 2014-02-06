package test.com.cti.lq;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ResourceBundle;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.PasswordResetBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.cti.lq.util.LQUnitTestDataSourceSetup;
import com.liferay.portal.model.User;

public class LeaderCRUDTest {

	// This class tests whether the valid leader data is updated in the back end
	// table or not.

	java.util.ResourceBundle rb = ResourceBundle
			.getBundle("test.com.cti.lq.UnitTest");

	LQLeaderService lqServiceLayer = new LQLeaderServiceImpl();

	@BeforeClass
	public static void setUpDataSource() throws Exception {
		if (LQUnitTestDataSourceSetup.setUpDataSource() != null) {
			LQUnitTestDataSourceSetup.setUpDataSource();
		}
		;
	}

	// Test for Edit leader Details.
	@Test
	public void testSaveLeaderDetails() throws SQLException {

		LeaderBean lb = new LeaderBean();

		lb.setUserid(Integer.valueOf(rb.getString("edit_leader_user_id")));
		lb.setFirstname(rb.getString("edit_leader_first_name"));
		lb.setLastname(rb.getString("edit_leader_last_name"));
		lb.setPrimaryPhone(rb.getString("edit_leader_primary_phone"));
		lb.setBusinessName(rb.getString("edit_leader_buniess_name"));
		lb.setCountry(rb.getString("edit_leader_country"));
		lb.setCity(rb.getString("edit_leader_city"));
		lb.setPhotoURL(rb.getString("edit_leader_url"));
		lb.setBioStatement(rb.getString("edit_leader_biostate"));
		lb.setWebsite(rb.getString("edit_leader_website"));
		lb.setFacultyRole(rb.getString("edit_leader_faculty_role"));

		boolean saveSuccess = lqServiceLayer.saveLeaderDetails(lb);
		assertTrue(saveSuccess);

	}

	// Test for Add Leader Details.
	@Test
	public void TestAddLeaderDetails() throws SQLException {

		LeaderBean lb = new LeaderBean();
		boolean saveSuccess = false;

		lb.setUserid(Integer.valueOf(rb.getString("add_leader_user_id")));
		lb.setFirstname(rb.getString("add_leader_first_name"));
		lb.setLastname(rb.getString("add_leader_last_name"));
		lb.setPrimaryPhone(rb.getString("add_leader_primary_phone"));
		lb.setBusinessName(rb.getString("add_leader_buniess_name"));
		lb.setCountry(rb.getString("add_leader_country"));
		lb.setCity(rb.getString("add_leader_city"));
		lb.setPhotoURL(rb.getString("add_leader_url"));
		lb.setBioStatement(rb.getString("add_leader_biostate"));
		lb.setWebsite(rb.getString("add_leader_website"));
		lb.setFacultyRole(rb.getString("add_leader_faculty_role"));
		lb.setEmailAddress(rb.getString("add_leader_email_address"));

		LeaderBean leader = lqServiceLayer.getLeaderDetails(lb, null);
		if (leader.getFirstname() != null) {
			//boolean savedel = lqServiceLayer.deleteLeader(lb);
			//saveSuccess = lqServiceLayer.addLeaderDetails(lb);
		} else {
			saveSuccess = lqServiceLayer.addLeaderDetails(lb);
			assertTrue(saveSuccess);
			
			/*PasswordResetBean resetPassword = new PasswordResetBean();
			resetPassword.setEmailAddress(lb.getEmailAddress());
			resetPassword.setNewPassword(rb.getString("add_leader_password"));
			boolean resetpass = lqServiceLayer.resetPassword(resetPassword);
			assertTrue(resetpass);*/
			
			//Commented because instead of sending mail, liferay asks password reset when the leader logs in again
		}

		
	}

}
