package com.doongji.nestalk.controller.v1.user;

import com.doongji.nestalk.controller.v1.user.dto.JoinRequest;
import com.doongji.nestalk.controller.v1.user.dto.JoinResult;
import com.doongji.nestalk.controller.v1.user.dto.UserDto;
import com.doongji.nestalk.email.EmailService;
import com.doongji.nestalk.entity.user.Role;
import com.doongji.nestalk.entity.user.User;
import com.doongji.nestalk.security.Jwt;
import com.doongji.nestalk.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@Api(tags = "사용자 APIs")
@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;
    private final EmailService emailService;
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

    @ApiOperation(value = "비밀번호를 찾기 위한 회원 인증(jwt 불필요)")
    @PostMapping(path = "user/password")
    public ResponseEntity<?> verifyEmail (
            @RequestBody @ApiParam(value = "example: {\"email\": \"doongji.team@gmail.com\"}", required = true) String email){
        try {
            User user = userService.findByEmail(email).orElseThrow(() -> new NoSuchElementException());
            String temporaryPassword = emailService.sendTemporaryPassword(user);
            userService.updatePassword(user.getUserId(), temporaryPassword);
        } catch (Exception e){
            return ResponseEntity.ok("메일을 발송하는데 실패하였습니다.");
        }
        return ResponseEntity.ok("메일을 성공적으로 발송하였습니다.");
    }
}
