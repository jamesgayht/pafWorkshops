package vttp.paf.day24ws.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PurchaseOrder {
    private int orderId;
    private String orderDate;
    private String customerName;
    private String shipAddress;
    private String notes;
    private float tax;
    private List<LineItem> lineItems = new LinkedList<>(); 

    public PurchaseOrder() {}

    public PurchaseOrder (String orderDate, String customerName, String shipAddress, String notes, float tax, List<LineItem> lineItems) {

        this.orderDate = orderDate;
        this.customerName = customerName; 
        this.shipAddress = shipAddress;
        this.notes = notes; 
        this.tax = tax; 
        this.lineItems = lineItems;

    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }
}
