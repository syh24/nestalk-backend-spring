package com.doongji.nestalk.controller.v1.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FindEmailResponse {

    @ApiModelProperty (value = "사용자 이메일", required = true)
    private String email;
}
