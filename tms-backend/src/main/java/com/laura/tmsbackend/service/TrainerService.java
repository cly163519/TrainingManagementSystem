package com.laura.tmsbackend.service;

import com.laura.tmsbackend.mapper.TrainerMapper;
import com.laura.tmsbackend.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private TrainerMapper trainerMapper;

    public void insert(Trainer trainer){
        trainerMapper.insert(trainer);
    }

    public void deleteById(Long trainerId){
        trainerMapper.deleteById(trainerId);
    }

    public void update(Trainer trainer){
        trainerMapper.update(trainer);
    }

    public Trainer findById(Long trainerId){
        return trainerMapper.findById(trainerId);
    }

    public List<Trainer> findAll(){
        return trainerMapper.findAll();
    }

}
