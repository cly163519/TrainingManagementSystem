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
}
