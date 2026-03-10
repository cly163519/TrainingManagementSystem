package com.laura.tmsbackend.mapper;

import com.laura.tmsbackend.model.Trainer;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrainerMapper {
    void insert(Trainer trainer);
    void deleteById(Long trainerId);
    void update(Trainer trainer);
    List<Trainer> findAll();
    Trainer findById(Long trainerId);
}
