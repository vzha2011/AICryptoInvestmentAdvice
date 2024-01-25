package com.project.AICryptoInvestmentAdvice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_information")
public class User {
    @Id
    @Column(name="user_name")
    private String userName;
    private String password;
    @OneToMany
    @JoinColumn(name = "user_name", nullable = true)
    private List<AdviceRecord> recordList;

    public User() {
    }
}
