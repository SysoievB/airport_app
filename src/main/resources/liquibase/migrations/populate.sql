--liquibase formatted sql
--changeset sysoiev:2

-- Insert data
-- populate users
INSERT INTO users (username, password, created, updated, status)
VALUES ('admin', '$2y$12$p2X0OkKfybvIspoee/x93eAcv6TVqRyQOApZVYKZxrMQIegsBIEYG',
        CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE');

-- populate roles
INSERT INTO roles(id, created, status, updated, name)
VALUES (1, CURRENT_TIMESTAMP(), 'ACTIVE',  CURRENT_TIMESTAMP(), 'ROLE_USER'),
       (2,  CURRENT_TIMESTAMP(), 'ACTIVE', CURRENT_TIMESTAMP(), 'ROLE_MODERATOR'),
       (3, CURRENT_TIMESTAMP(), 'ACTIVE',  CURRENT_TIMESTAMP(), 'ROLE_ADMIN');

-- populate user_roles
INSERT INTO user_roles
VALUES (1, 1),(1, 2),(1, 3);

-- populate customers
INSERT INTO customers(id, created, status, updated, date_of_birth, first_name, last_name)
VALUES (1,  CURRENT_TIMESTAMP(), 'ACTIVE', CURRENT_TIMESTAMP(), '1986-04-11', 'Alex', 'Andreev'),
       (2, CURRENT_TIMESTAMP(), 'ACTIVE', CURRENT_TIMESTAMP(), '1963-05-09', 'Vlad', 'Andreev');

-- populate tickets
INSERT INTO tickets(id, price, status, purchase_time, arrival, departure)
VALUES (1, 100.00,'ACTIVE', CURRENT_TIMESTAMP(), 'NY', 'LA'),
       (2, 200.00,'ACTIVE', CURRENT_TIMESTAMP(), 'London', 'Sydney');

-- populate customers_tickets
INSERT INTO customers_tickets
VALUES (1, 1);
INSERT INTO customers_tickets
VALUES (2, 1);
INSERT INTO customers_tickets
VALUES (2, 2);