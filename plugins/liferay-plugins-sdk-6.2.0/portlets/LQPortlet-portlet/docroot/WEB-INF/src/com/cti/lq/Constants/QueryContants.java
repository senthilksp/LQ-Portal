/**
 * 
 */
package com.cti.lq.Constants;

/**
 * @author senthil
 * 
 */
public class QueryContants {

	//The userids added in the query is to filter the leader and leader admin users that are created for unit testing purpose.  
	//These were shown in the home page which is not necessary.  
	//Hence it is given in the query to filter them out.
	public static final String getAllLeaders = "select a.userid,b.firstname,b.lastname,b.emailaddress,a.business_name,a.city,a.country,a.photo,"
			+ "a.bio_statement,a.primary_phone,a.website,a.faculty_role from user_lq a, user_ b where a.userid = b.userid and b.userid not in (13103, 13113);";

	public static final String getLeaderInfo = "select a.userid,b.firstname,b.lastname,b.emailaddress,a.business_name,a.city,a.country,a.photo,"
			+ "a.bio_statement,a.primary_phone,a.website,a.faculty_role from user_lq a, user_ b where a.userid = b.userid and a.userid = ?;";

	public static final String updateLeader_LQ = "update user_lq set faculty_role = ?, primary_phone = ?, website = ?,"
			+ " business_name=?, city=?, country=?, photo = ?, bio_statement=? where userid=?";
	
	public static final String updateLeader_lr ="update user_ set firstname=?, lastname=? where userid=?";
	
	public static final String getQuestInfoByleader = "select * from quest_master_ where userid=? order by quest_id";

	public static String getQuestInfo = "select b.id, a.quest_id, a.quest_title,a.access_mode, a.definition,a.userid,c.firstname,d.photo, b.quest_type, b.quest_location "
			+ "from quest_master_ a, quest_transaction_ b, user_ c, user_lq d where "
			+ "a.quest_id = b.quest_id and a.userid = c.userid and c.userid = d.userid and a.userid=? and a.quest_id=? order by quest_id, quest_type" ;

	public static String getQuestComment = "select b.id, b.quest_trans_id, b.comment, b.added_by, b.added_on "
			+ "from quest_transaction_ a, quest_comment b where "
			+ "a.id = b.quest_trans_id and b.quest_trans_id=? order by quest_id" ;
	
	public static final String insertLeader_LQ = "insert into user_lq(userid,roleid,faculty_role,primary_phone,website,business_name,"
			+ "city,country,photo,bio_statement) values(?,?,?,?,?,?,?,?,?,?) ";
	
	public static final String insertLeader_LR = "insert into user_ (firstname,lastname,companyid,createdate,modifieddate,defaultuser,screenname,emailaddress,"
												+ "languageid,timezoneid,greeting,jobtitle,failedloginattempts,lockout,userid) "
												+ "values(?,?,10157,?,?,false,?,?,'en_US','UTC','WELCOME!','LEADER',0,false,?)";
	
	public static final String selectRole = "select roleid from role_ where name = 'LEADER'";
	
	public static final String userIdQuery = "select max(userid) from user_";
	
	public static final String updateAccessMode = "update quest_master_ set access_mode = ? where userid=? and quest_id=?";
	
	public static final String insertQuestMaster = "insert into quest_master_ (quest_title,access_mode,definition,userid) values(?,?,?,?)";
	public static final String insertQuestTrans  = "Insert into quest_transaction_ (quest_id,quest_type,quest_location) values (?,?,?)";
	public static final String insertQuestComment  = "Insert into quest_comment (quest_trans_id,comment,added_by,added_on) values (?,?,?,?)";
	public static final String getQuestId = "select quest_id from quest_master_ where quest_title=?";
	
	public static Object getMaxQuestId = "select max(quest_id) from quest_master_ where userid=?";

	public static String resetPassword = "update user_ set password_ = ? where emailaddress = ?";
	
	public static final String updateQuestMaster 	  = "update quest_master_  set quest_title=?, definition=?,access_mode=? where quest_id=?";
	public static final String updateQuestTran   	  = "update quest_transaction_  set quest_location=? where id=?"; 
	public static final String deleteQuestTran   	  = "delete from quest_transaction_ where id=?";
	public static final String deleteQuestMaster 	  = "delete from quest_master_ where quest_id=?";
	public static final String deleteQuestComment 	  = "delete from quest_comment where quest_trans_id=?";
	public static final String deleteQuestTransaction = "delete from quest_transaction_ where quest_id=?";
	
	public static String deleteLeader1 = "delete from user_lq where userid=?";
	public static String deleteLeader2 = "delete from user_   where userid=?";
	
	public static String findQuestId = "select quest_id from quest_master_ where quest_title=? and definition=? and userid=?";
	
	public static final String searchQuery = "select a.userid,b.firstname,b.lastname,b.emailaddress,a.business_name,a.city,a.country,a.photo,"
			+ "a.bio_statement,a.primary_phone,a.website,a.faculty_role from user_lq a, user_ b where a.userid = b.userid and b.userid not in (13103, 13113)" +
			"and a.userid in (select userid from user_ where LOWER(firstname) like ? or LOWER(lastname) like ? or LOWER(emailaddress) like ? " +
			"union " +
			" select userid from user_lq where LOWER(faculty_role) like ? or LOWER(primary_phone) like ? or LOWER(website) like ? or " +
			"LOWER(business_name) like ? or LOWER(city) like ? or LOWER(country) like ? or LOWER(photo) like ? or LOWER(bio_statement) like ? " +
			"union " +
			" select userid from quest_master_ where LOWER(quest_title) like ? or LOWER(definition) like ? or " +
			"quest_id in(select quest_id from quest_transaction_ where LOWER(quest_location) like ?))";


}
