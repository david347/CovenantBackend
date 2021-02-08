package com.covenant.Pojo;

public class Response {
	int ID;
	String value;
	String name;
	
	public Response(int ID, String value, String name) {
		this.ID=ID;
		this.value=value;
		this.name=name;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
