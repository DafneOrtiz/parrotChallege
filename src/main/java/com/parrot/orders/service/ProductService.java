package com.parrot.orders.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.parrot.orders.model.db.Product;
import com.parrot.orders.model.db.User;
import com.parrot.orders.model.dto.OrderRequest;
import com.parrot.orders.repository.ProductRepository;
import com.parrot.orders.util.ProductUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public Optional<List<Product>> createProductsFromOrder(OrderRequest order) {
		log.info("Processing products {} ", order);

		List<Product> productsFromOrder = ProductUtils.buildProductsFromOrderDto(order);

		Optional<List<Product>> productsSaved = saveProducts(productsFromOrder, order.getUser());

		return (productsSaved.isPresent() ? productsSaved : Optional.empty());

	}

	@Transactional(propagation = Propagation.MANDATORY)
	public Optional<List<Product>> saveProducts(List<Product> productsFromOrder, User user) {

		List<Product> productsByUser = productRepository.findByUser(user);

		List<Product> productsNotCreated = ProductUtils.findProductsNotCreated(productsByUser, productsFromOrder);

		if (!productsNotCreated.isEmpty()) {
			productsByUser.addAll((List<Product>) productRepository.saveAll(productsNotCreated));
		}
		return ProductUtils.getInfoProductsFromOrder(productsByUser, productsFromOrder);

	}

}
