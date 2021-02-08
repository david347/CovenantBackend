package com.covenant.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	
	public String database_path;
	public Connection conn;
	static DataBase db;
	
	public DataBase(String database_path){
		this.database_path =database_path;
		db =this;
	}
	
	public static void create(String database_path){
		db = new DataBase(database_path);
	}
	
	public static DataBase get(){
		return db;
	}
	
	public int execute(String SQL) {
		
		try 
		{
			Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage()+"\n"+SQL);
            return 0;
        }
	}
	
	public ResultSet executeQuery(String SQL) {
		try 
		{
			Connection conn = this.connect();
			Statement pstmt = conn.createStatement();
			ResultSet rs    = pstmt.executeQuery(SQL);
            return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage()+"\n "+SQL);
            return null;
        }
	}
	
	
	@SuppressWarnings("finally")
	public Connection connect() {
        if(this.conn!=null)
        	return conn;
        try {
            // db parameters
            String url = "jdbc:sqlite:"+database_path;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    return conn;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return null;
            }
            return null;
        }
        
    }
	
	
}
