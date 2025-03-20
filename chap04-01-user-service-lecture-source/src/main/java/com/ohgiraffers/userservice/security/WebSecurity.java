package com.ohgiraffers.userservice.security;

import java.util.Collections;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.Filter;

/* 설명. WebSecurityConfigureMapper를 상속 받거나 @EnableWebSecurity 를 쓰는 예제는 옛날 방식	*/

@Configuration
public class WebSecurity {
	private JwtAuthenticationProvider jwtAuthenticationProvider;
	private Environment env;		// 의존성 주입 DI
	private JwtUtil jwtUtil; 	// 의존성 주입 DI

	@Autowired
	public WebSecurity(JwtAuthenticationProvider jwtAuthenticationProvider,
		Environment env,
		JwtUtil jwtUtil) {
		this.jwtAuthenticationProvider = jwtAuthenticationProvider;
		this.env = env;
		this.jwtUtil = jwtUtil;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
	}


	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
		http.csrf(csrf -> csrf.disable());
		// 화이트라벨 방식 -> 권한 설정
		/* 설명. 허용되는 경로 및 권한 설정 */
		/*
		 * 	AntPathRequestMatcher로 경로를 객체로 만들어, 해당 경로에 어떤 대상이 어떤 권한이 가능한 지 설정
		 * */
		http.authorizeHttpRequests(authz -> authz
					.requestMatchers(new AntPathRequestMatcher("/users/**", "POST")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/users/**", "GET")).hasRole("ENTERPRISE")
					.anyRequest().authenticated()
			)
			.authenticationManager(authenticationManager())

			/* 설명. Session을 쓰지 않고 Jwt 토큰방식으로 LocalThread에 저장하겠다는 설정
			* 		세션 - 서버에 저장
			* 		jwt 토큰 방식 - Authentication 은 응답하면 사라지는 공간(?)
			* */
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilter(getAuthenticationFilter(authenticationManager()));

		/* 설명. 로그인 이후 사용자가 들고 온 (request header 에 발급받은 bearer 토큰을 들고 온) 토큰을 검증하기 위한 필터 등록	*/
		http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
		// JwtFilter의 doFilterInternal 실행

		return http.build();
	}

	/* 설명. Filter는 jakarta.servlet으로 import */
	private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
		return new AuthenticationFilter(authenticationManager, env);
	}

}
