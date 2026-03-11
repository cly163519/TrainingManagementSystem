package com.laura.tmsbackend.service;

import com.laura.tmsbackend.mapper.TrainingClassMapper;
import com.laura.tmsbackend.model.TrainingClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingClassService {
    @Autowired
    private TrainingClassMapper trainingClassMapper;

    public void insert(TrainingClass trainingClass){
        trainingClassMapper.insert(trainingClass);
    }

    public void deleteById(Long classNo){
        trainingClassMapper.deleteById(classNo);
    }

    public void update(TrainingClass trainingClass){
        trainingClassMapper.update(trainingClass);
    }

    public List<TrainingClass> findAll(){
        return trainingClassMapper.findAll();
    }

    public TrainingClass findById(Long classNo){
        return trainingClassMapper.findById(classNo);
    }

}
