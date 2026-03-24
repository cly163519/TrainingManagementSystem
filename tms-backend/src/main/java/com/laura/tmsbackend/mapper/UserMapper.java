package com.laura.tmsbackend.mapper;

import com.laura.tmsbackend.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    void insert(User user);
    void deleteById(Long id);
    void update(User user);
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);


}
