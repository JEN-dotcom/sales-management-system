package com.ingryd.sms.service;

import com.ingryd.sms.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User createUser(User user);

    public User getUserByEmail(String email);

    public User updateUser(Long id, User user);

    public User getUserByFirstName(String firstName);

    public ResponseEntity<String> deleteUser(Long id);
}
