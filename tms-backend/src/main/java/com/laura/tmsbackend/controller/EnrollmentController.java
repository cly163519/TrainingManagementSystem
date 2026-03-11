package com.laura.tmsbackend.controller;

import com.laura.tmsbackend.model.Enrollment;
import com.laura.tmsbackend.model.Trainee;
import com.laura.tmsbackend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    public void insert(@RequestBody Enrollment enrollment){
        enrollmentService.insert(enrollment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        enrollmentService.deleteById(id);
    }

    @PutMapping
    public void update(@RequestBody Enrollment enrollment){
        enrollmentService.update(enrollment);
    }

    @GetMapping("/{id}")
    public Enrollment findById(@PathVariable Long id){
        return enrollmentService.findById(id);
    }
    @GetMapping
    public List<Enrollment> findAll(){
        return enrollmentService.findAll();
    }


}
