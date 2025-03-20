package com.ohgiraffers.userservice.controller;

import com.ohgiraffers.userservice.dto.UserDTO;
import com.ohgiraffers.userservice.service.UserService;
import com.ohgiraffers.userservice.vo.RequestRegistUserVO;
import com.ohgiraffers.userservice.vo.ResponseFindUserVO;
import com.ohgiraffers.userservice.vo.ResponseRegistUserVO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    private Environment env;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(Environment env, UserService userService, ModelMapper modelMapper) {
        this.env = env;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/health")
    public String status() {
        return "I'm Working in User Service " + env.getProperty("local.server.port");
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseRegistUserVO> registUser(@RequestBody RequestRegistUserVO newUser) {
        UserDTO userDTO = modelMapper.map(newUser, UserDTO.class);

        /* 설명. call by reference 개념 */
        userService.registUser(userDTO);    // service 가기 전
        ResponseRegistUserVO successRegistUser = modelMapper.map(userDTO, ResponseRegistUserVO.class);
        // service 다녀온 userDTO
        // service 가기 전/후가 같은 객체이니 반환받을 필요가 없다. (주소값을 넘겨주고, 그 인스턴스를 접근한 것)

        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(successRegistUser);
    }

    @GetMapping("/users/{memNo}")
    public ResponseEntity<ResponseFindUserVO> getUsers(@PathVariable String memNo){
        UserDTO userDTO = userService.getUserById(memNo);   // 서비스가 DTO 반환

        ResponseFindUserVO findUserVO = modelMapper.map(userDTO, ResponseFindUserVO.class);

        return ResponseEntity.status(HttpStatus.OK).body(findUserVO);
    }

}

/*  필기. DTO,  VO 구분
*       DTO : 계층을 오가며 비즈니스 로직을 진행할 때 필요한 값을 받는 것
*           RequestVO : 요청값을 받는 것
*           ResponseVO는 응답값
* */