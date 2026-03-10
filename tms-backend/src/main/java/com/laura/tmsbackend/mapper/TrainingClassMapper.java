package com.laura.tmsbackend.mapper;

import com.laura.tmsbackend.model.TrainingClass;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrainingClassMapper {
    void insert(TrainingClass trainingClass);
    void deleteById(Long classNo);
    void update(TrainingClass trainingClass);
    List<TrainingClass> findAll();
    TrainingClass findById(Long classNo);
}
