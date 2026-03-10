package com.laura.tmsbackend.mapper;

import com.laura.tmsbackend.model.Trainee;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TraineeMapper {
    void insert(Trainee trainee);
    void deleteById(Long salesId);
    void update(Trainee trainee);
    List<Trainee> findAll();
    Trainee findById(Long salesId);
}
