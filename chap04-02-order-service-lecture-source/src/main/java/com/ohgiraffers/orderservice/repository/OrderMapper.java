package com.ohgiraffers.orderservice.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ohgiraffers.orderservice.aggregate.Order;

@Mapper		//  == DAO, Repository
public interface OrderMapper {

	List<Order> getOrderByUserId(int userId);
}
