package com.rodtan.interview.service;

import com.rodtan.interview.model.Post;
import com.rodtan.interview.model.PostNotFoundException;
import com.rodtan.interview.model.User;
import com.rodtan.interview.model.UserNotFoundException;
import com.rodtan.interview.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    UserService userService;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("post not found"));
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        Post oldPost = getPostById(post.getId());
        if (oldPost == null) {
            throw new PostNotFoundException("post not found");
        }
        if (post.getUserId() != null) {
            User user = userService.getUser(post.getUserId());
            if (user == null) {
                throw new UserNotFoundException("user not found");
            }
        }

        return postRepository.save(post);
    }

    @Override
    public void deletePost(int id) {
        Post post = getPostById(id);
        if (post == null) {
            throw new PostNotFoundException("post not found");
        } else {
            postRepository.delete(post);
        }
    }
}
