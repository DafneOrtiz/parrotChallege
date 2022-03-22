package com.parrot.orders.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.parrot.orders.model.db.Order;
import com.parrot.orders.model.db.OrderDetail;
import com.parrot.orders.model.db.Product;
import com.parrot.orders.model.dto.OrderRequest;
import com.parrot.orders.repository.OrderDetailRepository;
import com.parrot.orders.util.OrderDetailUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderDetailService {

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Transactional(propagation = Propagation.MANDATORY)
	public Optional<List<OrderDetail>> createOrderDetailFromOrder(List<Product> productsOrder, OrderRequest orderDto, Order order){
		log.info("Processing order detail {} ", order);
		return Optional.of((List<OrderDetail>) orderDetailRepository.saveAll(OrderDetailUtils.buildOrderDetail(productsOrder, orderDto, order)));
	}

}
