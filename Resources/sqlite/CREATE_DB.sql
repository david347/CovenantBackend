CREATE TABLE cvn_client (
	client_id INTEGER NOT NULL PRIMARY KEY,
	name TEXT NOT NULL
);
CREATE TABLE cvn_user(
	cvn_user_id INTEGER NOT NULL PRIMARY KEY,
	ref_id TEXT,
	client_id INTEGER,
	name TEXT NOT NULL,
	coefficient REAL,
	FOREIGN KEY (client_id) REFERENCES cvn_client (client_id)
 );
CREATE TABLE cvn_poll(
	cvn_poll_id INTEGER NOT NULL PRIMARY KEY,
	client_id INTEGER,
	name TEXT NOT NULL,
	type TEXT NOT NULL,
	FOREIGN KEY (client_id) REFERENCES cvn_client (client_id)
 );
CREATE TABLE cvn_question(
	cvn_question_id INTEGER NOT NULL PRIMARY KEY,
	client_id INTEGER,
	name TEXT NOT NULL,
	type TEXT NOT NULL,
	cvn_poll_id INTEGER,
	FOREIGN KEY (cvn_poll_id) REFERENCES cvn_poll (cvn_poll_id),
	FOREIGN KEY (client_id) REFERENCES cvn_client (client_id)
 );
CREATE TABLE cvn_response(
	cvn_response_id INTEGER NOT NULL PRIMARY KEY,
	value TEXT,
	name TEXT,
	cvn_question_id INTEGER,
	UNIQUE (value,cvn_question_id),
	FOREIGN KEY (cvn_question_id) REFERENCES cvn_question (cvn_question_id)
 );
CREATE TABLE cvn_res_user(
	cvn_res_user_id INTEGER NOT NULL PRIMARY KEY,
	cvn_user_id INTEGER,
	cvn_question_id INTEGER,
	cvn_response_id INTEGER,
	UNIQUE (cvn_question_id,cvn_user_id),
	FOREIGN KEY (cvn_user_id) REFERENCES cvn_user (cvn_user_id),
	FOREIGN KEY (cvn_response_id) REFERENCES cvn_response (cvn_response_id),
	FOREIGN KEY (cvn_question_id) REFERENCES cvn_question (cvn_question_id)
 );
CREATE TABLE properties(
	properties_id INTEGER NOT NULL PRIMARY KEY,
	client_id INTEGER,
	name TEXT NOT NULL,
	value TEXT NOT NULL,
	FOREIGN KEY (client_id) REFERENCES cvn_client (client_id)
 );
CREATE TABLE presence(
	presence_id INTEGER NOT NULL PRIMARY KEY,
	cvn_user_id INTEGER,
	value TEXT NOT NULL,
	FOREIGN KEY (cvn_user_id) REFERENCES cvn_user (cvn_user_id),
	UNIQUE (cvn_user_id,value)
 ); 
 