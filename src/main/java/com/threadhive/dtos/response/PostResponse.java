package com.threadhive.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private UUID postId;
    private String username;
    private String content;
    private String mediaUrl;
    private String user;
    private int likeCount;
    private int commentCount;
    private int dislikeCount;
}
