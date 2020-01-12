CREATE OR REPLACE TRIGGER ORDER_HEADER
BEFORE UPDATE
   ON ORDER_HEADER
FOR EACH ROW
BEGIN
    IF(:old.STATUS <> '0') THEN
        RAISE_APPLICATION_ERROR (
        num => -20111,
        msg => 'Can not change the status');
    END IF;
END;
/
insert into ORDER_HEADER (ID, NAME, STATUS) values (ORDER_HEADER_ID_GEN.nextval, 'dupa', '0')/
insert into ORDER_HEADER (ID, NAME, STATUS) values (ORDER_HEADER_ID_GEN.nextval, 'dupa1', '0')/
