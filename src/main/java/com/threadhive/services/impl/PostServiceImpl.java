package com.threadhive.services.impl;

import com.threadhive.dtos.request.PostRequest;
import com.threadhive.dtos.response.PostResponse;
import com.threadhive.models.Post;
import com.threadhive.models.User;
import com.threadhive.repositories.PostRepository;
import com.threadhive.repositories.UserRepository;
import com.threadhive.services.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            postResponses.add(new PostResponse(
                    post.getId(),
                    post.getUser().getUsername(),
                    post.getContent(),
                    post.getMediaUrl(),
                    post.getUser().getUsername(),
                    0, 0, 0
            ));
        }
        return postResponses;
    }

    @Override
    public PostResponse createPost(PostRequest post) {
        Optional<User> user = userRepository.findByUsername(post.getUsername());
        if (user.isEmpty()) throw new UsernameNotFoundException(post.getUsername());

        Post newPost = new Post();
        newPost.setUser(user.get());
        newPost.setContent(post.getContent());
        newPost.setMediaUrl(post.getMediaUrl());

        Post savedPost = postRepository.save(newPost);
        return new PostResponse(
                savedPost.getId(),
                savedPost.getUser().getUsername(),
                savedPost.getContent(),
                savedPost.getMediaUrl(),
                savedPost.getUser().getUsername(),
                0, 0, 0
        );
    }

    @Override
    public PostResponse updatePost(PostRequest post) {
        return null;
    }

    @Override
    public void deletePost(UUID postId) {}

    @Override
    public PostResponse getPostById(int id) {
        return null;
    }

    @Override
    public List<PostResponse> getSuggestedPosts(String username) {
        return List.of();
    }

    @Override
    public List<PostResponse> getMyPosts(String username) {
        return List.of();
    }
}
