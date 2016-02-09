CREATE TABLE T_USER (
	id SERIAL,
	name VARCHAR(254), 
	firstname VARCHAR(254), 
	email VARCHAR(254),
	date_connect DATE, 
	date_update DATE, 
	PRIMARY KEY (id)
);

CREATE TABLE T_USER_IDX (
	t_user_id INT NOT NULL,
	rs_search INT NOT NULL
);