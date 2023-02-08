create database if not exists endpoint_monitoring_db;
use endpoint_monitoring_db;

create table if not exists user (
    id varchar(36) primary key,
    username varchar(100),
    email varchar(100),
    access_token varchar(36)
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
    monitored_interval blob,
    monitored_endpoint_id varchar(36),
    foreign key (monitored_endpoint_id) references monitored_endpoint(id)
);


-- seed data
INSERT INTO user (id, username, email, access_token) 
SELECT '00000000-0000-0000-0000-000000000001', 'Applifting', 'info@applifting.cz', '93f39e2f-80de-4033-99ee-249d92736a25' FROM DUAL 
WHERE NOT EXISTS (SELECT * FROM user WHERE id='00000000-0000-0000-0000-000000000001' LIMIT 1);

INSERT INTO user (id, username, email, access_token) 
SELECT '00000000-0000-0000-0000-000000000002', 'Batman', 'batman@example.com', 'dcb20f8a-5657-4f1b-9f7f-ce65739b359e' FROM DUAL 
WHERE NOT EXISTS (SELECT * FROM user WHERE id='00000000-0000-0000-0000-000000000002' LIMIT 1);