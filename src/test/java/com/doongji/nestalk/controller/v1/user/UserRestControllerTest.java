package com.doongji.nestalk.controller.v1.user;

import com.doongji.nestalk.entity.user.User;
import com.doongji.nestalk.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserRestControllerTest {

    @LocalServerPort private int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void before() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void 임시비밀번호_발급_성공테스트 () throws Exception{
        User user = joinUserSuccess();
        String url = "http://localhost:" + port + "/api/v1/user/password";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("메일을 성공적으로 발송하였습니다.");

    }

    @Test
    void 임시비밀번호_발급_실패테스트 () throws Exception {
        User user = joinUserFail();
        String url = "http://localhost:" + port + "/api/v1/user/password";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content("123@gmail.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("메일을 발송하는데 실패하였습니다.");

    }

    public User joinUserSuccess()  {
        return userService.join("abc@gmail.com","syh","123456789","010-1234-5678",LocalDate.now());

    }
    public User joinUserFail()  {
        return userService.join("abc@gmail.com","syh","123456789","010-1234-5678",LocalDate.now());
    }
}