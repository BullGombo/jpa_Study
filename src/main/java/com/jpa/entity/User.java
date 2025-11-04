package com.jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    // 속성 필드
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String address;

    // 생성자
    public User(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    // 메서드
    public void update(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

}
