package com.jpa.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    // User 객체를 생성할때의 Request DTO

    // final이 없는 필드를 입력
    private String name;
    private String email;
    private String address;

}
