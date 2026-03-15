package com.laura.tmsbackend.controller;

import com.laura.tmsbackend.model.Trainer;
import com.laura.tmsbackend.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/trainer")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @PostMapping
    public void insert(@RequestBody Trainer trainer){
        trainerService.insert(trainer);
    }
    @DeleteMapping("/{trainerId}")
    public void delete(@PathVariable Long trainerId){
        trainerService.deleteById(trainerId);
    }
    @PutMapping("/{trainerId}")
    public void update(@RequestBody Trainer trainer){
        trainerService.update(trainer);
    }
    @GetMapping("/{trainerId}")
    public Trainer findById(@PathVariable Long trainerId){
        return trainerService.findById(trainerId);
    }
    @GetMapping
    public List<Trainer> findAll(){
        return trainerService.findAll();
    }

}
