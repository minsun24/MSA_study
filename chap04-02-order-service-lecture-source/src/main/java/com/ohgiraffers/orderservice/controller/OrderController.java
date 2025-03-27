package com.ohgiraffers.orderservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohgiraffers.orderservice.dto.OrderDTO;
import com.ohgiraffers.orderservice.service.OrderService;
import com.ohgiraffers.orderservice.vo.ResponseOrderVO;

@RestController
public class OrderController {

	private final OrderService orderService;    // 서비스 인터페이스 추상메서드만 탐색(구현체 클래스는 상관 X)

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/users/{userId}/orders")
	public ResponseEntity<List<ResponseOrderVO>> getUserOrders(@PathVariable int userId) {
		List<OrderDTO> orderDTOList = orderService.getOrderByUserId(userId);

		List<ResponseOrderVO> returnValue = orderDTOToResponseOrder(orderDTOList);

		return ResponseEntity.ok().body(returnValue);
	}

	private List<ResponseOrderVO> orderDTOToResponseOrder(List<OrderDTO> orderDTOList) {

		List<ResponseOrderVO> responseList = new ArrayList<>();

		for (OrderDTO orderDTO : orderDTOList) {
			ResponseOrderVO responseOrder = new ResponseOrderVO();
			responseOrder.setOrderDate(orderDTO.getOrderDate());
			responseOrder.setOrderTime(orderDTO.getOrderTime());
			responseOrder.setOrderMenus(orderDTO.getOrderMenus());

			responseList.add(responseOrder);
		}

		return responseList;
	}
}

/*  문제.
     Q. 왜 컨트롤러가 서비스를 의존 주입 하는가? */
