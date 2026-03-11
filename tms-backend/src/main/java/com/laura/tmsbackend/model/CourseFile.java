package com.laura.tmsbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseFile {

    private Long courseFileId;
    private String slidesName;
    private String fileName;
    private Long trainingClassId;  // Belongs to which class

    public Long getCourseFileId() {
        return courseFileId;
    }

    public void setCourseFileId(Long courseFileId) {
        this.courseFileId = courseFileId;
    }

    public String getSlidesName() {
        return slidesName;
    }

    public void setSlidesName(String slidesName) {
        this.slidesName = slidesName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getTrainingClassId() {
        return trainingClassId;
    }

    public void setTrainingClassId(Long trainingClassId) {
        this.trainingClassId = trainingClassId;
    }
}
