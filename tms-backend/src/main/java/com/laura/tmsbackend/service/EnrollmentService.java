package com.laura.tmsbackend.service;

import com.laura.tmsbackend.mapper.EnrollmentMapper;

import com.laura.tmsbackend.model.Enrollment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    @Autowired

    private EnrollmentMapper enrollmentMapper;

    public void insert(Enrollment enrollment){
        enrollmentMapper.insert(enrollment);
    }

    public void deleteById(Long id){
        enrollmentMapper.deleteById(id);
    }

    public void update(Enrollment enrollment){
        enrollmentMapper.update(enrollment);
    }

    public Enrollment findById(Long id){
        return enrollmentMapper.findById(id);
    }

    public List<Enrollment> findAll(){
        return enrollmentMapper.findAll();
    }
}
