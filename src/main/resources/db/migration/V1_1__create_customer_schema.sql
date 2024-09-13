CREATE TABLE CUSTOMER
(
    ID         varchar(255) primary key not null,
    VERSION    int                      not null,
    NAME       varchar(255)             not null,
    EMAIL      varchar(255)             not null,
    CREATED_AT timestamp                not null,
    UPDATED_AT timestamp                not null,
    STATE      varchar(255)             not null
);