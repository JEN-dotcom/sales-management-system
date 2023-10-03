package com.ingryd.sms.repository;

import com.ingryd.sms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(long id);
    Optional<User> findUserByEmail(String email);

}
