package com.ohgiraffers.userservice.vo;

import lombok.Data;

/* 필기. 사용자가 입력한 값을 받아오는 객체 */
@Data
public class RequestLoginVO {
	private String email;
	private String pwd;
}
