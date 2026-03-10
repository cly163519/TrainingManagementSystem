package com.laura.tmsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainee {

    private Long salesId;
    private String name;
    private String branch;
    private String level;
    private String entryDate;
}
