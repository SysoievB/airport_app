--liquibase formatted sql
--changeset sysoiev:1

create table customers
(
    id            bigint not null auto_increment,
    created       datetime(6),
    status        varchar(255),
    updated       datetime(6),
    date_of_birth date,
    first_name    varchar(255),
    last_name     varchar(255),
    primary key (id)
) engine = InnoDB;

create table customers_tickets
(
    customer_id bigint not null,
    ticket_id   bigint not null,
    primary key (customer_id, ticket_id)
) engine = InnoDB;
create table roles
(
    id      bigint not null auto_increment,
    created datetime(6),
    status  varchar(255),
    updated datetime(6),
    name    varchar(255),
    primary key (id)
) engine = InnoDB;
create table tickets
(
    id            bigint not null auto_increment,
    created       datetime(6),
    status        varchar(255),
    updated       datetime(6),
    price         double precision,
    purchase_time datetime(6),
    primary key (id)
) engine = InnoDB;
create table user_roles
(
    user_id bigint not null,
    role_id bigint not null
) engine = InnoDB;
create table users
(
    id                        bigint not null auto_increment,
    created                   datetime(6),
    status                    varchar(255),
    updated                   datetime(6),
    last_password_change_date datetime(6),
    password                  varchar(255),
    username                  varchar(255),
    primary key (id)
) engine = InnoDB;
alter table customers_tickets
    add constraint FK7l61od5fjsqabsoo4i2uh2kud foreign key (ticket_id) references tickets (id);
alter table customers_tickets
    add constraint FKod7tg0rbk53byta8i9mhqkc0u foreign key (customer_id) references customers (id);
alter table user_roles
    add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id);
alter table user_roles
    add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id);
