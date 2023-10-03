package com.ingryd.sms.service;

import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(long id);

    public ResponseEntity<User> GetUserByEmail(String email);


    public User GetUserByFirstName(String firstName);

    public User createUser(UserDTO userDTO);

    public User updateUser(long id, UserDTO userDTO);

    public User createUser(User user);

    public ResponseEntity<String> deleteUser(long id);

}
