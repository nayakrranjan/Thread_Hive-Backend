package com.threadhive.dtos;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String name;
    private String profilePhoto;
    private String backGroundPhoto;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
}
