package com.jpa.controller;

import com.jpa.dto.*;
import com.jpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 컨트롤러는 User라는 엔티티를 알아서는 안됨! 따라서 Service의 메서드의 파라미터는 Entity가 아닌 DTO여야하며,
    // return 타입 또한 DTO여야함
    @PostMapping("/users")
    // ResponseEntity.status(HttpSatus.상태).body(응답데이터);
    // CreateuserResponse(DTO)를 return한다           CreateUserRequest(DTO)를 받아서 처리한다
    public ResponseEntity<CreateuserResponse> createUser(@RequestBody CreateUserRequest request) {
        CreateuserResponse result = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/users/{userId}")  // URL에서 매개변수를 따옴
    public ResponseEntity<GetOneUserResponse> getOneUser(@PathVariable Long userId) {
        GetOneUserResponse result = userService.getOne(userId);  // getOne() 메서드의 타입이 이미 GetOneUserResponse임.
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetOneUserResponse>> getAllUsers() { // 파라미터가 필요없음
        List<GetOneUserResponse> result = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/users/{userId}")  //       업데이트 대상 유저,                업데이트 내용
    public ResponseEntity<UpdateUserResponse> update(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        UpdateUserResponse result = userService.update(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) { // 반환 값 없음
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
