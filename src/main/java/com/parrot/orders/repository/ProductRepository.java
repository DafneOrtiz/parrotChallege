package com.parrot.orders.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.parrot.orders.model.db.Product;
import com.parrot.orders.model.db.User;

public interface ProductRepository extends CrudRepository<Product, Integer>{
	
	
	List<Product> findByUser (User user);

}
