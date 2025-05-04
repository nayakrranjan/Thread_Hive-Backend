package com.threadhive.services.impl;

import com.threadhive.models.Post;
import com.threadhive.services.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {



    @Override
    public Post createPost(Post post) {
        return null;
    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }

    @Override
    public void deletePost(Post post) {

    }

    @Override
    public Post getPostById(int id) {
        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        return List.of();
    }

    @Override
    public List<Post> getPostsForUser(String username) {
        return List.of();
    }

    @Override
    public List<Post> getPostsByUser(String username) {
        return List.of();
    }
}
