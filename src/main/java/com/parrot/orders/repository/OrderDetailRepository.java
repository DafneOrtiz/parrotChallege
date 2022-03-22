package com.parrot.orders.repository;


import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.parrot.orders.model.db.OrderDetail;
import com.parrot.orders.model.dto.ProductsResume;

public interface OrderDetailRepository  extends CrudRepository<OrderDetail, Integer>{
	
	@Query(value = "SELECT PD.NAME productName , SUM(OR.AMOUNT) totalAmount, ROUND(SUM(OR.AMOUNT)*OR.UNITARY_PRICE,2) totalPrice , \n"
			+ "FROM PRODUCTS PD, ORDER_DETAIL OR \n"
			+ "WHERE OR.ID_PRODUCT = PD.ID_PRODUCT   \n"
			+ "AND PD.ID_USER=?1 \n"
			+ "AND OR.CREATE_AT BETWEEN ?2 AND ?3\n"
			+ "GROUP BY PD.NAME ORDER BY totalAmount DESC;", nativeQuery = true)
	 List<ProductsResume> findSoldProductsByDate(int idUser ,Date init,Date end);

}
