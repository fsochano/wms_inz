create or replace trigger HoldToReleased
    BEFORE UPDATE
    ON Order_Header
    FOR EACH ROW
BEGIN
    IF :old.status = 'HOLD' and :new.status = 'RELEASED' THEN
        pick_task_generation(:old.id);
    END IF;
END;
/