package test.com.cti.lq;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ResourceBundle;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.util.LQUnitTestDataSourceSetup;

public class LeaderEditTest {

	// This class tests whether the valid leader data is updated in the back end table or not.
	
	
	@BeforeClass
	public static void setUpDataSource() throws Exception {
		LQUnitTestDataSourceSetup.setUpDataSource();
	}
	
	
	@Test
	public void testSaveEditLeaderValid() throws SQLException {
		
		java.util.ResourceBundle rb = ResourceBundle.getBundle("test.com.cti.lq.UnitTest");
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
		
		LQLeaderService lqServiceLayer = new LQLeaderServiceImpl();
		boolean saveSuccess = lqServiceLayer.saveLeaderDetails(lb);
		System.out.println("SaveSucess=" + saveSuccess);
		assertTrue(saveSuccess);
		
	}
	
}
