package com.cti.lq.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.Constants.QueryContants;
import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.QuestCommentBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.dao.LQLeaderDAO;
import com.cti.lq.persistence.DBConnectionFactory;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LiferayAddUserUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public class LQLeaderDAOImpl implements LQLeaderDAO {
	final Log LOG = LogFactory.getLog(LQLeaderDAO.class);

	@Override
	public List<LeaderBean> getLeaderList(List<LeaderBean> leaderlist, RenderRequest renderRequest) throws SQLException {

		LOG.info("Entering into LQLeaderDAOImpl->LQLeaderDAOImpl->getLeaderList");
		int signinUserId = renderRequest != null ? LQPortalUserServiceUtil.getUserId(renderRequest) : 0;

		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

		ResultSet rs1 = null;
		ResultSet rs2 = null;

		Boolean accessMode = false;

		StringBuffer leaderQuery = new StringBuffer(QueryContants.getAllLeaders);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps1 = con.prepareStatement(leaderQuery.toString());
			rs1 = ps1.executeQuery();
			leaderlist = new ArrayList<LeaderBean>();
			while (rs1.next()) {
				LeaderBean lb = new LeaderBean();
				lb = settingLeaderBean(rs1, lb);

				StringBuffer questQuery = new StringBuffer(QueryContants.getQuestInfoByleader);
				List<QuestMasterBean> questList = new ArrayList<QuestMasterBean>();
				ps2 = con.prepareStatement(questQuery.toString());
				ps2.setInt(1, lb.getUserid());
				rs2 = ps2.executeQuery();
				String role = renderRequest == null ? null : LQPortalUserServiceUtil.getRoleName(renderRequest);

				while (rs2.next()) {
					QuestMasterBean qb = new QuestMasterBean();
					qb.setUserId(rs2.getInt("userid"));
					qb.setQuestTitle(rs2.getString("quest_title"));
					qb.setQuestDefinition(rs2.getString("definition"));
					qb.setAccessMode(rs2.getBoolean("access_mode"));
					qb.setQuestId(rs2.getInt("quest_id"));

					if (qb.getAccessMode() || (qb.getAccessMode() == false && qb.getUserId() == signinUserId)) {
						accessMode = true;
					}

					if (role != null && role.equalsIgnoreCase(LQPortalConstants.LQ_LEADER_ADMIN)) {
						accessMode = true;
					}

					if (accessMode) {
						questList.add(qb);
						accessMode = false;
					}
				}

				if (questList.size() > 0) {
					lb.setQuestList(questList);
				}
				leaderlist.add(lb);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {
			closeDBOperations(con, ps1, rs1);
			closeDBOperations(con, ps2, rs2);
		}

		LOG.info("Leaving from LeaderDAO");
		return leaderlist;
	}

	@SuppressWarnings("resource")
	@Override
	public LeaderBean getLeaderDetails(RenderRequest renderRequest, LeaderBean leaderBean) throws SQLException {

		LOG.info("Entering into LQLeaderDAOImpl->getleaderDetails");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LeaderBean lb = new LeaderBean();
		List<QuestMasterBean> questList = new ArrayList<QuestMasterBean>();

		StringBuffer query = new StringBuffer(QueryContants.getLeaderInfo);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps = con.prepareStatement(query.toString());
			if (renderRequest != null && leaderBean.getUserid() == 0) {
				leaderBean.setUserid(LQPortalUserServiceUtil.getUserId(renderRequest));
			}
			ps.setInt(1, leaderBean.getUserid());

			rs = ps.executeQuery();

			while (rs.next()) {
				lb = settingLeaderBean(rs, lb);
			}

			rs.close();
			ps = con.prepareStatement(QueryContants.getQuestInfoByleader);
			ps.setInt(1, leaderBean.getUserid());
			rs = ps.executeQuery();
			while (rs.next()) {
				QuestMasterBean qb = new QuestMasterBean();
				qb.setUserId(rs.getInt("userid"));
				qb.setQuestTitle(rs.getString("quest_title"));
				qb.setQuestDefinition(rs.getString("definition"));
				qb.setAccessMode(rs.getBoolean("access_mode"));
				qb.setQuestId(rs.getInt("quest_id"));
				questList.add(qb);
			}
			lb.setQuestList(questList);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeDBOperations(con, ps, rs);
		}

		return lb;

	}

	private LeaderBean settingLeaderBean(ResultSet rs, LeaderBean lb) {
		LOG.info("Entering into LQLeaderDAOImpl->settingLeaderBean");
		try {
			lb.setUserid(rs.getInt("userid"));
			lb.setFirstname(rs.getString("firstname"));
			lb.setLastname(rs.getString("lastname"));
			lb.setEmailAddress(rs.getString("emailaddress"));
			lb.setPrimaryPhone(rs.getString("primary_phone"));
			lb.setFirstname(rs.getString("firstname"));
			lb.setBusinessName(rs.getString("business_name"));
			lb.setCountry(rs.getString("country"));
			lb.setCity(rs.getString("city"));
			lb.setPhotoURL(rs.getString("photo"));
			lb.setBioStatement(rs.getString("bio_statement"));
			lb.setWebsite(rs.getString("website"));
			lb.setFacultyRole(rs.getString("faculty_role"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lb;

	}

	// Update Leader details.
	@Override
	public Boolean saveLeaderDetails(LeaderBean leaderDetails) throws SQLException {
		LOG.info("Entering into LQLeaderDAOImpl->saveLeaderDetails");
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		Boolean saveFlag = false;
		int save1 = 0;
		int save2 = 0;
		int save3 = 0;

		StringBuffer updQuery1 = new StringBuffer(QueryContants.updateLeader_LQ);
		StringBuffer updQuery2 = new StringBuffer(QueryContants.updateLeader_lr);
		StringBuffer updQuery3 = new StringBuffer(QueryContants.updateAccessMode);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			con.setAutoCommit(false);

			ps1 = con.prepareStatement(updQuery1.toString());
			ps1.setString(1, leaderDetails.getFacultyRole());
			ps1.setString(2, leaderDetails.getPrimaryPhone());
			ps1.setString(3, leaderDetails.getWebsite());
			ps1.setString(4, leaderDetails.getBusinessName());
			ps1.setString(5, leaderDetails.getCity());
			ps1.setString(6, leaderDetails.getCountry());
			ps1.setString(7, leaderDetails.getPhotoURL());
			ps1.setString(8, leaderDetails.getBioStatement());
			ps1.setInt(9, leaderDetails.getUserid());
			save1 = ps1.executeUpdate();

			ps2 = con.prepareStatement(updQuery2.toString());
			ps2.setString(1, leaderDetails.getFirstname());
			ps2.setString(2, leaderDetails.getLastname());
			ps2.setInt(3, leaderDetails.getUserid());
			save2 = ps2.executeUpdate();

			ps3 = con.prepareStatement(updQuery3.toString());
			List<QuestMasterBean> questList = leaderDetails.getQuestList();

			if (questList != null && questList.size() > 0) {
				for (QuestMasterBean qb : questList) {
					ps3.setBoolean(1, qb.getAccessMode());
					ps3.setInt(2, qb.getUserId());
					ps3.setInt(3, qb.getQuestId());
					save3 = ps3.executeUpdate();
				}
			} else {
				save3 = 1;
			}

			con.commit();
			LOG.info("updation done");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeDBOperations(con, ps1, null);
			closeDBOperations(con, ps2, null);
			closeDBOperations(con, ps3, null);

		}

		saveFlag = (save1 > 0 && save2 > 0 && save3 > 0) ? true : false;
		return saveFlag;

	}

	// Add Leader Details
	@Override
	public Boolean addLeaderDetails(LeaderBean leaderDetails,ActionRequest actionRequest) throws SQLException {
		LOG.info("Entering into LQLeaderDAOImpl->addLeaderDetails");
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		Boolean saveSuccess = false;
		long userId = 0;
		int ins2 = 0;
		
		StringBuffer insertQuery2 = new StringBuffer(QueryContants.insertLeader_LQ);
		StringBuffer roleQry = new StringBuffer(QueryContants.selectRole);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			con.setAutoCommit(false);

			ps1 = con.prepareStatement(roleQry.toString());
			rs = ps1.executeQuery();
			while (rs.next()) {
				leaderDetails.setRoleid(rs.getInt(1));
			}
			rs.close();

			// if userId=0 then, this method is called by UserLayer. Otherwise
			// it is called by unit test

			userId = LiferayAddUserUtil.insertLiferayUser(actionRequest, leaderDetails);
			
			if (userId != 0) { 
				LOG.info("Inserted into Liferay User table");
	
				ps3 = con.prepareStatement(insertQuery2.toString());
	
				ps3.setLong(1, userId);
				ps3.setInt(2, leaderDetails.getRoleid());
				ps3.setString(3, leaderDetails.getFacultyRole());
				ps3.setString(4, leaderDetails.getPrimaryPhone());
				ps3.setString(5, leaderDetails.getWebsite());
				ps3.setString(6, leaderDetails.getBusinessName());
				ps3.setString(7, leaderDetails.getCity());
				ps3.setString(8, leaderDetails.getCountry());
				ps3.setString(9, leaderDetails.getPhotoURL());
				ps3.setString(10, leaderDetails.getBioStatement());
				ins2 = ps3.executeUpdate();
	
				con.commit();
				
				if (ins2 > 0) {
					saveSuccess = true;
					LOG.info("Inserted into LQ table");
				}
			} else {
				saveSuccess = false;
				LOG.info("Not able insert into Liferay User table");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBOperations(con, ps1, rs);
			closeDBOperations(con, ps2, null);
		}

		return saveSuccess;

	}

	@Override
	public List<QuestViewBean> getQuestDetails(RenderRequest renderRequest, List<QuestViewBean> questList, int userId, int questId)	throws SQLException {
		LOG.info("Entering into LQLeaderDAOImpl->getQuestDetails");

		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		questList = new ArrayList<QuestViewBean>();

		StringBuffer questInfoQuery = new StringBuffer(QueryContants.getQuestInfo);
		StringBuffer questCommentQuery = new StringBuffer(QueryContants.getQuestComment);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps1 = con.prepareStatement(questInfoQuery.toString());
			if (userId > 0) {
				ps1.setInt(1, userId);
			} else if (renderRequest != null) {
				ps1.setInt(1, LQPortalUserServiceUtil.getUserId(renderRequest));
			}

			if (questId > 0) {
				ps1.setInt(2, questId);
			} else {
				ps1.setInt(2, 0);
			}

			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				QuestViewBean qb = new QuestViewBean();
				qb.setId(rs1.getInt("id"));
				qb.setQuest_id(rs1.getInt("quest_id"));
				qb.setQuest_title(rs1.getString("quest_title"));
				qb.setAccess_mode(rs1.getBoolean("access_mode"));
				qb.setDefinition(rs1.getString("definition"));
				qb.setUserId(rs1.getInt("userid"));
				qb.setQuestType(rs1.getString("quest_type"));
				qb.setQuestLocation(rs1.getString("quest_location"));

				String showLocation = qb.getQuestLocation().substring(qb.getQuestLocation().lastIndexOf("/") + 1, qb.getQuestLocation().length());
				qb.setQlocationForDisplay(showLocation);

				qb.setFirstName(rs1.getString("firstname"));
				qb.setPhotoURL(rs1.getString("photo"));
				qb.setQuestTransId(rs1.getInt("id"));
				
				ps2 = con.prepareStatement(questCommentQuery.toString());
				ps2.setInt(1, rs1.getInt("id"));  // quest_transaction_ id
				rs2 = ps2.executeQuery();
				List<QuestCommentBean> commentList = new ArrayList<QuestCommentBean>();
				while (rs2.next()) {
					QuestCommentBean questCommentBean = new QuestCommentBean();
					questCommentBean.setId(rs2.getInt("id"));
					questCommentBean.setQuestTransId(rs2.getInt("quest_trans_id"));
					questCommentBean.setComment(rs2.getString("comment"));
					questCommentBean.setAddedBy(rs2.getString("added_by"));
					questCommentBean.setAddedOn(rs2.getString("added_on"));
					
					commentList.add(questCommentBean);
				}
				qb.setCommentList(commentList);
				questList.add(qb);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeDBOperations(con, ps1, rs1);
			closeDBOperations(con, ps2, rs2);
		}
		return questList;
	}

	@Override
	public List<QuestViewBean> getQuestMasterDetails(RenderRequest renderRequest, List<QuestViewBean> questList, int userId) throws SQLException {

		LOG.info("Entering into LQLeaderDAOImpl->getQuestMasterDetails");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		questList = new ArrayList<QuestViewBean>();
		List<QuestViewBean> questList_temp = new ArrayList<QuestViewBean>();

		StringBuffer query = new StringBuffer(QueryContants.getQuestInfoByleader);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps = con.prepareStatement(query.toString());
			ps.setInt(1, userId);

			rs = ps.executeQuery();
			while (rs.next()) {
				questList_temp = new ArrayList<QuestViewBean>();
				questList_temp = getQuestDetails(renderRequest, questList, userId, rs.getInt("quest_id"));
				questList.addAll(questList_temp);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeDBOperations(con, ps, rs);
		}

		return questList;

	}

	@Override
	public Boolean deleteLeader(LeaderBean lb) throws SQLException {
		LOG.info("Entering into LQLeaderDAOImpl->deleteLeader");
		Connection con = null;
		PreparedStatement ps = null;
		int save1 = 0;

		StringBuffer query1 = new StringBuffer(QueryContants.deleteLeader1);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(query1.toString());
			ps.setInt(1, lb.getUserid());

			save1 = ps.executeUpdate();

			con.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeDBOperations(con, ps, null);
		}

		return save1 > 0 ? true : false;
	}

	private void closeDBOperations(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
		if (con != null)
			con.close();
		if (ps != null)
			ps.close();
		if (rs != null)
			rs.close();
	}
}
