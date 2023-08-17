package com.nab5m.userservice.service;

import com.nab5m.userservice.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    public void createUser(User user);
    public User findUserByUserId(Long userId);

}
