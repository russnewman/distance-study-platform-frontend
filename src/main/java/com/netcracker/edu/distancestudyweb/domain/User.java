package com.netcracker.edu.distancestudyweb.domain;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private Role role;
    private List<String> authorities;
}
