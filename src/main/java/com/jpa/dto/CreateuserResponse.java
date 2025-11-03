package com.jpa.dto;

import lombok.Getter;

@Getter
public class CreateuserResponse {
    // 응답할때의 DTO

    // 속성 필드
    // Response객체는 마지막에 return되는 객체, 따라서 값이 바뀔 이유가 없으므로 final
    private final Long id;
    private final String name;
    private final String email;
    private final String address;

    // 생성자
    public CreateuserResponse(Long id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
