package com.rodtan.interview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodtan.interview.model.Post;
import com.rodtan.interview.model.User;
import com.rodtan.interview.service.PostService;
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
public class PostControllerTest {
    private MockMvc mvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private JacksonTester<Post> PostJacksonTester;

    private JacksonTester<List<Post>> PostListJacksonTester;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(postController)
                .build();
    }

    private User createUser() {
        User user = new User(1, "Rod", "Tan");
        return user;
    }
    private Post createPost() {
        User user = createUser();
        Post post = new Post(1, "Title", "Content", user, user.getId());
        return post;
    }
    private List<Post> getAllTestPost() {
        List<Post> posts = new ArrayList<>();
        posts.add(createPost());
        return posts;
    }

    @Test
    public void canGetAllPosts() throws Exception {
        given(postService.getAllPosts()).willReturn(getAllTestPost());
        MockHttpServletResponse response = mvc.perform(get("/posts")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                PostListJacksonTester.write(getAllTestPost()).getJson()
        );
    }

    @Test
    public void canGetPostById() throws Exception {
        given(postService.getPostById(1)).willReturn(createPost());
        MockHttpServletResponse response = mvc.perform(get("/posts/1")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                PostJacksonTester.write(createPost()).getJson()
        );
    }

    @Test
    public void returnsNotFoundIfPostNotFound() throws Exception {
        MockHttpServletResponse response = mvc
                .perform(get("/posts/id/1"))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }



}
