create or replace trigger HoldToReleased
    BEFORE UPDATE
    ON ORDER_HEADER
    FOR EACH ROW
BEGIN
    IF :old.status = 'HOLD' and :new.status = 'RELEASED' THEN
        pick_task_generation(:old.id);
    END IF;
END;
/