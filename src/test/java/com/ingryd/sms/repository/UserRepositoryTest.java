package com.ingryd.sms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ingryd.sms.entity.User;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;



    @Test
    public void saveUserTest() {
        User user1 = User.builder()
                .firstName("Efe")
                .lastName("Okorobie")
                .email("eokoro@gmail.com")
                .build();

        userRepository.save(user1);
    }

    @Test
    public void findById() {
        User user1 = User.builder()
                .firstName("Efe")
                .lastName("Okorobie")
                .email("eokoro@gmail.com")
                .build();

        user1= entityManager.persistAndFlush(user1);        

        User found = userRepository.findById(user1.getId()).get();
        assertEquals(user1.getId(), found.getId());
    }

    @Test
    public void findByFirstName() {
        User user1 = User.builder()
                .firstName("Efe")
                .lastName("Okorobie")
                .email("eokoro@gmail.com")
                .build();

        user1= entityManager.persistAndFlush(user1);        

        User found = userRepository.findByFirstName(user1.getFirstName());
        assertEquals("Efe", found.getFirstName());
    }

     @Test
    public void findByEmail() {
        User user1 = User.builder()
                .firstName("Efe")
                .lastName("Okorobie")
                .email("eokoro@gmail.com")
                .build();

        user1= entityManager.persistAndFlush(user1);        

        User found = userRepository.findByEmail(user1.getEmail());
        assertEquals("eokoro@gmail.com", found.getEmail());
    }

    @Test
    public void findAll() {
        User user1 = User.builder()
                .firstName("Efe")
                .lastName("Okorobie")
                .email("eokoro@gmail.com")
                .build();

        User user2 = User.builder()
                .firstName("John")
                .lastName("Enyinwa")
                .email("jen@gmail.com")
                .build();

        entityManager.persist(user1);
        entityManager.persist(user2);

        List<User> found = userRepository.findAll();
        assertEquals(2, found.size());
    }

    @Test
    public void deleteById() {
        User user1 = User.builder()
                .firstName("Efe")
                .lastName("Okorobie")
                .email("eokoro@gmail.com")
                .build();

        User user2 = User.builder()
                .firstName("John")
                .lastName("Enyinwa")
                .email("jen@gmail.com")
                .build();

        entityManager.persist(user1);
        entityManager.persist(user2);

        List<User> found = userRepository.findAll();
        Long id = found.get(0).getId();

        userRepository.deleteById(id);

        Optional<User> deletedUser = userRepository.findById(id);
        assertTrue(deletedUser.isEmpty());
    }
}
