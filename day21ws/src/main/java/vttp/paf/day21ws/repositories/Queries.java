package vttp.paf.day21ws.repositories;

public class Queries {
    public static final String SQL_SELECT_CUSTOMERS = "select id, company, first_name from customers limit ? offset ?";

    public static final String SQL_SELECT_CUSTOMER_BY_ID = "select id, company, first_name from customers where id = ?";

    public static final String SQL_SELECT_ORDERS_BY_CUSTOMER_ID = "select id, customer_id, ship_name, order_date from orders where customer_id = ?"; 
}
