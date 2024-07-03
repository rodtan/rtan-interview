package com.rodtan.interview.service;

import com.rodtan.interview.model.Post;
import com.rodtan.interview.model.User;
import com.rodtan.interview.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostService postService = new PostServiceImpl();

    private User createUser() {
        User user = new User(1, "Rod", "Tan");
        return user;
    }
    public Post createPost() {
        User user = createUser();
        Post post = new Post(1, "title", "content", user, user.getId());
        return post;
    }

    private List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(createPost());
        return posts;
    }

    @Test
    public void canGetAllPosts() throws Exception {
        given(postRepository.findAll()).willReturn(getAllPosts());
        List<Post> result = postService.getAllPosts();
        assert(result.size() == getAllPosts().size());
    }

    @Test
    public void canGetPostById() throws Exception {
        Post inputPost = createPost();
        given(postRepository.findById(1)).willReturn(Optional.of(inputPost));
        Post result = postService.getPostById(1);
        assert(result == inputPost);
    }

    @Test
    public void canAddPost() throws Exception {
        Post inputPost = createPost();
        given(postRepository.save(inputPost)).willReturn(inputPost);
        Post result = postService.addPost(inputPost);
        assert(result == inputPost);
    }

    @Test
    public void canUpdatePost() throws Exception {
        Post inputPost = createPost();
        given(postRepository.findById(1)).willReturn(Optional.of(inputPost));
        given(postRepository.save(inputPost)).willReturn(inputPost);
        Post result = postService.updatePost(inputPost);
        assert(result == inputPost);
    }

    @Test
    public void canDeletePost() throws Exception {
        Post inputPost = createPost();
        given(postRepository.findById(1)).willReturn(Optional.of(inputPost));
        postService.deletePost(inputPost.getId());
        verify(postRepository, times(1)).deleteById(1);
    }
}
