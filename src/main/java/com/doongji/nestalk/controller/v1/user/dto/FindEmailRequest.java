package com.doongji.nestalk.controller.v1.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FindEmailRequest {

    @ApiModelProperty (value = "사용자 이름", required = true)
    private String name;

    @ApiModelProperty(value = "사용자 핸드폰 번호", required = true)
    private String phone;
}
