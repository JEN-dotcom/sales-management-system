package com.ingryd.sms.service;

import com.ingryd.sms.entity.Role;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.error.ObjectNotFoundException;
import com.ingryd.sms.repository.RoleRepository;
import com.ingryd.sms.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new ObjectNotFoundException("Role not found")));
        user.setAuthorities(roles);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User does not exist"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("User does not exist"));
    }

    @Override
    public User updateUser(Long id, User user) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User does not exist"));
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());

        return userRepository.save(updatedUser);
    }

    @Override
    public User getUserByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public ResponseEntity<String> deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("user successfully deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User does not exist"));
    }
}
