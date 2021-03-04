package com.doongji.nestalk.entity.user;

import com.doongji.nestalk.entity.BaseTimeEntity;
import com.doongji.nestalk.security.Jwt;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
@Entity
@EqualsAndHashCode(of = "userId", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthday;

    public User(String email, String name, String password, String phone, LocalDate birthday) {
        this(null, email, name, password, phone, birthday);
    }

    public User(Long userId, String email, String name, String password, String phone, LocalDate birthday) {
        checkArgument(isNotEmpty(email), "email must be provided.");
        checkArgument(
                email.length() >= 4 && email.length() <= 50,
                "email length must be between 4 and 50 characters."
        );
        checkArgument(checkEmail(email), "Invalid email address: " + email);
        checkArgument(isNotEmpty(name), "name must be provided.");
        checkArgument(
                name.length() >= 1 && name.length() <= 10,
                "name length must be between 1 and 10 characters."
        );
        checkNotNull(password, "password must be provided.");
        checkNotNull(phone, "phone must be provided.");
        checkArgument(checkPhone(phone), "Invalid phone number: " + phone);
        checkNotNull(birthday, "birthday must be provided.");

        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.birthday = birthday;
    }

    private static boolean checkEmail(String email) {
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", email);
    }

    private static boolean checkPhone(String phone) {
        return matches("^\\d{3}-\\d{3,4}-\\d{4}$", phone);
    }

    public void login(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, password))
            throw new IllegalArgumentException("Bad credential");
    }

    public String createToken(Jwt jwt, String[] roles) {
        Jwt.Claims claims = Jwt.Claims.of(userId, email, roles);
        return jwt.newToken(claims);
    }

    public void changePassword (String password){
        this.password = password;
    }
}
