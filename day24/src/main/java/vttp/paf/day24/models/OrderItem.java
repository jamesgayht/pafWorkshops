package vttp.paf.day24.models;

public class OrderItem {
    private Integer itemId;
    private String description;
    private Integer quantity;
    
    // private String orderId;
    // you don't need this because it's a foreign key that SQL uses to define the relationship only
    // java references relationship definition in other ways, in which the PurchaseOrders has a 
    // list of OrderItems
    
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderItem() {
        
    }

    public OrderItem (String description, Integer quantity) {
        this.description = description;
        this.quantity = quantity;
    }
}
