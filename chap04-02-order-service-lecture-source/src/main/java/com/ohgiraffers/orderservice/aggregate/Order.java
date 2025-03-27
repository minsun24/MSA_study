package com.ohgiraffers.orderservice.aggregate;

import java.util.List;

import lombok.Data;

@Data
public class Order {
	private int orderCode;
	private int userId;
	private String orderDate;
	private String orderTime;
	private int totalOrderPrice;
	// ------------------------------- 테이블과 똑같은 형식

	// JOIN할 경우
	private List<OrderMenu> orderMenus;


}
