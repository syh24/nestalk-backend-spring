package com.doongji.nestalk.config;

import com.doongji.nestalk.service.UserDetailsServiceImpl;
import com.doongji.nestalk.enums.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   //인증객체 생성
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
   }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .antMatchers("/", "/login").permitAll()
               .antMatchers("/admin/**").hasRole(RoleType.ADMIN.name())
               .anyRequest().authenticated()
               .and()
           .formLogin()
               .loginPage("/login") //접근제어 페이지 호출시, 권한이 없을 경우 로그인페이지로 이동
               .defaultSuccessUrl("/user/login/result")
               .permitAll()
               .and()
           .logout()
               .permitAll();
   }

}
