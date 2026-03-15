package com.laura.tmsbackend.controller;

import com.laura.tmsbackend.model.TrainingClass;
import com.laura.tmsbackend.service.TrainingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainingclass")
public class TrainingClassController {

        @Autowired
        private TrainingClassService trainingClassService;

        @PostMapping
        public void insert(@RequestBody TrainingClass trainingClass) {
            trainingClassService.insert(trainingClass);
        }

        @DeleteMapping("/{classNo}")
        public void deleteById(@PathVariable Long classNo) {
            trainingClassService.deleteById(classNo);
        }

        @PutMapping("/{classNo}")
        public void update(@RequestBody TrainingClass trainingClass) {
            trainingClassService.update(trainingClass);
        }

        @GetMapping("/{classNo}")
        public TrainingClass findById(@PathVariable Long classNo) {
            return trainingClassService.findById(classNo);
        }
        @GetMapping
        public List<TrainingClass> findAll() {
            return trainingClassService.findAll();
        }


    }
