package com.threadhive.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "follows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;  // The user who is following

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following;  // The user being followed
}

