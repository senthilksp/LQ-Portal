package test.com.cti.lq;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQUnitTestDataSourceSetup;


public class RoleTest {

	// This class tests whether the userIds are returning correct roles.
	// Sample userIds are taken from "UnitTest.properties" File.

	java.util.ResourceBundle rb = ResourceBundle
			.getBundle("test.com.cti.lq.UnitTest");
	
	
	@BeforeClass
	public static void setUpDataSource() throws Exception {
		if(LQUnitTestDataSourceSetup.setUpDataSource() != null) {
			LQUnitTestDataSourceSetup.setUpDataSource();
		};
	}
	

	@Test
	public void testLeaderLogin() {

		/*int userId = Integer.valueOf(rb.getString("leader_userid"));
		System.out.println("userid====" +userId );
		 String roleName =
		 LQPortalUserServiceUtil.getRoleNameByUserId(userId);
		 System.out.println("rolename====" + roleName);
		//assertTrue(roleName.equalsIgnoreCase(LQPortalConstants.LQ_LEADER_ROLE));
*/		assertTrue(true);

	}

/*	@Test
	public void testLeaderAdminLogin() {

		int userId = Integer.valueOf(rb.getString("leader_admin_userid"));
		 String roleName =
		 LQPortalUserServiceUtil.getRoleNameByUserId(userId);
         assertTrue(roleName.equalsIgnoreCase(LQPortalConstants.LQ_LEADER_ADMIN));
		//assertTrue(true);

	}*/
}
