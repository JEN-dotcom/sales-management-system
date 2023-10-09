package com.ingryd.sms.service;

import com.ingryd.sms.entity.User;
import com.ingryd.sms.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


// @ExtendWith(MockitoExtension.class)
// @RunWith(SpringRunner.class)
@SpringBootTest
// @DataJpaTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;



    @Test
    public void testCreateUser(){
        User user = new User(1L, "Efe", "Okorobie", "efe@gmail.com", "55757577573");

        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.createUser(user));
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(
                List.of(
                        new User(1L, "Efe", "Okorobie", "efe@gmail.com", "55757577573"),
                        new User(2L, "John", "Enyinwa", "john@gmail.com", "55757577574")));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserById() {
        long userId = 1L;
        User user = new User(1L, "Efe", "Okorobie", "efe@gmail.com", "55757577573");
        when(userRepository.findById(userId)).thenReturn(Optional.of((user)));

        User result = userService.getUserById(userId);
        assertEquals(userId, result.getId().longValue());
    }

    @Test
    public void testGetUserByFirstName(){
        String firstName = "Efe";
        User user = new User(1L, "Efe", "Okorobie", "efe@gmail.com", "55757577573");
        when(userRepository.findByFirstName(firstName)).thenReturn((user));

        User result = userService.getUserByFirstName(firstName);
        assertEquals(firstName, result.getFirstName());
    }

    @Test
    public void testGetUserByEmail(){
        String email = "efe@gmail.com";
        User user = new User(1L, "Efe", "Okorobie", "efe@gmail.com", "55757577573");
        when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.getUserByEmail(email);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        User user = new User(userId, "Efe", "Okorobie", "efe@gmail.com", "55757577573");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(userId, user);

        verify(userRepository, times(1)).save(user);

        assertEquals(user, updatedUser);
    }

    @Test
    public void testDeleteUser() {
        long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }



}

