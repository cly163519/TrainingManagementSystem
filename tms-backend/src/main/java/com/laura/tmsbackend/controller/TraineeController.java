package com.laura.tmsbackend.controller;

import com.laura.tmsbackend.model.Trainee;
import com.laura.tmsbackend.service.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainee")
public class TraineeController {
    @Autowired
    private TraineeService traineeService;

    @PostMapping
    public void insert(@RequestBody Trainee trainee){
        traineeService.insert(trainee);
    }
    @DeleteMapping("/{salesId}")
    public void deleteById(@PathVariable Long salesId){
        traineeService.deleteById(salesId);
    }
    @PutMapping
    public void update(@RequestBody Trainee trainee){
        traineeService.update(trainee);
    }
    @GetMapping
    public List<Trainee> findAll(){
        return traineeService.findAll();
    }
    @GetMapping("/{salesId}")
    public Trainee findById(@PathVariable Long salesId){
        return traineeService.findById(salesId);
    }

}
