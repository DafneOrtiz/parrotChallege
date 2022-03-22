package com.parrot.orders.model.db;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product")
	private Integer idProduct;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "unitary_price")
	private Double unitaryPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUser" , nullable = false)
	private User user;
	
	@Column(name = "create_at")
	private Instant createAt;
	
	@Column(name = "update_at")
	private Instant updateAt;

}
