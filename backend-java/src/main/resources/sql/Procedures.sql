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

begin

    OPEN cursor_qty(oh_number);
    LOOP
        FETCH cursor_qty INTO cursor_sku_qty, cursor_sku, cursor_order_line_id;
        EXIT WHEN (cursor_qty%ROWCOUNT > 100) OR (cursor_qty%NOTFOUND);

        pick_location_search_clear_loc(
                cursor_sku, cursor_sku_qty, cursor_order_line_id
            );

        move_tasks_created := move_tasks_created + 1;
    END LOOP;
    --Dbms_output.put_line('Total number of PT created is: '||move_tasks_created); --apparently ROWNUM does not work
    CLOSE cursor_qty;

end pick_task_generation;
/

create or replace procedure pick_location_search_clear_loc(sku_needed in varchar2, sku_qty in number,
                                                           order_line_id in varchar2) is

    requested_order_line_id      varchar2(100) := order_line_id;
    requested_sku                varchar2(100) := sku_needed;
    requested_sku_qty            number        := sku_qty;
    current_container_sku        varchar2(100);
    current_container_qty        number;
    current_container_id         number;
    current_container_alloca_qty number;
    current_container_free_qty   number;
    CURSOR find_containers (requested_sku IN varchar2, requested_sku_qty IN number, requested_order_line_id IN varchar2 )
        IS
        select c.SKU_QTY, c.SKU_ID, c.ID, c.ALLOCATED_QTY, c.FREE_QTY
        INTO current_container_qty, current_container_sku, current_container_id, current_container_alloca_qty, current_container_free_qty
        from container c
        where c.SKU_ID = requested_sku
        order by c.SKU_QTY asc;

begin

    FOR counter IN find_containers(requested_sku, requested_sku_qty, requested_order_line_id)
        LOOP
            Dbms_output.put_line(
                        'Containers visited: ' || counter.ID || ' qty: ' || counter.SKU_QTY || ' sku_id: ' ||
                        counter.SKU_ID);
            IF counter.SKU_QTY <> 0 THEN

                IF requested_sku_qty <= counter.FREE_QTY THEN

                    current_container_alloca_qty := requested_sku_qty;
                    UPDATE container
                    SET ALLOCATED_QTY = (ALLOCATED_QTY + current_container_alloca_qty),
                        FREE_QTY      = (FREE_QTY - current_container_alloca_qty)
                    where ID = counter.ID;
                    Dbms_output.put_line('requested_order_line_id: ' || requested_order_line_id);
                    UPDATE ORDER_LINE
                    SET ALLOCATED = (ALLOCATED + current_container_alloca_qty)
                    where ID = requested_order_line_id;
                    insert into Pick_task("ID",
                                          "FROM_CONTAINER_ID",
                                          "TO_CONTAINER_ID",
                                          "QTY",
                                          "SKU_ID",
                                          "STATUS")
                    values (PICK_TASK_ID_GEN.nextval,
                            counter.ID,
                            NULL,
                            current_container_alloca_qty,
                            counter.SKU_ID,
                            'READY');
                    requested_sku_qty := requested_sku_qty - current_container_alloca_qty;
                ELSE
                    current_container_alloca_qty := counter.FREE_QTY;
                    UPDATE CONTAINER
                    SET ALLOCATED_QTY = (ALLOCATED_QTY + current_container_alloca_qty),
                        FREE_QTY      = (FREE_QTY - current_container_alloca_qty)
                    where ID = counter.ID;
                    Dbms_output.put_line('requested_order_line_id: ' || requested_order_line_id);
                    UPDATE ORDER_LINE
                    SET ALLOCATED = (ALLOCATED + current_container_alloca_qty)
                    where ID = requested_order_line_id;
                    insert into Pick_task("ID",
                                          "FROM_CONTAINER_ID",
                                          "TO_CONTAINER_ID",
                                          "QTY",
                                          "SKU_ID",
                                          "STATUS")
                    values (PICK_TASK_ID_GEN.nextval,
                            counter.ID,
                            NULL,
                            current_container_alloca_qty,
                            counter.SKU_ID,
                            'READY');
                    requested_sku_qty := requested_sku_qty - current_container_alloca_qty;
                END IF;

                Dbms_output.put_line('Picked qty: ' || counter.SKU_QTY || ' Remainig: ' || requested_sku_qty);
            END IF;
        END LOOP;

end pick_location_search_clear_loc;
/

exec pick_location_search_clear_loc ('SKU1',30);
exec pick_task_generation (1);
select *
from pick_task;
select *
from order_line;
select *
from order_header;
select *
from containers
where sku in ('SKU1', 'SKU2')
order by sku, qty asc;
select *
from containers
order by sku, qty asc;
update order_header
set status ='RELEASED'
where id = 1;

BEGIN
    update order_line set allocated = 0;
    update containers set free_qty = qty, allocated_qty =0;
    update order_header set status ='HOLD';
    delete from pick_task;
    commit;
END;
/



select *
from dcsdba.order_line;