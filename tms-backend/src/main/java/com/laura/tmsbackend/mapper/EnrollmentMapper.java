package com.laura.tmsbackend.mapper;

import com.laura.tmsbackend.model.Enrollment;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnrollmentMapper {
    void insert(Enrollment enrollMent);
    void deleteById(Long id);
    void update(Enrollment enrollMent);
    Enrollment findById(Long id);
    List<Enrollment> findAll();
}
