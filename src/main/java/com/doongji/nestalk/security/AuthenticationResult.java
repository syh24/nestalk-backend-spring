package com.doongji.nestalk.security;

import com.doongji.nestalk.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@ToString
public class AuthenticationResult {

    @ApiModelProperty(value = "API 토큰", required = true)
    private final String token;

    @ApiModelProperty(value = "사용자 정보", required = true)
    private final User user;

    public AuthenticationResult(String token, User user) {
        checkNotNull(token, "token must be provided.");
        checkNotNull(user, "user must be provided.");

        this.token = token;
        this.user = user;
    }

}