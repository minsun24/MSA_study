package com.ohgiraffers.orderservice.dto;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
public class OrderMenuDTO {
	private int orderCode;
	private int menuCode;
	private int orderAmount;

}
