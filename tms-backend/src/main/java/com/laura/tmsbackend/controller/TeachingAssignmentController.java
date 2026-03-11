package com.laura.tmsbackend.controller;


import com.laura.tmsbackend.model.TeachingAssignment;
import com.laura.tmsbackend.model.Trainee;
import com.laura.tmsbackend.service.TeachingAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachingAssignment")
public class TeachingAssignmentController {
    @Autowired
    private TeachingAssignmentService teachingAssignmentService;

    @PostMapping
    public void insert(@RequestBody TeachingAssignment teachingAssignment){
        teachingAssignmentService.insert(teachingAssignment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        teachingAssignmentService.deleteById(id);
    }

    @PutMapping
    public void update(@RequestBody TeachingAssignment teachingAssignment){
        teachingAssignmentService.update(teachingAssignment);
    }

    @GetMapping
    public List<TeachingAssignment> findAll(){
        return teachingAssignmentService.findAll();
    }

    @GetMapping("/{id}")
    public TeachingAssignment findById(@PathVariable Long id){
        return teachingAssignmentService.findById(id);
    }
}
