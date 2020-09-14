package com.netcracker.edu.distancestudyweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    private Long id;
    private String name;
    private String description;
    private boolean selected;
}
