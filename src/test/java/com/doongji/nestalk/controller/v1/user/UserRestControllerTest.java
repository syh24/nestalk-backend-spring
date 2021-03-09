package com.doongji.nestalk.controller.v1.user;

import com.doongji.nestalk.controller.v1.user.dto.FindEmailRequest;
import com.doongji.nestalk.controller.v1.user.dto.FindEmailResponse;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith (SpringExtension.class)
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserRestControllerTest {


    @LocalServerPort
    private int port;

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
    void 아이디_찾기_성공테스트() throws Exception {
        User user = joinUser();
        FindEmailRequest request = new FindEmailRequest("test", "010-1234-5678");
        String url = "http://localhost:" + port + "/api/v1/user/email";
        MvcResult result = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).contains(user.getEmail());
    }

    @Test
    void 아이디_찾기_실패테스트() throws Exception {
        User user = joinUser();
        FindEmailRequest request = new FindEmailRequest("ttt", "010-1234-5678");
        String url = "http://localhost:" + port + "/api/v1/user/email";
        MvcResult result = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is5xxServerError())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).doesNotContain(user.getEmail());
    }


    public User joinUser(){
        return userService.join("abc@gmail.com","test","123456789","010-1234-5678", LocalDate.now());
    }

}