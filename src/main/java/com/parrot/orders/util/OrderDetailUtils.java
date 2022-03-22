package com.parrot.orders.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.parrot.orders.model.db.Order;
import com.parrot.orders.model.db.OrderDetail;
import com.parrot.orders.model.db.Product;
import com.parrot.orders.model.dto.OrderRequest;
import com.parrot.orders.model.dto.ProductOrderDto;

public final class OrderDetailUtils {

	public static final List<OrderDetail> buildOrderDetail(List<Product> productsOrder, OrderRequest orderDto,
			Order order) {

		List<OrderDetail> ordersDetail = new ArrayList<>();
		
		Map<String, Integer> amountByProduct = orderDto.getProducts().stream()
				.collect(Collectors.toMap(ProductOrderDto::getName, ProductOrderDto::getAmount));
		
		Map<String, Double> unitaryPriceByProduct = orderDto.getProducts().stream()
				.collect(Collectors.toMap(ProductOrderDto::getName, ProductOrderDto::getUnitaryPrice));

		productsOrder.stream().forEach((productOrder) -> {
			ordersDetail.add(OrderDetail.builder()
						.product(productOrder)
						.amount(amountByProduct.get(productOrder.getName()))
						.unitaryPrice(unitaryPriceByProduct.get(productOrder.getName()))
						.order(order)
						.createAt(Instant.now())
						.updateAt(Instant.now())
						.build());

		});

		return ordersDetail;

	}

}
