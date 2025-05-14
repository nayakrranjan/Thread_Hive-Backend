package com.threadhive.controllers.v1;

import com.threadhive.dtos.request.PostRequest;
import com.threadhive.dtos.response.PostResponse;
import com.threadhive.services.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @GetMapping("/getAll")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        var posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        var post = postService.createPost(postRequest);
        return ResponseEntity.ok(post);
    }
}
