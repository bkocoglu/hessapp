package com.hassapp.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column
    private String nicname;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String mail;

    @Column
    private String userType;

    @Column
    private String password;

    @Column
    private int birthYear;

    @Column
    private String gender;

    @ManyToOne
    @JoinColumn(name = "fk_cityId")
    private City city;

    @Column
    private String photoUrl;

    @Column
    private boolean isActive;

}
