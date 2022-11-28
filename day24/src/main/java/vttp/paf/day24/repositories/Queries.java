package vttp.paf.day24.repositories;

public class Queries {
    public static String SQL_INSERT_ORDER_ITEM = "insert into order_item (description, quantity, purchase_order_id) values (?, ?, ?)";

    public static String SQL_INSERT_PURCHASE_ORDER = "insert into purchase_orders (order_id, name, order_date) values (?, ?, ?)";
}
