package com.covenant.Pojo;

public class User {
	
	int ID;
	String ref;
	String name;
	float cff;
	public User(int ID,String ref,String name,float cff) {
		this.ID=ID;
		this.ref=ref;
		this.name=name;
		this.cff=cff;
		
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getCff() {
		return cff;
	}
	public void setCff(float cff) {
		this.cff = cff;
	}
	
	
}
