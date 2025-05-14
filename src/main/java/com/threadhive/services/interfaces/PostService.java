package com.threadhive.services.interfaces;

import com.threadhive.dtos.request.PostRequest;
import com.threadhive.dtos.response.PostResponse;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponse createPost(PostRequest post);
    PostResponse updatePost(PostRequest post);
    void deletePost(UUID postId);
    PostResponse getPostById(int id);
    List<PostResponse> getAllPosts();
    List<PostResponse> getSuggestedPosts(String username);
    List<PostResponse> getMyPosts(String username);
}
