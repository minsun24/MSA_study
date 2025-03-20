package com.ohgiraffers.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ohgiraffers.userservice.service.UserService;

import lombok.extern.slf4j.Slf4j;

/* 설명. Jwt 토큰 방식의 로그인을 구경할 때 UsernamePasswordAuthenticationToken 을 처리할 프로바이더	*/
/* 설명. Service 계층을 UserDetailService 타입으로 바꾸고 옴	*/

/* 	특정 토큰(UsernamePasswordAuthenticationToken)을 처리하도록 선택된 프로바이더 */
@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private UserService userService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public JwtAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		/* 설명. 넘어온 토큰에서 사용자가 로그인 시 입력한 id(email), pwd 를 추출	*/
		String email = authentication.getName();
		String pwd = authentication.getCredentials().toString();

		/* 설명. 해당 email과 일치하는 DB에서 조회된 회원	(일치하는 회원 없을 경우 예외 처리)	*/
		UserDetails userDetails = userService.loadUserByUsername(email);

		// 서비스에 받아오는 암호화된 값(암호화된값)
		// 프로바이더가 가져온 사용자 입력값(평문)
		if(!passwordEncoder.matches(pwd, userDetails.getPassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		/* 설명. 예외가 발생하지 않고, 여기 코드가 실행되면 인증된 회원 정보	*/

		return new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities()
		);

	}

	/* 필기. supports : 토큰을 처리할 authentication
	*   */
	@Override
	public boolean supports(Class<?> authentication) {
		
		// UsernamePasswordAuthenticationToken 을 처리할 프로바이더임을 정의
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
