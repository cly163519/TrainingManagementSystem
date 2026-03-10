package com.laura.tmsbackend.mapper;

import com.laura.tmsbackend.model.TeachingAssignment;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeachingAssignmentMapper {
    void insert(TeachingAssignment teachingAssignment);
    void deleteById(Long id);
    void update(TeachingAssignment teachingAssignment);
    TeachingAssignment findById(Long id);
    List<TeachingAssignment> findAll();
}
