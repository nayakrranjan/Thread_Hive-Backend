package com.threadhive.services.interfaces;

import com.threadhive.models.Post;

import java.util.List;

public interface PostService {
    public Post createPost(Post post);
    public Post updatePost(Post post);
    public void deletePost(Post post);
    public Post getPostById(int id);
    public List<Post> getAllPosts();
    public List<Post> getPostsForUser(String username);
    public List<Post> getPostsByUser(String username);
}
