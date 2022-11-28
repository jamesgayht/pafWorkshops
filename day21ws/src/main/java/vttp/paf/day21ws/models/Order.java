package vttp.paf.day21ws.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Order {

    private int id;
    private int customerId;
    private String shipName;
    private String orderDate;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Order create(SqlRowSet rs) {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setCustomerId(rs.getInt("customer_id"));
        order.setOrderDate(rs.getString("order_date"));
        order.setShipName(rs.getString("ship_name"));

        return order;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
        .add("id", getId())
        .add("customerId", getCustomerId())
        .add("shipName", getShipName())
        .add("orderDate", getOrderDate())
        .build();
    }
}
