create database if not exists endpoint_monitoring_db;
use endpoint_monitoring_db;

create table if not exists user (
    id varchar(36) primary key,
    username varchar(100),
    email varchar(100),
    access_token varchar(36),
    unique(access_token)
);

create table if not exists monitored_endpoint (
    id varchar(36) primary key,
    name varchar(100),
    url varchar(1000),
    date_of_creation datetime,
    monitored_interval int,
    owner_id varchar(36),
    foreign key (owner_id) references user(id)
);

create table if not exists monitoring_result (
    id varchar(36) primary key,
    date_of_check datetime,
    http_status int,
    returned_payload longblob,
    monitored_endpoint_id varchar(36),
    foreign key (monitored_endpoint_id) references monitored_endpoint(id) on delete cascade
);

-- create index if exists
select if (
    exists(
        select distinct index_name from information_schema.statistics 
        where table_schema = 'endpoint_monitoring_db' 
        and table_name = 'monitoring_result' 
        and index_name like 'monitoring_result_date_index'
    )
    ,'select ''index monitoring_result_date_index already exists'';'
    ,'create index monitoring_result_date_index on monitoring_result(monitored_endpoint_id, date_of_check desc);') into @x;
prepare conditional_statement from @x;
execute conditional_statement;
deallocate prepare conditional_statement;

-- seed data
insert into user (id, username, email, access_token) 
select '00000000-0000-0000-0000-000000000001', 'Applifting', 'info@applifting.cz', '93f39e2f-80de-4033-99ee-249d92736a25' from dual 
where not exists (select * from user where id='00000000-0000-0000-0000-000000000001' limit 1);

insert into user (id, username, email, access_token) 
select '00000000-0000-0000-0000-000000000002', 'Batman', 'batman@example.com', 'dcb20f8a-5657-4f1b-9f7f-ce65739b359e' from dual 
where not exists (select * from user where id='00000000-0000-0000-0000-000000000002' limit 1);