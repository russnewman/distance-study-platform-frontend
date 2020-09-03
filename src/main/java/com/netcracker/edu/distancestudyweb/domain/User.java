package com.netcracker.edu.distancestudyweb.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private Role role;
}
