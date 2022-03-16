package com.application.airport_app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @LastModifiedDate
    @Column(name = "last_password_change_date")
    private Date lastPasswordChangeDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @OrderBy(value = "id ASC")
    private List<Role> roles;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, List<Role> roles) {
        this.id = id;
        this.roles = roles;
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(List<Role> roles) {
        this.roles = roles;
    }

    public User(String username, String password, AccountStatus status, Date lastPasswordChangeDate, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.lastPasswordChangeDate = lastPasswordChangeDate;
        this.roles = roles;
    }
}
