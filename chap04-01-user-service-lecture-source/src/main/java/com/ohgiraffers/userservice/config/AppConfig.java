package com.ohgiraffers.userservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    /* 설명. ModelMapper가 매번 매핑할 때 정확히 일치하는 필드끼리 매칭하도록 설정한 bean   */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        /* 맵핑 에러 해결을 위한 맵핑 전략 설정 */
        // modelMapper.getConfiguration() : 모델맵퍼의 설정으로 이동
        // setMatchingStrategy() : 매칭 전략 설정
        // MatchingStrategies 매칭종류들.종류
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}