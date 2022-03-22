package com.parrot.orders.repository;

import org.springframework.data.repository.CrudRepository;

import com.parrot.orders.model.db.Order;

public interface OrderRepository  extends CrudRepository<Order, Integer> {

}
