package com.nab5m.userservice.repository;

import com.nab5m.userservice.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final JPAQueryFactory queryFactory;

    @Override
    @NonNull
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUserByUserId(Long userId) {
        return null;
    }
}
