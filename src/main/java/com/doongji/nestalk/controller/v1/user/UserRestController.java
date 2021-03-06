package com.doongji.nestalk.controller.v1.user;

import com.doongji.nestalk.controller.v1.user.dto.JoinRequest;
import com.doongji.nestalk.controller.v1.user.dto.JoinResult;
import com.doongji.nestalk.controller.v1.user.dto.UserDto;
import com.doongji.nestalk.entity.user.Role;
import com.doongji.nestalk.entity.user.User;
import com.doongji.nestalk.security.Jwt;
import com.doongji.nestalk.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "사용자 APIs")
@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    private final Jwt jwt;

    @ApiOperation(value = "사용자 등록 (JWT 불필요)")
    @PostMapping(path = "user/join")
    public ResponseEntity<JoinResult> join(@RequestBody JoinRequest joinRequest) {
        User user = userService.join(
                joinRequest.getEmail(),
                joinRequest.getName(),
                joinRequest.getPassword(),
                joinRequest.getPhone(),
                joinRequest.getBirthday()
        );

        String token = user.createToken(jwt, new String[]{Role.USER.value()});
        return ResponseEntity.ok(new JoinResult(token, new UserDto(user)));
    }

    @ApiOperation(value = "이메일 중복확인 (JWT 불필요)")
    @PostMapping(path = "user/exists")
    public ResponseEntity<Boolean> checkEmail(
            @RequestBody @ApiParam(value = "example: {\"email\": \"doongji.team@gmail.com\"}") Map<String, String> request
    ) {
        String email = request.get("email");
        return ResponseEntity.ok(
                userService.findByEmail(email).isPresent()
        );
    }

}
