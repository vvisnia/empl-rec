package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveOrUpdateUserData(String login) {
        return userRepository.findByLogin(login)
                .map(user -> userRepository.save(User.builder().login(login).requestCount(user.getRequestCount() + 1).build()))
                .orElseGet(() -> userRepository.save(User.builder().login(login).requestCount(1).build()));
    }
}
