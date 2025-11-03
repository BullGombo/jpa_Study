package com.jpa.dto;

import com.jpa.repository.UserRepository;
import lombok.Getter;

@Getter
public class UpdateUserResponse {

    // Response라서 final
    private final Long id;
    private final String name;
    private final String email;
    private final String password;

    public UpdateUserResponse(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
