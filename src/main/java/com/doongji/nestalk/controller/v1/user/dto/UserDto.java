package com.doongji.nestalk.controller.v1.user.dto;

import com.doongji.nestalk.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@ToString
public class UserDto {

    @ApiModelProperty(value = "PK", required = true)
    private Long userId;

    @ApiModelProperty(value = "이메일", required = true)
    private String email;

    @ApiModelProperty(value = "사용자명", required = true)
    private String name;

    @ApiModelProperty(value = "전화번호", required = true)
    private String phone;

    @ApiModelProperty(value = "생년월일", required = true)
    private LocalDate birthday;

    public UserDto(User source) {
        copyProperties(source, this);
    }

}