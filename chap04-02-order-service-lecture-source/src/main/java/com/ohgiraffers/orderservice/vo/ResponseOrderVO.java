package com.ohgiraffers.orderservice.vo;

import java.util.List;

import com.ohgiraffers.orderservice.dto.OrderMenuDTO;

import lombok.Data;

@Data
public class ResponseOrderVO {
	private String orderDate;
	private String orderTime;

	private List<OrderMenuDTO> orderMenus;
}


/*  사용자가 주문한 메뉴 응답값 형식	*/
