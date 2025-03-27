package com.ohgiraffers.orderservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ohgiraffers.orderservice.aggregate.Order;
import com.ohgiraffers.orderservice.aggregate.OrderMenu;
import com.ohgiraffers.orderservice.dto.OrderDTO;
import com.ohgiraffers.orderservice.dto.OrderMenuDTO;
import com.ohgiraffers.orderservice.repository.OrderMapper;

@Service
public class OrderServiceImpl implements OrderService {
	private OrderMapper orderMapper;

	@Autowired
	public OrderServiceImpl(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	@Override
	public List<OrderDTO> getOrderByUserId(int userId) {
		List<Order> orderByUser = orderMapper.getOrderByUserId(userId);  // 반환해서 돌아오는 것 (resultMap) 은 Order 타입

		List<OrderDTO> orderDTOList = orderToOrderDTO(orderByUser);
		return orderDTOList;
	}

	/* 설명. List<Order> -> List<OderDTO>
	    == Entity(ResultMap)개념-> DTO
	* */
	// modelMapper 사용하지 않고 
	// Order 리스트에서 Order객체들을 하나씩 꺼내서 OrderDTO로 변환해주는 메서드
	// OrderDTO 타입 리스트를 반환
	private List<OrderDTO> orderToOrderDTO(List<Order> orderList) {
		List<OrderDTO> orderDTOList = new ArrayList<>();
		for (Order order : orderList) {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderDate(order.getOrderDate());
			orderDTO.setOrderTime(order.getOrderTime());

			List<OrderMenu> orderMenuList = order.getOrderMenus();
			List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
			for (OrderMenu orderMenu : orderMenuList) {
				OrderMenuDTO orderMenuDTO = new OrderMenuDTO();
				orderMenuDTO.setOrderCode(orderMenu.getOrderCode());
				orderMenuDTO.setMenuCode(orderMenu.getMenuCode());
				orderMenuDTO.setOrderAmount(orderMenu.getOrderAmount());

				orderMenuDTOList.add(orderMenuDTO);
			}

			orderDTO.setOrderMenus(orderMenuDTOList);
			orderDTO.setTotalOrderPrice(order.getTotalOrderPrice());
			orderDTOList.add(orderDTO);
		}

		return orderDTOList;
	}


}


/*  필기. 서비스 계층
     - 서비스 인터페이스를 구현한 하위구현체 => @Service 서비스 빈으로 등록시켜 둬야 함.
     - 컨트롤러로부터 요청을 넘겨 받아, Mapper를 실행시킴
     - @Service : 서비스 빈이라는 뜻
     - @Transactional : Spring이 프록시 객체를 만들어서 트랜잭션을 관리
* */