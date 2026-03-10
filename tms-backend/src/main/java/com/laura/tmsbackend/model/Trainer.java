package com.laura.tmsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {

    private Long trainerId;
    private String name;
    private String courseName;
    private String branch;
}
