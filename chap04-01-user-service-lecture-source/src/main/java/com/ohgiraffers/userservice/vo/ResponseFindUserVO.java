package com.ohgiraffers.userservice.vo;

import lombok.Data;

/*  특정 회원번호의 회원 정보 응답 */
@Data
public class ResponseFindUserVO {
	private String email;
	private String name;
	private String userId;

}
