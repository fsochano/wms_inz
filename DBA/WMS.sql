CREATE USER FISO
  IDENTIFIED BY password
  DEFAULT TABLESPACE tbs_01
  TEMPORARY TABLESPACE tbs_temp_02
  QUOTA 20M on tbs_01;
  
  select * from all_users; --ok
  
  select * from dba_users; --ok
  
  CREATE TABLESPACE tbs_01 --ok
   DATAFILE 'tbs_f2.dbf' SIZE 40M 
   ONLINE; --ok
   
   CREATE TEMPORARY TABLESPACE tbs_temp_02 --ok
  TEMPFILE 'temp02.dbf' SIZE 5M AUTOEXTEND ON
  TABLESPACE GROUP tbs_grp_01;
  
  alter session set "_ORACLE_SCRIPT"=true;   --ok
  
  GRANT CONNECT TO FISO; --ok
    
  ALTER USER FISO IDENTIFIED BY password1; -not/ok