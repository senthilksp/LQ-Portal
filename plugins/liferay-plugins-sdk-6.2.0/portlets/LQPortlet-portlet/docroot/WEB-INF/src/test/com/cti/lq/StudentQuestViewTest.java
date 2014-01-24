package test.com.cti.lq;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.util.LQUnitTestDataSourceSetup;

public class StudentQuestViewTest {

	// This class tests for all details of a quest can be viewable by a student.
	// Sample Quest-Id and User_id combination is taken from
	// "UnitTest.properties" file.

	@BeforeClass
	public static void setUpDataSource() throws Exception {
		LQUnitTestDataSourceSetup.setUpDataSource();
	}

	@Test
	public void studentQuestView() throws SQLException {

		java.util.ResourceBundle rb = ResourceBundle
				.getBundle("test.com.cti.lq.UnitTest");
		String quest[] = rb.getString("student_questId_and_userId").split(",");
		assertTrue(quest.length > 0);

		if (quest.length > 0) {

			int userId = Integer.valueOf(quest[0]);
			int questId = Integer.valueOf(quest[1]);

			LQLeaderService leaderService = new LQLeaderServiceImpl();
			List<QuestViewBean> questList = new ArrayList<QuestViewBean>();

			questList = leaderService.getQuestDetails(questList, null, userId,
					questId);
			assertTrue(questList.size() > 0);
		}

	}

}
