package vttp.paf.day23ws.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.paf.day23ws.models.Order;
import static vttp.paf.day23ws.repositories.Queries.*; 

@Repository
public class OrderRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Order> getOrdersById (Integer orderId) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_ORDER_BY_ID, orderId); 
        List<Order> orders = new LinkedList<>();

        while(rs.next()) 
            orders.add(Order.createOrder(rs));

        return orders;         
    }
}
