package com.jpa.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

//    private Long id;  <--- DB에서 자동으로 생성하는 부분임
    private String name;
    private String email;
    private String address;

}
