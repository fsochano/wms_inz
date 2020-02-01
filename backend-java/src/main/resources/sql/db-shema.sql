create sequence container_id_gen start with 1 increment by  1
create sequence location_id_gen start with 1 increment by  1
create sequence order_header_id_gen start with 1 increment by  1
create sequence order_line_id_gen start with 1 increment by  1
create sequence pick_list_id_gen start with 1 increment by  1
create sequence pick_task_id_gen start with 1 increment by  1
create sequence sku_id_gen start with 1 increment by  1
create table app_authority (authority varchar2(255 char) not null, username varchar2(255 char) not null, primary key (authority, username))
create table app_user (username varchar2(50 char) not null, is_account_non_expired number(1,0) not null, is_account_non_locked number(1,0) not null, is_credentials_non_expired number(1,0) not null, is_enabled number(1,0) not null, password varchar2(100 char) not null, primary key (username))
create table container (id number(19,0) not null, created_by varchar2(255 char), created_date timestamp, last_modified_by varchar2(255 char), last_modified_date timestamp, allocated_qty number(19,0) not null, container_size number(19,0) not null, free_qty number(19,0) not null, sku_capacity number(19,0) not null, sku_qty number(19,0) not null, type varchar2(255 char) not null, location_id number(19,0) not null, sku_id number(19,0) not null, primary key (id))
create table location (id number(19,0) not null, capacity number(19,0) not null, location_type varchar2(255 char) not null, name varchar2(255 char), primary key (id))
create table order_header (id number(19,0) not null, created_by varchar2(255 char), created_date timestamp, last_modified_by varchar2(255 char), last_modified_date timestamp, name varchar2(255 char) not null, status varchar2(255 char) not null, primary key (id))
create table order_line (id number(19,0) not null, created_by varchar2(255 char), created_date timestamp, last_modified_by varchar2(255 char), last_modified_date timestamp, allocated number(19,0) not null, qty number(19,0) not null, order_header_id number(19,0) not null, sku_id number(19,0) not null, primary key (id))
create table pick_list (id number(19,0) not null, created_by varchar2(255 char), created_date timestamp, last_modified_by varchar2(255 char), last_modified_date timestamp, status varchar2(255 char), order_header_id number(19,0) not null, primary key (id))
create table pick_task (id number(19,0) not null, created_by varchar2(255 char), created_date timestamp, last_modified_by varchar2(255 char), last_modified_date timestamp, qty number(19,0) not null, status varchar2(255 char), from_container_id number(19,0), order_line_id number(19,0) not null, pick_list_id number(19,0) not null, to_container_id number(19,0), primary key (id))
create table sku (id number(19,0) not null, description varchar2(255 char), name varchar2(255 char), primary key (id))
alter table app_authority add constraint FK197thtkcx4bjw4i2kjd2uhq5f foreign key (username) references app_user
alter table container add constraint FKi31dqrimilld25h4jsnae06yk foreign key (location_id) references location
alter table container add constraint FKaa89vjo5ii89krxduwp91msmq foreign key (sku_id) references sku
alter table order_line add constraint FKoujl67v3lk4glhmln31imw1wo foreign key (order_header_id) references order_header
alter table order_line add constraint FKrglnumwuohendwx9ntuixjd0k foreign key (sku_id) references sku
alter table pick_list add constraint FKj6q1c6tm8kpw8dinyexwmf0g8 foreign key (order_header_id) references order_header
alter table pick_task add constraint FKoeb48flohhbu3a5s2o4d441dy foreign key (from_container_id) references container
alter table pick_task add constraint FK4xsr7jqifxxpimrd27899g9k9 foreign key (order_line_id) references order_line
alter table pick_task add constraint FKignl1s7c99kgfyqh41x9t8g7g foreign key (pick_list_id) references pick_list
alter table pick_task add constraint FKee4rsl6atnph8vpnwtp22yf66 foreign key (to_container_id) references container