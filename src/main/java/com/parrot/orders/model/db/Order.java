package com.parrot.orders.model.db;

import java.time.Instant;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private Integer idOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUser" , nullable = false)
	private User user;

	@Column(name = "client_name")
	private String clientName;

	@Column(name = "total_price")
	private Double TotalPrice;

	@Column(name = "create_at")
	private Instant createAt;
	
	@Column(name = "update_at")
	private Instant updateAt;

}
