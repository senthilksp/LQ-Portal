package test.com.cti.lq;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.util.LQUnitTestDataSourceSetup;

public class StudentQuestPageTest {

	// This test is for whether a single quest is exists or not.
	// This is not going to check all the leaders have quest or not.
	
	@BeforeClass
	public static void setUpDataSource() throws Exception {
		LQUnitTestDataSourceSetup.setUpDataSource();
	}
	
	@Test
	public void testQuestsExists() {
		LQLeaderService leaderService = new LQLeaderServiceImpl();
		List<LeaderBean> leaderList = new ArrayList<LeaderBean>();
		List<QuestMasterBean> questList = new ArrayList<QuestMasterBean>();

		try {
			leaderList = leaderService.getLeaderList(leaderList, null);
			if (leaderList.size() > 0) {
				for (LeaderBean leader : leaderList) {
					questList = leader.getQuestList();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("questList size=" + questList.size());
		assertTrue(questList.size() > 0);
	}

}
