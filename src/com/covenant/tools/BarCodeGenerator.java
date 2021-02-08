package com.covenant.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.covenant.Utils.DataBase;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
public class BarCodeGenerator {

	static String AS ="0";
	static String SI ="1";
	static String NO ="2";
	static String A ="3";
	static String B ="4";
	static String C ="5";
	static String D ="6";
	
	public static void generate(String path) {
		 String SQL= "select ref_id from cvn_user;";
	     DataBase db=DataBase.get();
	     try {
	     ResultSet rs= db.executeQuery(SQL);
	     
	     
			while(rs.next()) {
				 proccess( rs.getString("ref_id"),path);
				 
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void proccess(String code, String path)  {
		try {
			generateBarCode(code+AS, path);
			generateBarCode(code+SI, path);
			generateBarCode(code+NO, path);
			generateBarCode(code+A, path);
			generateBarCode(code+B, path);
			generateBarCode(code+C, path);
			generateBarCode(code+D, path);
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static String  generateBarCode(String code,String path)throws FileNotFoundException, IOException, WriterException {
		
		int width  = 300;
		int height = 100; 
		String imgFormat = "png";

		//BitMatrix bitMatrix = new UPCAWriter().encode(code, BarcodeFormat.CODE_128, width, height);
		BitMatrix bitMatrix = new Code128Writer().encode(code, BarcodeFormat.CODE_128, width, height);
		File basedir = new File(path);
		if(!basedir.exists())basedir.mkdir();
		MatrixToImageWriter.writeToStream(bitMatrix, imgFormat, new FileOutputStream(basedir+File.separator+code+".png"));
		return basedir+File.separator+code+".png";
	}
	/*
	public String fixLength(String text, int size) {
		//if()
			}
	*/
	
}
