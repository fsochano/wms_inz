CREATE TABLE "CONTAINERS"
(
    "CONTAINER_ID"   NUMBER NOT NULL ENABLE,
    "QTY"            VARCHAR2(4000),
    "CONTAINER_SIZE" VARCHAR2(4000),
    "SKU_CAPACITY"   VARCHAR2(4000),
    "SKU"            VARCHAR2(4000)
        CONSTRAINT "CONTAINERS_PK" PRIMARY KEY ("CONTAINER_ID")
        USING INDEX ENABLE
)
/
ALTER TABLE "CONTAINERS"
    ADD "ALLOCATED_QTY" NUMBER;
/
ALTER TABLE "CONTAINERS"
    ADD "FREE_QTY" NUMBER;
/
CREATE TABLE "LOCATIONS"
(
    "LOCATION_ID"       NUMBER NOT NULL ENABLE,
    "LOCATION_CAPACITY" NUMBER NOT NULL ENABLE,
    CONSTRAINT "LOCATIONS_PK" PRIMARY KEY ("LOCATION_ID")
        USING INDEX ENABLE
)
/
CREATE TABLE "ORDER_LINE"
(
    "ID"       NUMBER NOT NULL ENABLE,
    "ORDER_ID" NUMBER NOT NULL ENABLE,
    "SKU"      VARCHAR2(4000),
    "QTY"      NUMBER,
    CONSTRAINT "ORDER_LINE_PK" PRIMARY KEY ("ID", "ORDER_ID")
        USING INDEX ENABLE
)
/
ALTER TABLE "ORDER_LINE"
    ADD "ALLOCATED" NUMBER;
/
CREATE TABLE "ORDER_HEADER"
(
    "ID"     NUMBER NOT NULL ENABLE,
    "STATUS" VARCHAR2(4000),
    CONSTRAINT "ORDER_HEADER_PK" PRIMARY KEY ("ID")
        USING INDEX ENABLE
)
/
UPDATE ORDER_LINE
set STATUS ='HOLD';
CREATE TABLE "PICK_TASK"
(
    "ID"             NUMBER NOT NULL ENABLE,
    "FROM_CONTAINER" VARCHAR2(4000),
    "TO_LOC"         VARCHAR2(4000),
    "SKU"            VARCHAR2(4000),
    "QTY"            NUMBER,
    CONSTRAINT "PICK_TASK_PK" PRIMARY KEY ("ID")
        USING INDEX ENABLE
)
/
create sequence PICK_TASK_SEQ
    start with 1
    increment by 1
    cache 20
    minvalue 1
    maxvalue 999999
    nocycle
    noorder;
/
--CREATE OR REPLACE EDITIONABLE SYNONYM  "DBMS_XPLAN" FOR "LIVESQL"."ORACLE_SQL_USER_XPLAN"
--/
--CREATE OR REPLACE EDITIONABLE SYNONYM  "V$SESSION" FOR "LIVESQL"."ORACLE_SQL_USER_V$SESSION"
--/
--CREATE OR REPLACE EDITIONABLE SYNONYM  "V$SQL_PLAN_STATISTICS_ALL" FOR "LIVESQL"."ORACLE_SQL_USER_V$SQL_PLAN_S_A"
--/

select *
from containers;


insert into containers
(container_id,
 qty,
 container_size,
 sku_capacity,
 sku)
values (1, 30, 1, 30, 'SKU1');

insert into containers
(container_id,
 qty,
 container_size,
 sku_capacity,
 sku)
values (2, 30, 1, 30, 'SKU2');

insert into containers
(container_id,
 qty,
 container_size,
 sku_capacity,
 sku)
values (3, 30, 1, 30, 'SKU3');

insert into containers
(container_id,
 qty,
 container_size,
 sku_capacity,
 sku)
values (4, 15, 1, 30, 'SKU1');

insert into containers
(container_id,
 qty,
 container_size,
 sku_capacity,
 sku)
values (5, 15, 1, 30, 'SKU2');

insert into containers
(container_id,
 qty,
 container_size,
 sku_capacity,
 sku)
values (6, 15, 1, 30, 'SKU3');

update containers
set free_qty      = qty,
    allocated_qty =0;
select *
from containers c
order by c.sku;

INSERT INTO Order_line("ID",
                       "ORDER_ID",
                       "SKU",
                       "QTY",
                       "ALLOCATED")
VALUES (1, 1, 'SKU2', 45, 0);

INSERT INTO Order_line("ID",
                       "ORDER_ID",
                       "SKU",
                       "QTY",
                       "ALLOCATED")
VALUES (2, 1, 'SKU1', 35, 0);

INSERT INTO Order_line("ID",
                       "ORDER_ID",
                       "SKU",
                       "QTY",
                       "ALLOCATED")
VALUES (3, 2, 'SKU3', 100, 0);
INSERT INTO Order_Header("ID",
                         "STATUS")
VALUES (1, 'HOLD');
INSERT INTO Order_Header("ID",
                         "STATUS")
VALUES (2, 'HOLD');

select *
from order_line;