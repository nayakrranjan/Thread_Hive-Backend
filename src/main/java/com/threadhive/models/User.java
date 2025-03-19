package com.threadhive.models;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "users")
@Getter @Setter  // Lombok annotations to generate getters and setters
@NoArgsConstructor @AllArgsConstructor @ToString
public class User {

    @Id  // Marks as Primary Key
    @GeneratedValue(strategy = GenerationType.AUTO)  // Auto-generates UUID
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)  // Ensures unique usernames
    private String username;

    @Column(nullable = false, unique = true)  // Ensures unique emails
    private String email;

    @Column(nullable = false)  // Password should not be null
    private String password;

    @Column(nullable = false, length = 100)  // Name should not be null, max 100 chars
    private String name;

    @Column(length = 500)  // Stores image URL (profile photo)
    private String profilePhoto;

    @Column(length = 500)  // Stores image URL (background photo)
    private String backGroundPhoto;

    @Column(updatable = false)  // Timestamp should not be updated after creation
    private Timestamp createdDate;

    @Column(insertable = false)  // Auto-updates on modification
    private Timestamp lastModifiedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedDate = new Timestamp(System.currentTimeMillis());
    }
}
