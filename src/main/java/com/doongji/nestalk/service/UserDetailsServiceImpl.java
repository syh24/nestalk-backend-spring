package com.doongji.nestalk.service;

import com.doongji.nestalk.enums.RoleType;
import com.doongji.nestalk.entity.User;
import com.doongji.nestalk.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User userEntity = userRepository.findByEmail(id).orElseThrow(()->new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        String roleName = userEntity.getRole().equalsIgnoreCase("admin") ? RoleType.ADMIN.name() : RoleType.USER.name();

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority role = new SimpleGrantedAuthority(roleName);
        authorities.add(role);

        return new org.springframework.security.core.userdetails.User(userEntity.getUserId(), userEntity.getPassword(), authorities);
    }
}
