package com.threadhive.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "dislikes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dislike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // Reference to Post entity

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Reference to User entity
}
