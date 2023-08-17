package com.nab5m.userservice.repository;

import com.nab5m.userservice.entity.User;

public interface UserRepository {
    public void saveUser(User user);
    public User findUserByUserId(Long userId);
}