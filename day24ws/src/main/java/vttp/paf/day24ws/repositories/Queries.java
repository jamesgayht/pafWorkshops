package vttp.paf.day24ws.repositories;

public class Queries {
    
    public static final String SQL_INSERT_LINE_ITEM = "INSERT INTO order_details (product, unit_price, discount, quantity, order_id) values (?, ?, ?, ?, ?)"; 

    public static final String SQL_INSERT_PURCHASE_ORDER = "insert into orders (order_date, customer_name, ship_address, notes, tax) values (?, ?, ?, ?, ?)"; 

    public static final String SQL_GET_ORDER_ID_FROM_LAST_INSERTED_ROW = "select * from orders where order_id=(SELECT LAST_INSERT_ID())";
}
