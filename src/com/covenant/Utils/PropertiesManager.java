package com.covenant.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * POJO class, loads the values from a .properties file
 * @author David.garay
 *
 */
public class PropertiesManager {

	private static PropertiesManager main;
	private String path;
	
	private Properties s_prop;
	private String filename;
	public PropertiesManager(String path, String fileName) throws IOException {
		this.path =path;
		this.filename =fileName;
		loadProperties();
	}
	
	
	private void loadProperties() throws IOException {
		if(s_prop==null)
			s_prop = new Properties();
		FileInputStream fis = null;
		File checkFile = new File(this.path+ File.separator+this.filename);
		if(!checkFile.exists()) {
			checkFile.createNewFile();
		}
		
		fis = new FileInputStream(checkFile);
		
		s_prop.load(fis);
		
		fis.close();
		
	}
	
	public void saveProperties() throws IOException {
		
		FileOutputStream fis = null;
		File checkFile = new File(this.path+ File.separator+this.filename);
		if(!checkFile.exists()) {
			checkFile.createNewFile();
		}
		
		fis = new FileOutputStream(checkFile);
		
		s_prop.save(fis, "");
		fis.close();
		
	}
	
	public Object getProperty(Object key) {
		Object res=null;
		res = s_prop.get(key);
		return res;
	}


	public void toGlobal() {
		main = this;
	}
	
	public static Object getGlobalProperty(Object key) {
		return main.getProperty(key);
	}


	public void setProperty(String key, Object object) {
		s_prop.setProperty(key, object.toString());
		
	}
	
}
