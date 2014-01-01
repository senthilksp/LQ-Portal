package com.cti.lq.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.Constants.QueryContants;
import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.dao.LQLeaderDAO;
import com.cti.lq.persistence.DBConnectionFactory;
import com.cti.lq.util.LQPortalUserServiceUtil;

import java.sql.PreparedStatement;

public class LQLeaderDAOImpl implements LQLeaderDAO {
	final Log LOG = LogFactory.getLog(LQLeaderDAO.class);

	@Override
	public List<LeaderBean> getLeaderList(List<LeaderBean> leaderlist,
			RenderRequest renderRequest) {

		LOG.info("Entering into LeaderDAO");

		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

		ResultSet rs1 = null;
		ResultSet rs2 = null;

		StringBuffer leaderQuery = new StringBuffer(QueryContants.getAllLeaders);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps1 = con.prepareStatement(leaderQuery.toString());
			rs1 = ps1.executeQuery();
			leaderlist = new ArrayList<LeaderBean>();
			while (rs1.next()) {
				LeaderBean lb = new LeaderBean();
				lb.setUserid(rs1.getInt("userid"));
				lb.setFirstname(rs1.getString("firstname"));
				lb.setLastname(rs1.getString("lastname"));
				lb.setEmailAddress(rs1.getString("emailaddress"));
				lb.setPrimaryPhone(rs1.getString("primary_phone"));
				lb.setFirstname(rs1.getString("firstname"));
				lb.setBusinessName(rs1.getString("business_name"));
				lb.setCountry(rs1.getString("country"));
				lb.setCity(rs1.getString("city"));
				lb.setPhotoURL(rs1.getString("photo"));
				lb.setBioStatement(rs1.getString("bio_statement"));
				lb.setWebsite(rs1.getString("website"));
				lb.setFacultyRole(rs1.getString("faculty_role"));

				StringBuffer questQuery = new StringBuffer(
						QueryContants.getQuestInfoByleader);
				List<QuestMasterBean> questList = new ArrayList<QuestMasterBean>();
				ps2 = con.prepareStatement(questQuery.toString());
				ps2.setInt(1, lb.getUserid());
				rs2 = ps2.executeQuery();

				while (rs2.next()) {
					QuestMasterBean qb = new QuestMasterBean();
					qb.setUserId(rs2.getInt("userid"));
					qb.setQuestTitle(rs2.getString("quest_title"));
					qb.setQuestDefinition(rs2.getString("definition"));
					qb.setQuestTitle(rs2.getString("quest_title"));
					qb.setAccessMode(rs2.getBoolean("access_mode"));
					qb.setQuestId(rs2.getInt("quest_id"));
					questList.add(qb);
				}

				if (questList.size() > 0) {
					lb.setQuestList(questList);
				}
				leaderlist.add(lb);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		LOG.info("Leaving from LeaderDAO");
		return leaderlist;
	}

	@Override
	public LeaderBean getLeaderDetails(RenderRequest renderRequest,
			LeaderBean leaderBean) {

		LOG.info("Entering into getleaderDetails");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LeaderBean lb = new LeaderBean();

		StringBuffer query = new StringBuffer(QueryContants.getLeaderInfo);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps = con.prepareStatement(query.toString());
			if (leaderBean.getUserid() > 0) {
				ps.setInt(1, leaderBean.getUserid());
			} else {
				ps.setInt(1, LQPortalUserServiceUtil.getUserId(renderRequest));
			}

			rs = ps.executeQuery();

			while (rs.next()) {
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
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lb;

	}

	@Override
	public void saveLeaderDetails(LeaderBean leaderDetails) {
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

		StringBuffer updQuery1 = new StringBuffer(QueryContants.updateLeader_LQ);
		StringBuffer updQuery2 = new StringBuffer(
				QueryContants.updateLeader_liferay);

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
			ps1.executeUpdate();

			ps2 = con.prepareStatement(updQuery2.toString());
			ps2.setString(1, leaderDetails.getFirstname());
			ps2.setString(2, leaderDetails.getLastname());
			ps2.setString(3, leaderDetails.getEmailAddress());
			ps2.setInt(4, leaderDetails.getUserid());
			ps2.executeUpdate();

			con.commit();
			LOG.info("updation done");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public List<QuestViewBean> getQuestDetails(RenderRequest renderRequest,
			List<QuestViewBean> questList, int userId, int questId) {
		LOG.info("Entering into getleaderDetails");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		questList = new ArrayList<QuestViewBean>();

		StringBuffer query = new StringBuffer(QueryContants.getQuestInfo);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps = con.prepareStatement(query.toString());
			if (userId > 0) {
				ps.setInt(1, userId);
			} else {
				ps.setInt(1, LQPortalUserServiceUtil.getUserId(renderRequest));
			}
			if (questId > 0) {
				ps.setInt(2, questId);
			} else {
				ps.setInt(2, 0);
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				QuestViewBean qb = new QuestViewBean();
				qb.setQuest_id(rs.getInt("quest_id"));
				qb.setQuest_title(rs.getString("quest_title"));
				qb.setAccess_mode(rs.getBoolean("access_mode"));
				qb.setDefinition(rs.getString("definition"));
				qb.setUserId(rs.getInt("userid"));
				qb.setQuestType(rs.getString("quest_type"));
				qb.setQuestLocation(rs.getString("quest_location"));
				qb.setFirstName(rs.getString("firstname"));
				qb.setPhotoURL(rs.getString("photo"));
				questList.add(qb);
			}
		

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return questList;
	}

}
