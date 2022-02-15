package com.application.airport_app.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "accounts")
public class Account extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;
}
