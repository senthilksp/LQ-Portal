package test.com.cti.lq;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

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
		if (LQUnitTestDataSourceSetup.setUpDataSource() != null) {
			LQUnitTestDataSourceSetup.setUpDataSource();
		}
		;
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
					if (leader.getQuestList() != null
							&& leader.getQuestList().size() > 0) {
						questList.addAll(leader.getQuestList());
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(questList.size() > 0);
	}

}
