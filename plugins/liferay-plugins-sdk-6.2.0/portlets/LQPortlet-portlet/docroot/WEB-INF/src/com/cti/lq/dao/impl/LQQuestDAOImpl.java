package com.cti.lq.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.Constants.QueryContants;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.dao.LQQuestDAO;
import com.cti.lq.persistence.DBConnectionFactory;

public class LQQuestDAOImpl implements LQQuestDAO {
	final Log LOG = LogFactory.getLog(LQQuestDAOImpl.class);

	@Override
	public List<QuestMasterBean> getQuestMasterList(int userId)
			throws SQLException {
		LOG.info("Entering into getQuestMasterList");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<QuestMasterBean> qustMasterList = new ArrayList<QuestMasterBean>();

		StringBuffer query = new StringBuffer(
				QueryContants.getQuestInfoByleader);

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps = con.prepareStatement(query.toString());
			ps.setInt(1, userId);

			rs = ps.executeQuery();
			while (rs.next()) {
				QuestMasterBean qb = new QuestMasterBean();
				qb.setUserId(rs.getInt("userid"));
				qb.setQuestTitle(rs.getString("quest_title"));
				qb.setQuestDefinition(rs.getString("definition"));
				qb.setAccessMode(rs.getBoolean("access_mode"));
				qb.setQuestId(rs.getInt("quest_id"));
				qustMasterList.add(qb);
			}
			ps.close();
			rs.close();

		} catch (Exception ex) {
			if (con != null)
				con.close();
			ex.printStackTrace();
		}

		finally {
			closeDBOperations(con, ps, rs);
		}

		return qustMasterList;
	}

	private void closeDBOperations(Connection con, PreparedStatement ps,
			ResultSet rs) throws SQLException {
		if (con != null)
			con.close();
		if (ps != null)
			ps.close();
		if (rs != null)
			rs.close();

	}

	@Override
	public Boolean saveQuestDetails(QuestMasterBean questmaster,
			List<QuestTransactionBean> qTransList) throws SQLException {
		LOG.info("saveQuestDetails");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int questId = 0;
		int save1 = 0;
		int save2 = 0;

		StringBuffer insQuery = new StringBuffer(
				QueryContants.insertQuestMaster);
		LOG.info("insQuery" + insQuery);
		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			con.setAutoCommit(false);
			
			ps = con.prepareStatement(insQuery.toString());
			ps.setString(1, questmaster.getQuestTitle());
			ps.setBoolean(2, questmaster.getAccessMode());
			ps.setString(3, questmaster.getQuestDefinition());
			ps.setInt(4, questmaster.getUserId());
			save1 = ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement(QueryContants.getQuestId);
			ps.setString(1, questmaster.getQuestTitle());

			rs = ps.executeQuery();
			while (rs.next()) {
				questId = rs.getInt(1);
			}
			ps.close();

			if (qTransList.size() > 0) {
				for (QuestTransactionBean qt : qTransList) {
					ps = con.prepareStatement(QueryContants.insertQuestTrans);
					ps.setInt(1, questId);
					ps.setString(2, qt.getQuestType());
					ps.setString(3, qt.getQuestLocation());
					save2 = ps.executeUpdate();
				}
			}
			
			con.commit();
		} catch (Exception ex) {
			if (con != null)
				con.close();
			ex.printStackTrace();
		} finally {
			closeDBOperations(con, ps, rs);
		}

		if (save1 > 0 && save2 > 0) {
			return true;
		} else {
			return false;
		}
	}

	
	@Override
	public Boolean saveQuestTransactions(List<QuestTransactionBean> qTransList,
			int userId,int questId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int save1 = 0;

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			if (qTransList.size() > 0) {
				for (QuestTransactionBean qt : qTransList) {
					ps = con.prepareStatement(QueryContants.insertQuestTrans);
					ps.setInt(1, questId);
					ps.setString(2, qt.getQuestType());
					ps.setString(3, qt.getQuestLocation());
					save1 = ps.executeUpdate();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			if (con != null)
				con.close();

		} finally {
			closeDBOperations(con, ps, rs);
		}

		if (save1 > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Boolean updateQuestTransaction(QuestTransactionBean transBean)
			throws SQLException {
		// TODO Auto-generated method stub
		
		LOG.info("Enterint into updateQuestTransaction");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int save1 = 0;
		// set quest_id=?,quest_type=?quest_location=? where id=?";

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps = con.prepareStatement(QueryContants.updateQuestTran.toString());
			ps.setString(1, transBean.getQuestLocation());
			ps.setInt(2, transBean.getId());
			save1 = ps.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
			if (con != null)
				con.close();

		} finally {
			closeDBOperations(con, ps, rs);
		}

		if (save1 > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Boolean updateQuestMaster(QuestMasterBean questMaster)
			throws SQLException {
		LOG.info("Entering into updateQuestMaster");
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int save1 = 0;
		// set quest_title=?, definition=? where quest_id=?";

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps = con.prepareStatement(QueryContants.updateQuestMaster
					.toString());
			ps.setString(1, questMaster.getQuestTitle());
			ps.setString(2, questMaster.getQuestDefinition());
			ps.setInt(3, questMaster.getQuestId());
			save1 = ps.executeUpdate();

		} catch (Exception ex) {
			ex.printStackTrace();
			if (con != null)
				con.close();

		} finally {
			closeDBOperations(con, ps, rs);
		}

		if (save1 > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean deleteQuestTransaction(int id1) throws SQLException {
		LOG.info("Entering into deleteQuestTransaction");
		Connection con = null;
		PreparedStatement ps = null;
		int save1 = 0;
		// set quest_title=?, definition=? where quest_id=?";

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps = con.prepareStatement(QueryContants.deleteQuestTran.toString());
			ps.setInt(1, id1);
			save1 = ps.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if (con != null)
				con.close();

		} finally {
			closeDBOperations(con, ps, null);
		}

		if (save1 > 0) {
			return true;
		} else {
			return false;
		}
	}
}
