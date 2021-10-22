CREATE TABLE endpoint
(
    id varchar(36) PRIMARY KEY,
    method varchar(10),
    path varchar(1024),
    host varchar(1024),
    response_timeout int,
    policy json
)