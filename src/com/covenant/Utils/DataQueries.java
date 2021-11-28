package com.covenant.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.covenant.Pojo.Response;
import com.covenant.Pojo.User;

public class DataQueries {

	public static User getUserByRef(String ref_id) {
		ResultSet rs = DataBase.get().executeQuery("SELECT * FROM cvn_user WHERE ref_id = '"+ref_id+"'");
		if(rs==null)return null;
		try {
			if(rs.next()) {
				return  new User(rs.getInt("cvn_user_id"), rs.getString("ref_id"), rs.getString("name"), rs.getFloat("coefficient"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void savePropery(String value, String name) {
		String SQL="insert into properties(value,name) "
				+ "values ('"+value+"','"+name+"')";
		DataBase.get().execute(SQL);
	}
	
	public static String getPropery(String value) {
		String SQL="select name from properties where value= '"+value+"' ";
		ResultSet rs = DataBase.get().executeQuery(SQL);
		try {
			if(rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static float getCff(String type, int question_id) {
		String SQL= "select sum(u.coefficient) as total from cvn_user u " + 
				"join cvn_res_user ru on u.cvn_user_id = ru.cvn_user_id " +
				"join cvn_response r on ru.cvn_response_id = r.cvn_response_id " +
				"join cvn_question q on q.cvn_question_id = r.cvn_question_id " + 
				"where q.cvn_question_id = "+question_id+" " + 
				"and r.value= '"+type+"'";
		ResultSet rs_a = DataBase.get().executeQuery(SQL);
		float total=0;
		try {
			if(rs_a.next()) {
				total= rs_a.getFloat("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total/100f;
		
	}
	
	public static float getCff(int question_id) {
		String SQL= "select sum(u.coefficient) as total from cvn_user u " + 
				"join cvn_res_user ru on u.cvn_user_id = ru.cvn_user_id " +
				"join cvn_response r on ru.cvn_response_id = r.cvn_response_id " +
				"join cvn_question q on q.cvn_question_id = r.cvn_question_id " + 
				"where q.cvn_question_id = "+question_id+" ";
		ResultSet rs_a = DataBase.get().executeQuery(SQL);
		float total=0;
		try {
			if(rs_a.next()) {
				total= rs_a.getFloat("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total/100f;
		
	}
	
	public static String getMissed(int question_id) {
		String SQL= "select * from cvn_user where cvn_user_id in \r\n"
				+ " (select u.cvn_user_id from cvn_user u join presence p on p.cvn_user_id = u.cvn_user_id) \r\n"
				+ " and cvn_user_id not in \r\n"
				+ " (select u.cvn_user_id from cvn_user u join cvn_res_user ru on u.cvn_user_id = ru.cvn_user_id join cvn_response r on ru.cvn_response_id = r.cvn_response_id join cvn_question q on q.cvn_question_id = r.cvn_question_id  where q.cvn_question_id = "+question_id+") ";
		ResultSet rs_a = DataBase.get().executeQuery(SQL);
		String users="";
		try {
			if(rs_a.next()) {
				users+= rs_a.getString("ref_id")+",";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "("+users+")";
		
	}
	
	
	
	public static List<Response> getResponseByQuestionID(int question_id) {
		List<Response> res = new ArrayList<Response>();
		String SQL= "select * from cvn_response where cvn_question_id = "+question_id+" ";
				
		ResultSet rs = DataBase.get().executeQuery(SQL);
		float total=0;
		try {
			while(rs.next()) {
				res.add(new Response(rs.getInt("cvn_response_id"), rs.getString("value"), rs.getString("name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}

	public static void addPresence(User user, String value) {
		String SQL =  "insert into presence (cvn_user_id,value) values('"+user.getID()+"','"+value+"')";
		DataBase.get().execute(SQL);
	}
}
