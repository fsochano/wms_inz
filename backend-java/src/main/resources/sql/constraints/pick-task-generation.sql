drop procedure pick_task_generation/
create or replace procedure pick_task_generation(
    oh_number in varchar2
) is

    cursor cursor_qty (oh_number IN varchar2) is
        SELECT ol.QTY, ol.SKU_ID, ol.ID
        FROM order_line ol
        WHERE ol.ORDER_HEADER_ID = oh_number;
    --group by ol.sku, ol.order_id;

    sku_qty              number;
    cursor_sku_qty       pick_task.QTY%TYPE;
    cursor_sku           order_line.SKU_ID%TYPE;
    cursor_order_line_id order_line.id%TYPE;
    move_tasks_created   number;
    pick_list_id         PICK_LIST.id%TYPE;

begin

    pick_list_id := PICK_LIST_ID_GEN.nextval;
    insert into PICK_LIST (ID, STATUS, ORDER_HEADER_ID) values (pick_list_id, 'RELEASED', oh_number);
    OPEN cursor_qty(oh_number);
    LOOP
        FETCH cursor_qty INTO cursor_sku_qty, cursor_sku, cursor_order_line_id;
        EXIT WHEN (cursor_qty%ROWCOUNT > 100) OR (cursor_qty%NOTFOUND);

        pick_location_search_clear_loc(
                pick_list_id, cursor_sku, cursor_sku_qty, cursor_order_line_id
            );

        move_tasks_created := move_tasks_created + 1;
    END LOOP;
    --Dbms_output.put_line('Total number of PT created is: '||move_tasks_created); --apparently ROWNUM does not work
    CLOSE cursor_qty;

end pick_task_generation;
/