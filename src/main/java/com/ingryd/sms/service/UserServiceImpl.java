package com.ingryd.sms.service;

import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.UserDTO;
import com.ingryd.sms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(UserDTO userDTO){
        return userRepository.save(new User());
    }

    @Override
    @Transactional
    public User getUserById(Long id){
        return userRepository.findUserById(id);

    }

    @Override
    @Transactional
    public ResponseEntity<User> GetUserByEmail(String email){
        return new ResponseEntity<>(userRepository.findUserByEmail(email), HttpStatus.OK);
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserDTO userDTO){
        User updatedUser = userRepository.findUserById(id);
        updatedUser.setFirstName(userDTO.getFirstName());
        updatedUser.setLastName(userDTO.getLastName());
        updatedUser.setEmail(userDTO.getEmail());

        userRepository.save(updatedUser);
        return updatedUser;

    }


    @Override
    @Transactional
    public User GetUserByFirstName(String firstName){
        return userRepository.findUserByFirstName(firstName);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteUser(Long id){
        userRepository.deleteById(id);
        return ResponseEntity.ok("user successfully deleted");
    }


}
