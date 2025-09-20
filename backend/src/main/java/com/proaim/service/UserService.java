package com.proaim.service;

import com.proaim.dto.RegisterRequest;
import com.proaim.entity.User;

import java.util.List;

public interface UserService {
    
    User createUser(RegisterRequest registerRequest);
    
    List<User> getAllUsers();
    
    User getUserById(Long id);
    
    User updateUser(Long id, User user);
    
    void deleteUser(Long id);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
