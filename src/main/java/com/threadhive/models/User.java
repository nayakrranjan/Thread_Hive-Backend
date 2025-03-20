package com.threadhive.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Username can only contain letters, numbers, periods and underscores")
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
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
