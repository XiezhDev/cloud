CREATE TABLE tuser (
  id varchar(36) PRIMARY KEY,
  username varchar(10),
  password varchar(100),
  truename varchar(10),
  email varchar(30),
  phone varchar(15),
  state decimal(1),
  code varchar(36)
);