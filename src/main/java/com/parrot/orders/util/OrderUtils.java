package com.parrot.orders.util;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import com.parrot.orders.model.db.Order;
import com.parrot.orders.model.dto.OrderRequest;
import com.parrot.orders.model.dto.OrderResponse;

public final class OrderUtils {

	public static final Order buildOrder(OrderRequest order) {

		return Order.builder()
				.user(order.getUser())
				.createAt(Instant.now())
				.updateAt(Instant.now())
				.clientName(order.getClientName())
				.TotalPrice(order.getTotalPrice())
				.build();

	}

	public static final String validateOrderRequest(OrderRequest orderResquest) {

		String errorMesage = "";

		if (Objects.isNull(orderResquest)) {
			errorMesage = "Order cannot be null";
		}
		if (Objects.isNull(orderResquest.getProducts())) {
			errorMesage = "Products cannot be null";
		}
		if (StringUtils.isEmpty(orderResquest.getClientName())) {
			errorMesage = "Client name cannot be null";
		}
		if (Objects.isNull(orderResquest.getTotalPrice())) {
			errorMesage = "Total price cannot be null";
		}
		return errorMesage;

	}
	
	public static final Optional<OrderResponse> buildOrderResponse(OrderRequest orderResquest, String requestValidationError) {
		
		return Optional.of(OrderResponse.builder()
				.clientName(orderResquest.getClientName())
				.products(orderResquest.getProducts())
				.TotalPrice(orderResquest.getTotalPrice())
				.requestValidationError(requestValidationError)
				.build());
		

	}

}
