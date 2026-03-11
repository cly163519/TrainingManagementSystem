package com.laura.tmsbackend.service;

import com.laura.tmsbackend.mapper.TraineeMapper;
import com.laura.tmsbackend.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    @Autowired

    private TraineeMapper traineeMapper;

    public void insert(Trainee trainee){
        traineeMapper.insert(trainee);
    }

    public void deleteById(Long salesId){
        traineeMapper.deleteById(salesId);
    }

    public void update(Trainee trainee){
        traineeMapper.update(trainee);
    }

    public Trainee findById(Long salesId){
        return traineeMapper.findById(salesId);
    }

    public List<Trainee> findAll(){
        return traineeMapper.findAll();
    }
}
