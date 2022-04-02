package com.covenant.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.covenant.Pojo.User;
import com.covenant.Utils.DataBase;
import com.covenant.Utils.DataQueries;

public class StateQuorum extends State{

	MainFramePanelQuorum main;
	
	
	
	public StateQuorum(MainFramePanelQuorum main) {
		this.main = main;
		
	}
	@Override
	public void handle(Object code) {
		String code_ = (String)code;
		String opt = code_.substring(code_.length()-1, code_.length());
		String ref = code_.substring(0, code_.length()-1);
		

		if(main.containsUserByRef(ref)) {
			main.drawQuorum(main.users.stream().reduce(0f, (acc, elm) -> acc+elm.getCff(), Float::sum));
			return;	
		}
		
		User user = DataQueries.getUserByRef(ref+"");
		try {
			if(user !=null) {
				main.addUser(user);;
			}
			main.drawQuorum(main.users.stream().reduce(0f, (acc, elm) -> acc+elm.getCff(), Float::sum));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
