package com.ingryd.sms.repository;

import com.ingryd.sms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    User findUserByEmail(String email);
    void deleteById(Long id);
    User findUserByFirstName(String firstName);

}
