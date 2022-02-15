# airport_app
JWT based spring security REST Api with DB & CRUD operations with such entities:

User (id, username, password, created, updated, lastPasswordChangeDate, Account account)

Ticket (id, Flight flight price, purchaseTime)

Flight(id, from, to)

Account(id, AccountStatus status)

Customer (id, firstName, lastName, Set tickets, Account account)

enum AccountStatus {ACTIVE, DELETED, BANNED}

Requirements:

All CRUD operations for every entity

MVC pattern

Use Maven & Spring (IoC, Security, Data, etc.)

For connection with DB use - Spring Data Jpa

Initializing DB should be with liquibase

User interaction needs to be implemented with Postman (https://www.getpostman.com/)

App should have 3 roles: ROLE_ADMIN (has full access to all entities)

ROLE_MODERATOR (has read and write access for all entities Developer)

ROLE_USER (has read access for entities Developer,Skill)

Technologies: Java, MySQL, Spring (MVC, Web, Data, Security, Boot), Lombok, Maven, Liquibase.
