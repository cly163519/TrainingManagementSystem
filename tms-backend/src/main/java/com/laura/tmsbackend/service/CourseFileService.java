package com.laura.tmsbackend.service;

import com.laura.tmsbackend.mapper.CourseFileMapper;

import com.laura.tmsbackend.model.CourseFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseFileService {
    @Autowired
    private CourseFileMapper courseFileMapper;

    public void insert(CourseFile courseFile){
        courseFileMapper.insert(courseFile);
    }

    public void deleteById(Long courseFileId){
        courseFileMapper.deleteById(courseFileId);
    }

    public void update(CourseFile courseFile){
        courseFileMapper.update(courseFile);
    }

    public CourseFile findById(Long courseFileId){
        return courseFileMapper.findById(courseFileId);
    }

    public List<CourseFile> findAll(){
        return courseFileMapper.findAll();
    }
}
