CREATE OR REPLACE TRIGGER ORDER_HEADER_B_Update_status
BEFORE UPDATE
   ON ORDER_HEADER
FOR EACH ROW
BEGIN
    IF(:old.STATUS <> 'HOLD') THEN
        RAISE_APPLICATION_ERROR (
        num => -20111,
        msg => 'Can not change the status');
    END IF;
END;
/
CREATE OR REPLACE TRIGGER ORDER_HEADER_B_Update_user
    BEFORE INSERT OR UPDATE
        ON ORDER_HEADER
    FOR EACH ROW
DECLARE
    update_date timestamp(6);
    update_user varchar2(20);
BEGIN
    SELECT CURRENT_TIMESTAMP INTO update_date FROM DUAL;
    SELECT user INTO update_user FROM DUAL;
        :new.LAST_CHANGE_BY := update_user;
        :new.LAST_CHANGE_DATE := update_date;
    DBMS_OUTPUT.put_line('ORDER_HEADER_Update_data by '|| update_user ||' on '|| update_date);
END;
/