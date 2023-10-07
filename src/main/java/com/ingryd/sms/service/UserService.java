package com.ingryd.sms.service;

import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<User> getAllUsers();


    public User getUserById(Long id);

    public User getUserByEmail(String email);


    public User getUserByFirstName(String firstName);

    public User createUser(User user);


    public User updateUser(Long id, User user);


    public ResponseEntity<String> deleteUser(Long id);

}
