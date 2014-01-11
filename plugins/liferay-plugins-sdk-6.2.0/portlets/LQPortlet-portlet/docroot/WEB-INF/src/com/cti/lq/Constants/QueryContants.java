/**
 * 
 */
package com.cti.lq.Constants;

/**
 * @author senthil
 * 
 */
public class QueryContants {

	public static final String getAllLeaders = "select a.userid,b.firstname,b.lastname,b.emailaddress,a.business_name,a.city,a.country,a.photo,"
			+ "a.bio_statement,a.primary_phone,a.website,a.faculty_role from user_lq a, user_ b where a.userid = b.userid;";

	public static final String getLeaderInfo = "select a.userid,b.firstname,b.lastname,b.emailaddress,a.business_name,a.city,a.country,a.photo,"
			+ "a.bio_statement,a.primary_phone,a.website,a.faculty_role from user_lq a, user_ b where a.userid = b.userid and a.userid = ?;";

	public static final String updateLeader_LQ = "update user_lq set faculty_role = ?, primary_phone = ?, website = ?,"
			+ " business_name=?, city=?, country=?, photo = ?, bio_statement=? where userid=?";
	
	public static final String updateLeader_lr ="update user_ set firstname=?, lastname=?, emailaddress=? where userid=?";
	
	public static final String getQuestInfoByleader = "select * from quest_master_ where userid=? order by quest_id";

	public static String getQuestInfo = "select  a.quest_id, b.id,a.quest_title,a.access_mode, a.definition,a.userid,c.firstname,d.photo, b.quest_type, b.quest_location "
			+ "from quest_master_ a, quest_transaction_ b, user_ c, user_lq d where "
			+ "a.quest_id = b.quest_id and a.userid = c.userid and c.userid = d.userid and a.userid=? and a.quest_id=? order by quest_id, quest_type" ;

	public static final String insertLeader_LQ = "insert into user_lq(userid,roleid,faculty_role,primary_phone,website,business_name,"
			+ "city,country,photo,bio_statement) values(?,?,?,?,?,?,?,?,?,?) ";
	
	public static final String insertLeader_LR = "insert into user_ (firstname,lastname,companyid,createdate,modifieddate,defaultuser,screenname,emailaddress,"
												+ "languageid,timezoneid,greeting,jobtitle,failedloginattempts,lockout,userid) "
												+ "values(?,?,10157,?,?,false,?,?,'en_US','UTC','WELCOME!','LEADER_ADMIN',0,false,?)";
	
	public static final String selectRole = "select roleid from role_ where name = 'LEADER'";
	
	public static final String userIdQuery = "select max(userid) from user_";
	
	public static final String updateAccessMode = "update quest_master_ set access_mode = ? where userid=? and quest_id=?";
	
	public static final String insertQuestMaster = "insert into quest_master_ (quest_title,access_mode,definition,userid) values(?,?,?,?)";
	public static final String insertQuestTrans  = "Insert into quest_transaction_ (quest_id,quest_type,quest_location) values (?,?,?)";
	public static final String getQuestId = "select quest_id from quest_master_ where quest_title=?";
	
	public static Object getMaxQuestId = "select max(quest_id) from quest_master_ where userid=?";
	
	public static final String updateQuestMaster = "update quest_master_  set quest_title=?, definition=? where quest_id=?";
	public static final String updateQuestTran   = "update quest_transaction_  set quest_location=? where id=?"; 
	public static final String deleteQuestTran   = "delete from quest_transaction_ where id=?";
	

}
