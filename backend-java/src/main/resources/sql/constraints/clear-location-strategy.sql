drop procedure pick_location_search_clear_loc/
create or replace
    procedure pick_location_search_clear_loc(pl_id in pick_list.id%TYPE,
                                             sku_needed in varchar2,
                                             sku_qty in number,
                                             ol_id in order_line.id%TYPE) is
    current_pick_list_id         pick_list.id%TYPE := pl_id;
    requested_order_line_id      varchar2(100)     := ol_id;
    requested_sku                varchar2(100)     := sku_needed;
    requested_sku_qty            number            := sku_qty;
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
        where c.SKU_ID = requested_sku and c.TYPE ='STORAGE'
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
                                          "PICK_LIST_ID",
                                          "FROM_CONTAINER_ID",
                                          "TO_CONTAINER_ID",
                                          "QTY",
                                          "ORDER_LINE_ID",
                                          "STATUS")
                    values (PICK_TASK_ID_GEN.nextval,
                            current_pick_list_id,
                            counter.ID,
                            NULL,
                            current_container_alloca_qty,
                            requested_order_line_id,
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
                                          "PICK_LIST_ID",
                                          "FROM_CONTAINER_ID",
                                          "TO_CONTAINER_ID",
                                          "QTY",
                                          "ORDER_LINE_ID",
                                          "STATUS")
                    values (PICK_TASK_ID_GEN.nextval,
                            current_pick_list_id,
                            counter.ID,
                            NULL,
                            current_container_alloca_qty,
                            requested_order_line_id,
                            'READY');
                    requested_sku_qty := requested_sku_qty - current_container_alloca_qty;
                END IF;

                Dbms_output.put_line('Picked qty: ' || counter.SKU_QTY || ' Remainig: ' || requested_sku_qty);
            END IF;
        END LOOP;

end pick_location_search_clear_loc;
/