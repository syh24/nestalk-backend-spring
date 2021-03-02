package com.doongji.nestalk.security;

import com.doongji.nestalk.entity.user.Role;
import com.doongji.nestalk.entity.user.User;
import com.doongji.nestalk.error.NotFoundException;
import com.doongji.nestalk.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@RequiredArgsConstructor
@Component
public class JwtProvider implements AuthenticationProvider {

    private final Jwt jwt;

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authentication;
        return processUserAuthentication(jwtToken.authenticationRequest());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtToken.class, authentication);
    }

    private Authentication processUserAuthentication(AuthenticationRequest request) {
        try {
            User user = userService.login(request.getEmail(), request.getPassword());
            JwtToken authenticated = new JwtToken(
                    new JwtAuthentication(user.getUserId(), user.getEmail()),
                    null,
                    createAuthorityList(Role.USER.value())
            );
            String token = user.createToken(jwt, new String[]{Role.USER.value()});
            authenticated.setDetails(new AuthenticationResult(token, user));
            return authenticated;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

}
