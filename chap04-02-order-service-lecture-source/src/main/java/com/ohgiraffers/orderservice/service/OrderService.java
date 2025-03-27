package com.ohgiraffers.orderservice.service;

import java.util.List;

import com.ohgiraffers.orderservice.dto.OrderDTO;

public interface OrderService {
	List<OrderDTO> getOrderByUserId(int userId);
}

/*  필기. 인터페이스를 통해 연동한 이유
     - PSA 참고
     - SOLID 때문에, 인터페이스를 구현하였음.
* */
