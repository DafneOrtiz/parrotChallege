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
@Table(name = "order_detail")
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order_detail")
	private Integer idOrderDetail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idOrder" , nullable = false)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProduct" , nullable = false)
	private Product product;
	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "unitary_price")
	private Double unitaryPrice;
	
	@Column(name = "create_at")
	private Instant createAt;
	
	@Column(name = "update_at")
	private Instant updateAt;

}
