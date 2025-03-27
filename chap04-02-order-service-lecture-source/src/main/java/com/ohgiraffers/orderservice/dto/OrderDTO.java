package com.ohgiraffers.orderservice.dto;

import java.util.List;

import lombok.Data;


/* 설명. 추천하는 방식
 * 		Query 쪽은 DTO 로 매칭 이동 및 DB와의 CRUD 관련해서까지 다 활용할 수 있지만
 * 		DTO가 의미 없이 모든 경우의 수를 감당해 내기 보다는
 * 		DTO를 "요청별"로 구분해서 설계하고,
 * 		Entity 개념의 클래스는 모둔 ResultMap의 결과물을 받아줄 수 있게 설계하는 것을 추천
 * 		-
 * 		(DTO : 요청할 요청 응답 값을 담는 용도,
 * 		Entity 개념의 클래스 : 모든 resultmap 의 결과를 받아줄 수 있는 용도)
 *
 * */

/*  	-> Q. 그러면, 요청이랑 응답으로만 구분하는 건 ???
 * 		-> entity 도 만들게 되면.... query, command에서 공용으로 사용 가능? ??
 * */

@Data
public class OrderDTO {  // 의미: MyOrderDTO (특정 회원의 주문건을 조회하는 용도)
	private int orderCode;
	private int userId;
	private String orderDate;
	private String orderTime;
	private int totalOrderPrice;

	// 응답할 때 조인 필요
	private List<OrderMenuDTO> orderMenus;
}


/*
* 	DTO 세부적으로 구현하면 -> MyOrderDTO, AllOrderDTO
	JOIN 여부에 따라 다른 구조로 구현될 수 있음.
	DTO/ENTITY는 RESULTMAP 모두 만족 가능 ? ?
	*
	* 요청값/응답값은 매번 달라지게 됨. 응답값(조인 발생 여부)
	* 응답값 조인 발생 -> RESULT MAP 여러 형식으로 ...ㅣ
	* 계층을 오갈 때 담는 값이 달라짐 ..... 그럴 대 받는 것이 DTO? 4
	*
	* Order == OrderDTO
	* 테이블이맣아질수록 DTO 크게 됨
	* => 그렇다면? dto(컨트롤러부터 요청/응답)와 resultmap 전용 aggregaet
*
* * */

