package com.doongji.nestalk.controller.v1.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequest {

    @ApiModelProperty(value = "로그인 이메일", required = true)
    private String email;

    @ApiModelProperty(value = "이름", required = true)
    private String name;

    @ApiModelProperty(value = "로그인 비밀번호", required = true)
    private String password;

    @ApiModelProperty(value = "전화번호", required = true)
    private String phone;

    @ApiModelProperty(value = "생년월일", required = true)
    private LocalDate birthday;

}