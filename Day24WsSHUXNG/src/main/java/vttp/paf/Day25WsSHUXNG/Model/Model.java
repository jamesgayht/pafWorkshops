package vttp.paf.Day25WsSHUXNG.Model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Model {
    private int orderId;
    private Date orderDate;
    private String customerName;
    private String shippingAddress;
    private String notes;
    private double tax;
    private List<OrderDeetsModel> products = new LinkedList<>();

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public double getTax() {
        return tax;
    }
    public void setTax(double tax) {
        this.tax = tax;
    }
    public List<OrderDeetsModel> getProducts() {
        return products;
    }
    public void setProducts(List<OrderDeetsModel> products) {
        this.products = products;
    }

    
}
