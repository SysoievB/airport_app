package com.application.airport_app.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User extends BaseEntity {

    private String username;

    private String password;

    private Date created;

    private Date updated;

    private Date lastPasswordChangeDate;

    private Account account;
}
