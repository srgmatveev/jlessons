USE hw09;
CREATE TABLE IF NOT EXISTS User (
 id BIGINT(20) NOT NULL AUTO_INCREMENT,
 name VARCHAR(255),
 age INT(3),
 CONSTRAINT users_pk PRIMARY KEY (id)
);

