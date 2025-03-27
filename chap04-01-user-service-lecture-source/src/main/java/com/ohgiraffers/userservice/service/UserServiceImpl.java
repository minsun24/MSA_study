package com.ohgiraffers.userservice.service;

import com.ohgiraffers.userservice.aggregate.UserEntity;
import com.ohgiraffers.userservice.dto.UserDTO;
import com.ohgiraffers.userservice.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    ModelMapper modelMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder; // 암호화 도구 추가

    @Autowired  // 의존성 주입
    public UserServiceImpl(UserRepository userRepository
                            , ModelMapper modelMapper
                            , BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bcryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void registUser(UserDTO userDTO){

        /* 설명. 회원 가입 할 때 고유 번호 할당*/
        userDTO.setUserId(UUID.randomUUID().toString());

        /* 설명. Entity 로 modelMapper로 맵핑 후
                엔티티에 있는 encryptedPwd에 암호화된 값을 추가한다.
        */
        UserEntity registUser = modelMapper.map(userDTO, UserEntity.class);
        registUser.setEncryptedPwd(bCryptPasswordEncoder.encode(userDTO.getPwd())); // plain text(평문) -> digest(암호문)
        // DTO에 저장된 비밀번호 평문이 암호화되어 Entity에 들어가게 된다.

        userRepository.save(registUser); // 영속성 컨텍스트에 위임

    }


    /* 설명. 스프링 시큐리티를 사용할 때 Provider에서 활용할(자동으로 실행될) 로그인용 메소드
    *       (id로 회원조회해서 UserDetails타입을 반환하는 메소드) */
    @Override       // UserDataService 층
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 사용자가 로그인 시 입력한 아이디(email)이 들어옴

        UserEntity loginUser = userRepository.findByEmail(email);   // email 필드로 where 절을 걸어서 조회하는 쿼리 메소드

        /* 설명. 사용자가 로그인 시 ID를(=email을) 잘못 입력 했다면 */
        if(loginUser == null) {
            throw new UsernameNotFoundException(email + " 이메일 아이디의 유저는 존재하지 않습니다.");
        }

        /* 설명. DB에서 조회된 해당 email의 회원 권한들을 가져와서 List<GrantedAuthority> 로 만듦  */
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ENTERPRISE"));

        return new User(loginUser.getEmail(), loginUser.getEncryptedPwd(),
            true, true, true, true, grantedAuthorities);
    }


    @Override
    public UserDTO getUserById(String memNo) {
        UserEntity foundUser = userRepository.findById(Long.parseLong(memNo)).get();

        UserDTO userDTO = modelMapper.map(foundUser, UserDTO.class);

        return userDTO;
    }


}
