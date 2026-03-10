package com.laura.tmsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachingAssignment {
    private Long id;
    private Long trainingClassId;  // Which class
    private Long trainerId;        // Which trainer
}
