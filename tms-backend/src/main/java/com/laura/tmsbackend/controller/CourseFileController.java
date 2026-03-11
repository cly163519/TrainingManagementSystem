package com.laura.tmsbackend.controller;

import com.laura.tmsbackend.model.CourseFile;
import com.laura.tmsbackend.model.Trainee;
import com.laura.tmsbackend.service.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coursefile")
public class CourseFileController {
    @Autowired
    private CourseFileService courseFileService;

    @PostMapping
    public void insert(@RequestBody CourseFile courseFile){
        courseFileService.insert(courseFile);
    }

    @DeleteMapping("/{courseFileId}")
    public void deleteById(@PathVariable Long courseFileId){
        courseFileService.deleteById(courseFileId);
    }

    @PutMapping
    public void update(@RequestBody CourseFile courseFile){
        courseFileService.update(courseFile);
    }

    @GetMapping
    public List<CourseFile> findAll(){
        return courseFileService.findAll();
    }

    @GetMapping("/{courseFileId}")
    public CourseFile findById(@PathVariable Long courseFileId){
        return courseFileService.findById(courseFileId);
    }
}
