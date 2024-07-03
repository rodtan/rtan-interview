package com.rodtan.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodtan.interview.model.User;
import com.rodtan.interview.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private JacksonTester<User> UserJacksonTester;

    private JacksonTester<List<User>> UserListJacksonTester;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    private User createUser() {
        User user = new User(1, "Rod", "rodtest@example.com");
        user.setId(1);
        return user;
    }
    private List<User> getAllTestUser() {
        List<User> users = new ArrayList<>();
        users.add(createUser());
        return users;
    }

    @Test
    public void canGetAllUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(getAllTestUser());
        MockHttpServletResponse response = mvc.perform(get("/users")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                UserListJacksonTester.write(getAllTestUser()).getJson()
        );
    }

    @Test
    public void canGetUserById() throws Exception {
        given(userService.getUser(1)).willReturn(createUser());
        MockHttpServletResponse response = mvc.perform(get("/users/1")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                UserJacksonTester.write(createUser()).getJson()
        );
    }

    @Test
    public void returnsNotFoundIfUserNotFound() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(get("/users/id/1"))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void canSaveUser() throws Exception {
        User inputUser = createUser();
        given(userService.addUser(inputUser)).willReturn(inputUser);
        String requestJson = UserJacksonTester.write(inputUser).getJson();
        MockHttpServletResponse response = mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                UserJacksonTester.write(createUser()).getJson()
        );
    }

    @Test
    public void canUpdateUser() throws Exception {
        User updatedUser = new User(1, "RodTan", "rodtest@example.com");
        given(userService.updateUser(updatedUser)).willReturn(updatedUser);
        String requestJson = UserJacksonTester.write(updatedUser).getJson();
        MockHttpServletResponse response = mvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                UserJacksonTester.write(updatedUser).getJson()
        );
    }

    @Test
    public void canDeleteUser() throws Exception {
        MockHttpServletResponse response = mvc.perform(delete("/users/1"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}
