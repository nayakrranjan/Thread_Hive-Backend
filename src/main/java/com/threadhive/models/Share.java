package com.threadhive.models;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "shares")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // Reference to Post entity

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Reference to User entity

    @Column(name = "shared_at", nullable = false, updatable = false)
    private Timestamp sharedAt;

    @PrePersist
    protected void onShare() {
        this.sharedAt = new Timestamp(System.currentTimeMillis());
    }
}
