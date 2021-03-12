package com.doongji.nestalk.controller.v1.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public class FindEmailResponse {

    @ApiModelProperty(value = "사용자 이메일", required = true)
    private String email;

    public FindEmailResponse(String email) {
        checkNotNull(email, "email must be provided.");
        this.email = email;
    }
}
