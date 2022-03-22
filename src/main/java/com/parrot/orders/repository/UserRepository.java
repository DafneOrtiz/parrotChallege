package com.parrot.orders.repository;

import org.springframework.data.repository.CrudRepository;

import com.parrot.orders.model.db.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	boolean existsUserByEmail(String email);
	User findUserByEmail(String email);

}
