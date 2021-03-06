package com.cti.lq.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.Constants.QueryContants;
import com.cti.lq.beans.QuestCommentBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
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

		StringBuffer query = new StringBuffer(QueryContants.getQuestInfoByleader);

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

	@Override
	public Boolean saveQuestDetails(QuestMasterBean questmaster,
			List<QuestTransactionBean> qTransList) throws SQLException {
		LOG.info("Entering into saveQuestDetails");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int quest_master_id = 0;
		int quest_trans_id = 0;
		int quest_comment_id = 0;

		StringBuffer insQuery = new StringBuffer(QueryContants.insertQuestMaster);
		LOG.info("insQuery" + insQuery);
		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(insQuery.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, questmaster.getQuestTitle());
			ps.setBoolean(2, questmaster.getAccessMode());
			ps.setString(3, questmaster.getQuestDefinition());
			ps.setInt(4, questmaster.getUserId());
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();  
		    rs.next();  
		    quest_master_id = rs.getInt(1);
		    
			LOG.info("quest_master_id = " + quest_master_id);
			ps.close();
			rs.close();

			if (qTransList.size() > 0) {
				for (QuestTransactionBean qt : qTransList) {
					
					ps = con.prepareStatement(QueryContants.insertQuestTrans, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, quest_master_id);
					ps.setString(2, qt.getQuestType());
					ps.setString(3, qt.getQuestLocation());
					ps.executeUpdate();
					
					rs = ps.getGeneratedKeys();  
				    rs.next();  
				    quest_trans_id = rs.getInt(1);
					
				    LOG.info("quest_trans_id = " + quest_trans_id);
					if (qt.getQuestCommentBean() != null && qt.getQuestCommentBean().size() > 0) {
						QuestCommentBean questCommentBean = qt.getQuestCommentBean().get(0);
						ps.close();
						rs.close();
						ps = con.prepareStatement(QueryContants.insertQuestComment, Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, quest_trans_id);
						ps.setString(2, questCommentBean.getComment());
						ps.setString(3,  questCommentBean.getAddedBy());
						ps.setString(4, questCommentBean.getAddedOn());
						ps.executeUpdate();
						
						rs = ps.getGeneratedKeys();  
					    rs.next();  
					    quest_comment_id = rs.getInt(1);
					    
						LOG.info("quest_comment_id = " + quest_comment_id);
					}
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

		if (quest_master_id > 0 && quest_trans_id > 0 && quest_comment_id > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean saveQuestTransactions(List<QuestTransactionBean> qTransList,
			int userId, int questId) throws SQLException {
		
		LOG.info("Entering into saveQuestTransactions");
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

		LOG.info("Entering into updateQuestTransaction");
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
			ps.setBoolean(3, questMaster.getAccessMode());
			ps.setInt(4, questMaster.getQuestId());
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
	public Boolean deleteQuestTransaction(int questTransId) throws SQLException {
		LOG.info("Entering into deleteQuestTransaction");
		Connection con = null;
		PreparedStatement ps = null;
		int save1 = 0;

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			
			con.setAutoCommit(false);
			
			ps = con.prepareStatement(QueryContants.deleteQuestComment.toString());
			ps.setInt(1, questTransId);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement(QueryContants.deleteQuestTran.toString());
			ps.setInt(1, questTransId);
			save1 = ps.executeUpdate();
			ps.close();
			
			con.commit();
		
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

	@Override
	public Boolean deleteQuestMaster(int questId) throws SQLException {
		LOG.info("Entering into deleteQuestMaster");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int save1 = 0;
		int save2 = 0;

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(QueryContants.deleteQuestTransaction.toString());
			ps.setInt(1, questId);
			save1 = ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement(QueryContants.deleteQuestMaster.toString());
			ps.setInt(1, questId);
			save2 = ps.executeUpdate();
			ps.close();

			con.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			if (con != null)
				con.close();

		} finally {
			closeDBOperations(con, ps, rs);
		}

		if (save1 > 0 && save2 > 0) {
			LOG.info("Deletion Done");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int findQuestId(QuestMasterBean questmaster) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int questId = 0;

		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(QueryContants.findQuestId.toString());
			ps.setString(1, questmaster.getQuestTitle());
			ps.setString(2, questmaster.getQuestDefinition());
			ps.setInt(3, questmaster.getUserId());

			rs = ps.executeQuery();
			while (rs.next()) {
				questId = rs.getInt(1);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			if (con != null)
				con.close();

		} finally {
			closeDBOperations(con, ps, rs);
		}

		return questId;

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
	public Boolean saveQuestComment(QuestCommentBean questCommentBean) throws SQLException {
		LOG.info("Entering into saveQuestComment");
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int save1 = 0;
		
		try {
			con = DBConnectionFactory.getPostgresDBConnection();
			ps = con.prepareStatement(QueryContants.insertQuestComment.toString());
			ps.setInt(1, questCommentBean.getQuestTransId());
			ps.setString(2, questCommentBean.getComment());
			ps.setString(3, questCommentBean.getAddedBy());
			ps.setString(4, questCommentBean.getAddedOn());
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
}
