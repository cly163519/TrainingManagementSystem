package com.laura.tmsbackend.service;

import com.laura.tmsbackend.mapper.TeachingAssignmentMapper;

import com.laura.tmsbackend.model.TeachingAssignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service

public class TeachingAssignmentService {
    @Autowired
    private TeachingAssignmentMapper teachingAssignmentMapper;

    @PostMapping
    public void insert(@RequestBody TeachingAssignment teachingAssignment){//RequestBody means change JSON from frontend to java object
        teachingAssignmentMapper.insert(teachingAssignment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        teachingAssignmentMapper.deleteById(id);
    }

    @PutMapping
    public void update(@RequestBody TeachingAssignment teachingAssignment){
        teachingAssignmentMapper.update(teachingAssignment);
    }

    @GetMapping("/{id}")
    public TeachingAssignment findById(@PathVariable Long id){
        return teachingAssignmentMapper.findById(id);
    }

    @GetMapping
    public List<TeachingAssignment> findAll(){
        return teachingAssignmentMapper.findAll();
    }
}
