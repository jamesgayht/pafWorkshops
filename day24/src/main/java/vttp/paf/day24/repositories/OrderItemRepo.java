package vttp.paf.day24.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.paf.day24.models.OrderItem;
import vttp.paf.day24.models.PurchaseOrder;

import static vttp.paf.day24.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderItemRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // by right, should return something to check whether the query ran successfully
    public void addOrderItems(PurchaseOrder po) {
        addOrderItems(po.getOrderItemList(), po.getOrderId());
    }

    public void addOrderItems(List<OrderItem> oiList, String orderId) {

        List<Object[]> orderItems = new LinkedList<>();
        for (OrderItem oi : oiList) {
            Object[] o = new Object[3];
            o[0] = oi.getDescription();
            o[1] = oi.getItemId();
            o[2] = oi.getQuantity();
            orderItems.add(o);
        }
        // need to add jdbctemplate here to update MySQL database
        // jdbcTemplate.batchUpdate(SQL_INSERT_ORDER_ITEM, o[0]);
    }

    // lecture notes example of batch updates
    public int[] addOrderItemsLambda(List<OrderItem> oiList, String orderId) {
        List<Object[]> orderItems = new LinkedList<>();
        orderItems = oiList.stream().map(oi -> new Object[] { oi.getDescription(), oi.getItemId(), oi.getQuantity()})
                .collect(Collectors.toList());

        int[] addedOrders = jdbcTemplate.batchUpdate(SQL_INSERT_ORDER_ITEM, orderItems);

        return addedOrders;
    }

    // chuk's example of stream
    public void addOrderItemsCStream(List<OrderItem> oiList, String orderId) {
        List<Object[]> orderItems = new LinkedList<>();
        orderItems = oiList.stream().map(oi -> {
            Object[] o = new Object[3];
            o[0] = oi.getDescription();
            o[1] = oi.getQuantity();
            o[2] = orderId;
            return o;
        })
        .toList();

        
    }
}
