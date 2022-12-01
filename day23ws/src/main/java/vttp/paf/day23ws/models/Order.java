package vttp.paf.day23ws.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Order {

    private int orderId;
    private String orderDate;
    private int customerId;
    private int productId;
    private double totalPrice;
    private double costPrice;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public static Order createOrder(SqlRowSet rs) {

        Order order = new Order(); 

        order.setOrderId(rs.getInt("order_id"));
        order.setOrderDate(rs.getString("order_date"));
        order.setCustomerId(rs.getInt("customer_id"));
        order.setProductId(rs.getInt("product_id"));
        order.setTotalPrice(rs.getDouble("total_price"));
        order.setCostPrice(rs.getDouble("cost_price"));

        return order;
    }

}
