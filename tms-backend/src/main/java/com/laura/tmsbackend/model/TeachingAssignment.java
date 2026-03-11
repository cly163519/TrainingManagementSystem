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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrainingClassId() {
        return trainingClassId;
    }

    public void setTrainingClassId(Long trainingClassId) {
        this.trainingClassId = trainingClassId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }
}
