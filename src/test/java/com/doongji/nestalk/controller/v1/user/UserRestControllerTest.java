package com.doongji.nestalk.controller.v1.user;

import com.doongji.nestalk.controller.v1.user.dto.FindEmailRequest;
import com.doongji.nestalk.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Slf4j
class UserRestControllerTest {

    @InjectMocks
    private UserRestController userRestController;

    @Mock
    private UserService userService;


    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(userRestController)
                .build();
    }

    @Test
    @DisplayName("아이디 찾기 테스트")
    void find() throws Exception {
        String expectEmail = "test@gmail.com";
        FindEmailRequest request = new FindEmailRequest("test", "010-1234-5678");
        given(userService.findUserByNameAndPhone(ArgumentMatchers.any(),ArgumentMatchers.any())).willReturn(expectEmail);
        final ResultActions actions = mvc.perform(post("/api/v1/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(expectEmail));
    }

}