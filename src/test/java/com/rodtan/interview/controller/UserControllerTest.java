package com.rodtan.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodtan.interview.model.User;
import com.rodtan.interview.model.UserNotFoundException;
import com.rodtan.interview.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private JacksonTester<User> UserJacksonTester;

    private JacksonTester<List<User>> UserListJacksonTester;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    public User createUser() {
        User user = new User("Rod", "rodtest@example.com");
        user.setId(1);
        return user;
    }
    @Test
    public void canGetAllUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(getAllTestUser());

        MockHttpServletResponse response = mvc.perform(get("/api/users")).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                UserListJacksonTester.write(getAllTestUser()).getJson()
        );
    }

    @Test
    public void canGetUserById() throws Exception {
        given(userService.getUser(1)).willReturn(createUser());

        MockHttpServletResponse response = mvc.perform(get("/api/users/1")).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                UserJacksonTester.write(createUser()).getJson()
        );
    }

    @Test
    public void returnsNotFoundIfUserNotFound() throws Exception {
        given(userService.getUser(1)).willThrow(new UserNotFoundException("invalid id"));

        MockHttpServletResponse response = mvc
                .perform(get("/api/users/id/1"))
                .andReturn()
                .getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private List<User> getAllTestUser() {
        List<User> users = new ArrayList<>();
        users.add(createUser());
        return users;
    }

}
