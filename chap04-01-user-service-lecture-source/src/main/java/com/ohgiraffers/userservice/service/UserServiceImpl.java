package com.ohgiraffers.userservice.service;

import com.ohgiraffers.userservice.controller.UserService;
import com.ohgiraffers.userservice.dto.UserDTO;
import com.ohgiraffers.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registUser(UserDTO userDTO){
        /* 설명. 회원 가입 할 때 고유 번호 할당*/
        userDTO.setUserId(UUID.randomUUID().toString());
    }

}
