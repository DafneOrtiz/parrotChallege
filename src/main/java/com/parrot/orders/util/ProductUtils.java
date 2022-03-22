package com.parrot.orders.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.parrot.orders.model.db.Product;
import com.parrot.orders.model.dto.OrderRequest;

public final class ProductUtils {

	public static final List<Product> buildProductsFromOrderDto(OrderRequest order) {

		List<Product> productsFromOrder = new ArrayList<>();

		order.getProducts().stream().forEach((productDto) -> {
			productsFromOrder
					.add(Product.builder().name(productDto.getName()).unitaryPrice(productDto.getUnitaryPrice())
							.createAt(Instant.now()).updateAt(Instant.now()).user(order.getUser()).build());

		});
		return productsFromOrder;

	}

	public static final List<Product> findProductsNotCreated(List<Product> productsByUser,
			List<Product> productsFromOrder) {

		if (productsByUser.isEmpty()) {
			return productsFromOrder;
		}

		List<String> productsNamesByUser = productsByUser.stream().map(Product::getName).collect(Collectors.toList());

		return productsFromOrder.stream().filter(product -> !productsNamesByUser.contains(product.getName()))
				.collect(Collectors.toList());

	}

	public static final Optional<List<Product>>getInfoProductsFromOrder(List<Product> productsByUser,
			List<Product> productsFromOrder) {

		List<String> productsNames = productsFromOrder.stream().map(Product::getName).collect(Collectors.toList());

		return Optional.of(productsByUser.stream().filter(product -> productsNames.contains(product.getName()))
				.collect(Collectors.toList()));

	}

}
