package com.parrot.orders.service;

import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.parrot.orders.model.db.Order;
import com.parrot.orders.model.db.Product;
import com.parrot.orders.model.dto.OrderRequest;
import com.parrot.orders.model.dto.OrderResponse;
import com.parrot.orders.repository.OrderRepository;
import com.parrot.orders.repository.UserRepository;
import com.parrot.orders.util.OrderUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductService productService;

	@Autowired
	OrderDetailService orderDetailService;

	@Transactional(rollbackFor = Exception.class)
	public Optional<OrderResponse> processOrder(OrderRequest orderRequest) throws Exception {
		log.info("Processing order {} ", orderRequest);

		try {

			String requestValidationError = OrderUtils.validateOrderRequest(orderRequest);
			
			if (StringUtils.isEmpty(requestValidationError)) {
				orderRequest.setUser(userRepository.findUserByEmail(orderRequest.getUser().getEmail()));
			
				Optional<List<Product>> productsOrderSaved = productService.createProductsFromOrder(orderRequest);

				Optional<Order> orderSaved = saveOrder(orderRequest);

				orderDetailService.createOrderDetailFromOrder(productsOrderSaved.get(), orderRequest, orderSaved.get());
			}

			return OrderUtils.buildOrderResponse(orderRequest,requestValidationError);
		} catch (Exception e) {
			log.info("An ocurred a error whe traying to create order {} ", orderRequest, e);
			throw e;
		}

	}

	@Transactional(propagation = Propagation.MANDATORY)
	public Optional<Order> saveOrder(OrderRequest orderDto) {
		return Optional.of(orderRepository.save(OrderUtils.buildOrder(orderDto)));
	}

}
