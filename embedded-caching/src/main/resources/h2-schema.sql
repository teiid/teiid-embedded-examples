DROP TABLE IF EXISTS PERFTEST;
DROP TABLE IF EXISTS PERFTEST_MAT;
DROP TABLE IF EXISTS PERFTEST_STAGING;
DROP TABLE IF EXISTS ITEMS;
DROP TABLE IF EXISTS QUERYSQL;
DROP TABLE IF EXISTS PERFRESULT;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS h2_test_mat;
DROP TABLE IF EXISTS mat_test_staging;
DROP TABLE IF EXISTS PRODUCT;


CREATE TABLE PERFTEST(id CHAR(4), col_a CHAR(16), col_b CHAR(40), col_c CHAR(40));
CREATE TABLE PERFTEST_MAT(id CHAR(4), col_a CHAR(16), col_b CHAR(40), col_c CHAR(40));
CREATE TABLE PERFTEST_STAGING(id CHAR(4), col_a CHAR(16), col_b CHAR(40), col_c CHAR(40));
CREATE TABLE QUERYSQL (id integer not null auto_increment, content varchar(255), PRIMARY KEY (id));
CREATE TABLE ITEMS (id integer not null auto_increment, item varchar(40), PRIMARY KEY (id));
CREATE TABLE PERFRESULT (id integer not null auto_increment, value integer, item_id integer, querysql_id integer, PRIMARY KEY (id));


CREATE TABLE status
(
  VDBName varchar(50) not null,
  VDBVersion varchar(50) not null,
  SchemaName varchar(50) not null,
  Name varchar(256) not null,
  TargetSchemaName varchar(50),
  TargetName varchar(256) not null,
  Valid boolean not null,
  LoadState varchar(25) not null,
  Cardinality long,
  Updated timestamp not null,
  LoadNumber long not null,
  PRIMARY KEY (VDBName, VDBVersion, SchemaName, Name)
);


ALTER TABLE PERFRESULT ADD CONSTRAINT FK_TO_QUERYSQL FOREIGN KEY (querysql_id) REFERENCES QUERYSQL (id);
ALTER TABLE PERFRESULT ADD CONSTRAINT FK_TO_ITEMS FOREIGN KEY (item_id) REFERENCES ITEMS (id);

INSERT INTO ITEMS (item) VALUES ('Query Time');
INSERT INTO ITEMS (item) VALUES ('Deserialize Time');
INSERT INTO ITEMS (item) VALUES ('Serialize Time');




CREATE TABLE h2_test_mat
(
   product_id integer,
   SYMBOL varchar(16)
);

CREATE TABLE mat_test_staging
(
   product_id integer,
   SYMBOL varchar(16)
);



CREATE TABLE  PRODUCT (
   ID integer,
   SYMBOL varchar(16),
   COMPANY_NAME varchar(256),
   CONSTRAINT PRODUCT_PK PRIMARY KEY(ID)
);


INSERT INTO PRODUCT (ID,SYMBOL,COMPANY_NAME) VALUES(100,'IBM','International Business Machines Corporation');
INSERT INTO PRODUCT (ID,SYMBOL,COMPANY_NAME) VALUES(101,'DELL','Dell Computer Corporation');
INSERT INTO PRODUCT (ID,SYMBOL,COMPANY_NAME) VALUES(102,'HPQ','Hewlett-Packard Company');
INSERT INTO PRODUCT (ID,SYMBOL,COMPANY_NAME) VALUES(103,'GE','General Electric Company');
INSERT INTO PRODUCT (ID,SYMBOL,COMPANY_NAME) VALUES(104,'SAP','SAP AG');
INSERT INTO PRODUCT (ID,SYMBOL,COMPANY_NAME) VALUES(105,'TM','Toyota Motor Corporation');
