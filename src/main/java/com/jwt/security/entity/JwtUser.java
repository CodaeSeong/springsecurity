package com.jwt.security.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
//@SequenceGenerator(
//        name = "user_seq",
//        sequenceName = "user_seq",
//        initialValue = 1,
//        allocationSize = 1
//)
public class JwtUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String username;

    private String password;

    private String roles;

    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
