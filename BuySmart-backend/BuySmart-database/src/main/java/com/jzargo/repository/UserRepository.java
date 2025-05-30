package com.jzargo.repository;

import com.jzargo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User entity);

    Optional<User> findById(Long aLong);

    Optional<User> findById(long l);

    List<User> findAll();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String targetEmail);

    boolean existsByPassword(String password);

    User getUserByEmail(String email);


    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

}
