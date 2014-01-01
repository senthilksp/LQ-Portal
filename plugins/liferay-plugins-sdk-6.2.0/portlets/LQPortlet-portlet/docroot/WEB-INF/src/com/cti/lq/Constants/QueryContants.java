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
	
	public static final String updateLeader_liferay ="update user_ set firstname=?, lastname=?, emailaddress=? where userid=?";
	
	public static final String getQuestInfoByleader = "select * from quest_master_ where userid=?";

	public static String getQuestInfo = "select a.quest_id, a.quest_title,a.access_mode, a.definition,a.userid,c.firstname,d.photo, b.quest_type, b.quest_location "
			+ "from quest_master_ a, quest_transaction_ b, user_ c, user_lq d where "
			+ "a.quest_id = b.quest_id and a.userid = c.userid and c.userid = d.userid and a.userid=? and a.quest_id=?" ;

}
