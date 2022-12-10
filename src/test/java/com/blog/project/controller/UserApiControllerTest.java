package com.blog.project.controller;

import com.blog.project.ControllerUnitTest;
import com.blog.project.config.auth.PrincipalDetailService;
import com.blog.project.config.oauth.PrincipalOauth2UserService;
import com.blog.project.model.User;
import com.blog.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;



@WebMvcTest(UserApiControllerTest.class)
public class UserApiControllerTest {

    private final Logger log = LoggerFactory.getLogger(ControllerUnitTest.class);

    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private PrincipalOauth2UserService principalOauth2UserService;
    @MockBean
    private PrincipalDetailService principalDetailService;


    @Test
    @DisplayName("id중복 체크 ")
    public void idCheck(){

        Object obj=userService.userSelect("username");

        assertEquals(null,obj);

    }
}
