package com.hassapp.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "accountCode")
public class AccountActivateCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_userNick")
    private User user;

    @Column
    private String activateCode;

    @Column
    private LocalDateTime date;
}
