/**
 * 
 */
package test.com.cti.lq;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
import com.cti.lq.service.LQQuestService;
import com.cti.lq.service.impl.LQQuestServiceImpl;
import com.cti.lq.util.LQPortalUtil;
import com.cti.lq.util.LQUnitTestDataSourceSetup;

/**
 * @author senthil
 * 
 */
public class QuestServiceTest {

	java.util.ResourceBundle rb = ResourceBundle
			.getBundle("test.com.cti.lq.UnitTest");

	int userId = Integer.valueOf(rb.getString("add_quest_master_userId"));
	int questId = 0;

	@BeforeClass
	public static void setUpDataSource() throws Exception {
		if (LQUnitTestDataSourceSetup.setUpDataSource() != null) {
			LQUnitTestDataSourceSetup.setUpDataSource();
		}
		;
	}

	// Used in AddQuest.java. For Adding Quest Details in Quest Master table and
	// Quest Transaction Table
	@Test
	public void testSaveQuestDetails() {

		// Save quest Master
		QuestMasterBean questmaster = new QuestMasterBean();
		QuestTransactionBean transBean = new QuestTransactionBean();
		List<QuestTransactionBean> qTransList = new ArrayList<QuestTransactionBean>();

		questmaster.setUserId(userId);
		questmaster.setAccessMode(true);
		questmaster.setQuestDefinition(rb
				.getString("add_quest_master_quest_definition"));
		questmaster.setQuestTitle(rb.getString("add_quest_master_quest_name"));

		// Save quest Transaction.

		String imageFileName = rb.getString("add_quest_image_file_name");
		String audioFileName = rb.getString("add_quest_audio_file_name");
		String videoFileName = rb.getString("add_quest_video_file_name");

		if (!(imageFileName == null || "".equals(imageFileName))) {
			LQPortalUtil.uploadFilesForTesting(imageFileName);

			transBean.setQuestType(LQPortalConstants.IMAGE_TYPE);
			transBean.setQuestLocation(getLocation() + "\\" + imageFileName);
			qTransList.add(transBean);

		}
		if (!(audioFileName == null || "".equals(audioFileName))) {
			LQPortalUtil.uploadFilesForTesting(audioFileName);

			transBean = new QuestTransactionBean();
			transBean.setQuestType(LQPortalConstants.AUDIO_TYPE);
			transBean.setQuestLocation(getLocation() + "\\" + audioFileName);
			qTransList.add(transBean);

		}
		if (!(videoFileName == null || "".equals(videoFileName))) {
			LQPortalUtil.uploadFilesForTesting(videoFileName);

			transBean = new QuestTransactionBean();
			transBean.setQuestType(LQPortalConstants.VIDEO_TYPE);
			transBean.setQuestLocation(getLocation() + "\\" + videoFileName);
			qTransList.add(transBean);
		}

		try {
			LQQuestService questService = new LQQuestServiceImpl();
			Boolean isSaved = questService.saveQuestDetails(questmaster,
					qTransList);
			assertTrue(isSaved);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Used in EditQuest.java. Leader can Edit the Quest Master.
	@Test
	public void testupdateQuestMaster() {
		LQQuestService questService = new LQQuestServiceImpl();
		questId = findQuestIdForUpdate();

		QuestMasterBean questMaster = new QuestMasterBean();
		questMaster.setQuestId(questId);
		questMaster.setUserId(userId);
		questMaster.setQuestDefinition(rb
				.getString("edit_quest_master_quest_definition"));
		questMaster.setAccessMode(Boolean.valueOf(rb
				.getString("edit_quest_master_quest_access_mode")));
		questMaster.setQuestTitle(rb.getString("edit_quest_master_quest_name"));

		try {
			questService.updateQuestMaster(questMaster);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Used in EditQuest.java. Leader can Edit the Quest Transaction.

	@Test
	public void testUpdateQuestTransaction() {
		String imageFileName = rb.getString("edit_quest_image_file_name");

		LQPortalUtil.uploadFilesForTesting(imageFileName);

		QuestTransactionBean transBean = new QuestTransactionBean();
		transBean.setId(Integer.valueOf(rb.getString("edit_quest_trans_id")));
		transBean.setQuestLocation(getLocation() + "\\" + imageFileName);

		try {
			LQQuestService questService = new LQQuestServiceImpl();
			Boolean transSaved = questService.updateQuestTransaction(transBean);
			assertTrue(transSaved);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Used in EditQuest.java. Leader can Delete the Entire quest.
	@Test
	public void testDeleteQuestMaster() {
		try {

			LQQuestService questService = new LQQuestServiceImpl();
			questId = findQuestIdForDelete();
			boolean deleted = questService.deleteQuestMaster(questId);
			assertTrue(deleted);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int findQuestIdForDelete() {
		QuestMasterBean questmaster = new QuestMasterBean();
		questmaster.setUserId(userId);
		questmaster.setQuestDefinition(rb
				.getString("edit_quest_master_quest_definition"));
		questmaster.setQuestTitle(rb.getString("edit_quest_master_quest_name"));

		try {
			LQQuestService questService = new LQQuestServiceImpl();
			questId = questService.findQuestId(questmaster);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questId;
	}

	private int findQuestIdForUpdate() {
		QuestMasterBean questmaster = new QuestMasterBean();
		questmaster.setUserId(userId);
		questmaster.setQuestDefinition(rb
				.getString("add_quest_master_quest_definition"));
		questmaster.setQuestTitle(rb.getString("add_quest_master_quest_name"));

		try {

			LQQuestService questService = new LQQuestServiceImpl();
			questId = questService.findQuestId(questmaster);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questId;
	}
	
	private String getLocation() {
		return System.getenv("LQ_HOME") + "//lqfiles//";
		
	}

}
