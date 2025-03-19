package com.ohgiraffers.userservice.controller;

import com.ohgiraffers.userservice.vo.RequestRegistUserVO;
import com.ohgiraffers.userservice.vo.ResponseRegistUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    private Environment env;
    private UserService userService;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService ;
    }

    @GetMapping("/health")
    public String status() {
        return "I'm Working in User Service" + env.getProperty("local.server.port");
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseRegistUserVO> registUser(@RequestBody RequestRegistUserVO newUser){
        UserDTO userDTO = modelMapper.map(newUser, UserDTO.class)
    }

    @PostMapping("users")   // 사용자 추가 요청
    public ResponseEntity<ResponseRegistUserVO> registUser(
            @RequestBody RequestRegistUserVO newUser){
        /*  ResponseRegistUserVO & RequestRegistUserVO
            - 값을 담는 용도
            - Value Object -> 별도의 패키지로 관리

        *   */
        return null;
    }

}
