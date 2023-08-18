package com.nab5m.userservice.service;

import com.nab5m.userservice.entity.User;
import com.nab5m.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    @NonNull
    public void createUser(User user) {
        userRepository.saveUser(user);
    }
}
