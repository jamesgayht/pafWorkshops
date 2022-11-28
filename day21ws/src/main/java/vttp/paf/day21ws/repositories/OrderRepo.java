package vttp.paf.day21ws.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.paf.day21ws.models.Order;
import static vttp.paf.day21ws.repositories.Queries.*;;

@Repository
public class OrderRepo {
  
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Order> getOrders(Integer customerId) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_ORDERS_BY_CUSTOMER_ID, customerId);

        List<Order> orders = new LinkedList<>(); 

        while(rs.next())
            orders.add(Order.create(rs));

        return orders; 
    }
}
