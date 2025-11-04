package com.jpa.service;

import com.jpa.dto.*;
import com.jpa.entity.User;
import com.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    // 3-Layer-Architecture의 컨트롤러-서비스-레포지토리 -> 레포지토리에 해당, 하위에 레포지토리를 포함한다
    // 상위 레이어가 하위 레이어를 가져와야함
    private final UserRepository userRepository;

    // 저장
    @Transactional
    // 읽기 전용이 아니므로 public, User라는 Entity를 그대로 반환해서는 안되므로 CreateuserResponse를 return,
    public CreateuserResponse save(CreateUserRequest request) {
        // CreateUserRequest 클래스의 속성 필드에 맞춰서 객체를 생성
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getAddress()
        );

        User savedUser = userRepository.save(user); // request가 아닌 user를 save
        // return savedUser; <--- 안됨! User라는 Entity는 Controller로 가면 안된다!
        // 따라서, savedUser를 CreateUserResponse타입으로 형변환 후 반환해야 함
//        CreateuserResponse response = new CreateuserResponse(
//                savedUser.getId(),
//                savedUser.getName(),
//                savedUser.getEmail(),
//                savedUser.getAddress()
//        );
//        return response;
        // 아래가 간결화된 반환
        return new CreateuserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getAddress(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public GetOneUserResponse getOne(Long userId) {
        // Optional<User> 이므로, Null이 나올수도 있음
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

//        GetOneUserResponse getOneUserResponse = new GetOneUserResponse(
//                user.getId(),
//                user.getName(),
//                user.getEmail(),
//                user.getAddress()
//        );
//        return getOneUserResponse;
        return new GetOneUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAddress()
        );
    }

    // 다건 조회
    @Transactional(readOnly = true)
    public List<GetOneUserResponse> getAll() {
        List<User> users = userRepository.findAll();

        // 현 메서드의 반환 타입은 List<GetOneUserResponse>, List<User> users의 리스트 타입을 바꿔야한다

        List<GetOneUserResponse> dtos = new ArrayList<>();

        // 반복문으로 [List<User> users]를 순회하면서 User user의 요소들에 접근하고,
        // 각각 GetOneUserResponse dto 객체에 답는다
        for (User user : users) {
            GetOneUserResponse dto = new GetOneUserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress()
            );
            // GetOneUserResponse dto 들을 같은 타입의 리스트 dtos에 넣는다
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        // 더티채킹 - Transaction 안에서 DB를 갔다온 영속 객체를 자동으로 업데이트

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );
        user.update(
                request.getName(),
                request.getEmail(),
                request.getAddress()
        );
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAddress()
        );
    }

    @Transactional
    public void deleteById(Long userId) {
        boolean exists = userRepository.existsById(userId);
        // 유저가 존재하지 않는 경우
        if (!exists) {
            throw new IllegalStateException("없는 유저입니다.");
        }

        // 유저가 있는 경우 -> 삭제 가능!
        userRepository.deleteById(userId);

    }

}
