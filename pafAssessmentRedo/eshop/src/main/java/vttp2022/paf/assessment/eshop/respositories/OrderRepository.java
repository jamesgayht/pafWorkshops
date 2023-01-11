package vttp2022.paf.assessment.eshop.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.models.Status;


import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate; 

	public boolean insertOrder(Order order) {

		return jdbcTemplate.update(SQL_INSERT_INTO_ORDER, order.getOrderId(), order.getCustomerId(), order.getOrderDate()) > 0; 

	}

	public void insertOrderStatusDispatched(OrderStatus orderStatus) {
		jdbcTemplate.update(SQL_INSERT_INTO_ORDER_STATUS_DISPATCHED, orderStatus.getOrderId(), orderStatus.getDeliveryId()); 
	}
	
	public void insertOrderStatusPending(OrderStatus orderStatus) {
		jdbcTemplate.update(SQL_INSERT_INTO_ORDER_STATUS_PENDING, orderStatus.getOrderId()); 
	}

	public void insertLineItems(List<LineItem> lineItems, String orderId) {
		List<Object[]> data = lineItems.stream()
								.map(li -> {
									Object[] l = new Object[3];
									l[0] = li.getItem();
									l[1] = li.getQuantity();
									l[2] = orderId; 
									return l;
								}).toList();
								
		// batch update
		jdbcTemplate.batchUpdate(SQL_INSERT_INTO_LINEITEMS, data);
	}

	public Optional<List<Status>> findOrderStatusByName (String name) {
		
		final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_ORDER_STATUS_BY_NAME, name); 

		List<Status> statuses = new LinkedList<>();
		
		if(rs.wasNull()) {
			return Optional.empty();
		}
		else {
			while(rs.next()) {
				statuses.add(Status.createStatus(rs));
				System.out.println("LIST SIZE >> " + statuses.size());
			}

			return Optional.of(statuses);
		}
	}

	public Optional<Order> findOrderByOrderId(String orderId) {
		
		final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_ORDER_BY_ORDERID, orderId); 

		final SqlRowSet rsLineItems = jdbcTemplate.queryForRowSet(SQL_GET_LINEITEMS_BY_ORDERID, orderId); 

		List<LineItem> lineItems = new LinkedList<>(); 
		
		while(rsLineItems.next()) 
			lineItems.add(LineItem.createLineItem(rsLineItems));
		
		Order order = new Order();

		if(!rs.next()) {
			return Optional.empty();
		}
		else {
			order = Order.createOrder(rs);
		}

		order.setLineItems(lineItems);

		return Optional.of(order); 

	}

}
