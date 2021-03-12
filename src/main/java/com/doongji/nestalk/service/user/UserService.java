package com.doongji.nestalk.service.user;

import com.doongji.nestalk.entity.user.User;
import com.doongji.nestalk.error.NotFoundException;
import com.doongji.nestalk.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User join(String email, String name, String password, String phone, LocalDate birthday) {
        checkNotNull(password, "password must be provided.");
        checkArgument(
                password.length() >= 8 && password.length() <= 15,
                "password length must be between 8 and 15 characters."
        );

        return update(
                new User(email, name, passwordEncoder.encode(password), phone, birthday)
        );
    }

    @Transactional
    public User login(String email, String password) {
        checkNotNull(password, "password must be provided.");

        User user = findByEmail(email)
                .orElseThrow(() -> new NotFoundException(User.class, email));
        user.login(passwordEncoder, password);
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private User update(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserByNameAndPhone(String name, String phone) {
        return userRepository.findByNameAndPhone(name, phone)
                .orElseThrow(() -> new NotFoundException(User.class, name, phone));
    }
}
