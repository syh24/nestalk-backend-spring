package com.doongji.nestalk.repository.user;

import com.doongji.nestalk.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNameAndPhone(String name, String phone);
}
