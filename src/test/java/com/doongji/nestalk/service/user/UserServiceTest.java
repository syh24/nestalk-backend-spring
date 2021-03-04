package com.doongji.nestalk.service.user;

import com.doongji.nestalk.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private String email;

    private String name;

    private String password;

    private String phone;

    private LocalDate birthday;

    @BeforeAll
    void setUp() {
        name = "둥지";
        email = "doongji.team@gmail.com";
        password = "P@ssword1";
        phone = "010-0000-0000";
        birthday = LocalDate.of(1995, 2, 19);
    }

    @Test
    @Order(1)
    void 사용자_회원가입() {
        User user = userService.join(email, name, password, phone, birthday);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).contains(name);
        assertThat(user.getPhone()).contains(phone);
        assertThat(user.getBirthday()).isEqualTo(birthday);
        log.info("User: {}", user);
    }

    @Test
    @Order(2)
    void 사용자_로그인() {
        User user = userService.login(email, password);
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).contains(name);
        assertThat(user.getPhone()).contains(phone);
        assertThat(user.getBirthday()).isEqualTo(birthday);
        log.info("Login User: {}", user);
    }

    @Test
    @Order(3)
    void 이메일로_사용자_조회() {
        User user = userService.findByEmail(email).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPhone()).contains(phone);
        assertThat(user.getBirthday()).isEqualTo(birthday);
        log.info("Found by {}: {}", email, user);
    }

    @Test
    @Order(4)
    void 비밀번호_변경 () throws Exception{
        String newPassword = "testPassword";
        User user = userService.findByEmail(email).orElse(null);
        userService.updatePassword(user.getUserId(),newPassword);
        User findUser = userService.login(user.getEmail(),newPassword);
        assertThat(findUser).isNotNull();
        assertThat(findUser.getUserId()).isEqualTo(1L);
        assertThat(findUser.getEmail()).isEqualTo(email);
        assertThat(findUser.getName()).isEqualTo(name);
        assertThat(findUser.getPhone()).contains(phone);
        assertThat(findUser.getBirthday()).isEqualTo(birthday);
        assertThat(passwordEncoder.matches(newPassword,findUser.getPassword())).isTrue();
        log.info("Update Password {}: {}", user.getPassword(), findUser.getPassword());
    }
}