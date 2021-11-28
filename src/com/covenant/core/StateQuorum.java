package com.covenant.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.covenant.Pojo.User;
import com.covenant.Utils.DataBase;
import com.covenant.Utils.DataQueries;

public class StateQuorum extends State{

	PanelQuorum main;
	float counter=0f;
	
	
	public StateQuorum(PanelQuorum main) {
		this.main = main;
		
	}
	@Override
	public void handle(Object code) {
		String code_ = (String)code;
		String opt = code_.substring(code_.length()-1, code_.length());
		String ref = code_.substring(0, code_.length()-1);
		
		if(main.containsUserByRef(ref))
			return;
		
		
		User user = DataQueries.getUserByRef(ref+"");
		try {
			if(user !=null) {
				counter+=main.addUser(user);;
			}
			main.drawQuorum(counter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
