package com.laura.tmsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingClass {

    private Long classNo;
    private String className;
    private String state;        // PLANNED, ENROLLING, ONGOING, COMPLETED
    private String coordinator;
    private String level;        // branches
    private String courseLevel;  // New Recruit / Agent Qualification etc
    private int attendCount;
    private int classDays;
    private String startDate;
    private String endDate;
    private String department;
    private String location;
}
