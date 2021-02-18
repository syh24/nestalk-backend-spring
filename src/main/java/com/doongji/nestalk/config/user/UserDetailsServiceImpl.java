package com.doongji.nestalk.config.user;

import com.doongji.nestalk.vo.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @PersistenceContext
    private EntityManager em; // JPA

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        List<UserVo> findUsers = em
                .createQuery("select v from UserVo v where v.userId = :userId", UserVo.class)
                .setParameter("userId", id)
                .getResultList();

        if (findUsers.size() == 0) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        UserVo userEntity  = findUsers.get(0);
        String roleName = userEntity.getRole().equalsIgnoreCase("admin") ? "ADMIN" : "USER";

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority role = new SimpleGrantedAuthority(roleName);
        authorities.add(role);

        return new User(userEntity.getUserId(), userEntity.getPassword(), authorities);

    }
}
