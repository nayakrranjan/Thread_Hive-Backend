package com.threadhive.dtos;

import java.util.UUID;

import lombok.*;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String name;
    private String profilePhoto;
    private String backGroundPhoto;
    // private Timestamp createdDate;
    // private Timestamp lastModifiedDate;
}
