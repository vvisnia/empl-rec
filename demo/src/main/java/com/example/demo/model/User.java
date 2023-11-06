package com.example.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "user_request_count")
@Data
@Builder(toBuilder = true)
public class User {
    @Id
    @Column(name = "LOGIN", unique = true)
    private String login;
    @Column(name = "REQUEST_COUNT")
    private Integer requestCount;

    public User() {
    }

    public User(String login, Integer requestCount) {
        this.login = login;
        this.requestCount = requestCount;
    }
}
