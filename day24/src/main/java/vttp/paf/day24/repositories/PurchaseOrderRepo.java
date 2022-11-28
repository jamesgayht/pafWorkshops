package vttp.paf.day24.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.paf.day24.models.PurchaseOrder;
import static vttp.paf.day24.repositories.Queries.*;

@Repository
public class PurchaseOrderRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // doesnt matter what you return, this is just for the user to check whether the syntax 
    // ran properly e.g. returning true (1) means the jdbctemplate was updated properly 
    public boolean insertPurchaseOrder (PurchaseOrder po) {
        return jdbcTemplate.update(SQL_INSERT_PURCHASE_ORDER, po.getOrderId(), po.getName(), po.getOrderDate()) > 0;
    }
}
