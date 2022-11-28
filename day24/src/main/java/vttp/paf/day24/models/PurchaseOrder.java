package vttp.paf.day24.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PurchaseOrder {
    private String orderId;
    private String name;
    private Date orderDate;
    private List<OrderItem> orderItemList = new LinkedList<>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public void addOrderItem(OrderItem orderItem) {
        System.out.println(">>>> orderItem = " + orderItem.getItemId());
        System.out.println(">>>> orderItem = " + orderItem.getDescription());
        System.out.println(">>>> orderItem = " + orderItem.getQuantity());
        this.orderItemList.add(orderItem);
    }
    
}
