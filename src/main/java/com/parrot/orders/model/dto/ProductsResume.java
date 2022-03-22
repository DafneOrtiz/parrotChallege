package com.parrot.orders.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ProductsResume {

	public String getProductName();

	public String getTotalAmount();

	public String getTotalPrice();

}
