package com.covenant.Pojo;

import java.util.List;

public class Question {

	int cvn_question_id;
	int client_id;
	String name;
	String type;
	int cvn_poll_id;
	
	List<Response> responses;
	
	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	public Question(int cvn_question_id, int client_id,	String name, String type, int cvn_poll_id) {
		this.cvn_question_id = cvn_question_id;
		this.client_id = client_id;
		this.name = name;
		this.type = type;
		this.cvn_poll_id = cvn_poll_id;
	}
	
	public int getCvn_question_id() {
		return cvn_question_id;
	}

	public void setCvn_question_id(int cvn_question_id) {
		this.cvn_question_id = cvn_question_id;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCvn_poll_id() {
		return cvn_poll_id;
	}

	public void setCvn_poll_id(int cvn_poll_id) {
		this.cvn_poll_id = cvn_poll_id;
	}
	
	
}
