package vttp2022.paf.assessment.eshop.respositories;

public class Queries {
    
    public static String SQL_FIND_CUSTOMER_BY_NAME = "select * from customers where name = ?";

    public static String SQL_INSERT_INTO_ORDER = "insert into orders (order_id, customer_id, order_date) values (?, ?, ?)";

    public static String SQL_INSERT_INTO_LINEITEMS = "insert into lineitems (item, quantity, order_id) values (?, ?, ?)";    

    public static String SQL_GET_ORDER_BY_ORDERID = "select * from orders o join customers c on o.customer_id = c.customer_id where o.order_id = ?";

    public static String SQL_GET_LINEITEMS_BY_ORDERID = "select * from lineitems where order_id = ?";

    public static String SQL_INSERT_INTO_ORDER_STATUS_DISPATCHED = "insert into order_status (order_id, delivery_id, status, status_update) values (?, ?, \"dispatched\", current_timestamp())";
    
    public static String SQL_INSERT_INTO_ORDER_STATUS_PENDING = "insert into order_status (order_id, status, status_update) values (?, \"pending\", current_timestamp())";

    public static String SQL_FIND_ORDER_STATUS_BY_NAME = "select os.status, COUNT(os.status) as count from customers c join orders o on o.customer_id = c.customer_id join order_status os on o.order_id = os.order_id where c.name = ? group by c.name, os.status";



}
