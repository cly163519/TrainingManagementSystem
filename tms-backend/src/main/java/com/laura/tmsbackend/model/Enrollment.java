package com.laura.tmsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    private Long id;
    private Long trainingClassId;  // Class which trainee enroll in
    private Long traineeId;        // Which trainee
    private String enrollDate;
    private String attendanceStatus;
}
