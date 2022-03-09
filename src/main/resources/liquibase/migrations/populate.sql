--liquibase formatted sql
--changeset sysoiev:2

-- Insert data
-- populate users
INSERT INTO users (username, password, created, updated, status)
VALUES ('admin', '$2y$12$p2X0OkKfybvIspoee/x93eAcv6TVqRyQOApZVYKZxrMQIegsBIEYG',
        CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');

-- populate roles
INSERT INTO roles
VALUES (1, 'ROLE_USER', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');
INSERT INTO roles
VALUES (2, 'ROLE_MODERATOR', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');
INSERT INTO roles
VALUES (3, 'ROLE_ADMIN', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');

-- populate user_roles
INSERT INTO user_roles
VALUES (1, 1),(1, 2),(1, 3);

-- populate customers
INSERT INTO customers
VALUES (1, 'Alex', 'Andreev','1986-04-11', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');
INSERT INTO customers
VALUES (2, 'Vlad', 'Andreev', '1963-05-09', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');
create table tickets
(
    id            bigint not null auto_increment,
    created       datetime(6),
    status        varchar(255),
    updated       datetime(6),
    price         double precision,
    purchase_time datetime(6),
    arrival       varchar(255),
    departure     varchar(255),
    primary key (id)
) engine = InnoDB;
-- populate tickets
INSERT INTO tickets(id, price, status, purchase_time, arrival, departure)
VALUES (1, 100.00,'ACTIVE', CURRENT_TIMESTAMP(), 'NY', 'LA');
INSERT INTO tickets
VALUES (2, 200.00,'ACTIVE', CURRENT_TIMESTAMP(), 'London', 'Sydney');

-- populate customers_tickets
INSERT INTO customers_tickets
VALUES (1, 1);
INSERT INTO customers_tickets
VALUES (2, 1);
INSERT INTO customers_tickets
VALUES (2, 2);