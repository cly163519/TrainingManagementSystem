package com.laura.tmsbackend.service;

import com.laura.tmsbackend.mapper.UserMapper;
import com.laura.tmsbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String username, String password){
        User user = userMapper.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found!");
        }
        if(!user.getPassword().equals(password)){
            throw new RuntimeException("Wrong password!");
        }
        return user;
    }

   public void insert(User user){
        userMapper.insert(user);
    }

    public void deleteById(Long id){
        userMapper.deleteById(id);
    }

    public void update(User user){
        userMapper.update(user);
    }

    public List<User> findAll(){
        return userMapper.findAll();
    }


}
