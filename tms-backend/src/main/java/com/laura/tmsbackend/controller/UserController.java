package com.laura.tmsbackend.controller;

import com.laura.tmsbackend.mapper.UserMapper;
import com.laura.tmsbackend.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    // Login
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        User found = userMapper.findByUsername(user.getUsername());

        if (found == null || !found.getPassword().equals(user.getPassword())) {
            result.put("success", false);
            result.put("message", "Invalid username or password");
            return result;
        }

        session.setAttribute("userId", found.getId());
        session.setAttribute("username", found.getUsername());
        session.setAttribute("role", found.getRole());
        session.setAttribute("realName", found.getRealName());

        result.put("success", true);
        result.put("role", found.getRole());
        result.put("realName", found.getRealName());
        return result;
    }

    // Logout
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    // Get current user
    @GetMapping("/me")
    public Map<String, Object> me(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Object userId = session.getAttribute("userId");

        if (userId == null) {
            result.put("success", false);
            result.put("message", "Not logged in");
            return result;
        }

        result.put("success", true);
        result.put("userId", userId);
        result.put("username", session.getAttribute("username"));
        result.put("role", session.getAttribute("role"));
        result.put("realName", session.getAttribute("realName"));
        return result;
    }

    // List all users (ADMIN only)
    @GetMapping
    public List<User> findAll(HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return null;
        }
        return userMapper.findAll();
    }

    // Create user (ADMIN only)
    @PostMapping("/create")
    public void insert(@RequestBody User user, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return;
        }
        userMapper.insert(user);
    }

    // Update user (ADMIN only)
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody User user, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return;
        }
        user.setId(id);
        userMapper.update(user);
    }

    // Delete user (ADMIN only)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id, HttpSession session) {
        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return;
        }
        userMapper.deleteById(id);
    }
}