package vttp.paf.day24ws.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.paf.day24ws.models.PurchaseOrder;
import static vttp.paf.day24ws.repositories.Queries.*;

@Repository
public class PurchaseOrderRepo {

    @Autowired 
    private JdbcTemplate jdbcTemplate; 

    public boolean insertPurchaseOrder(PurchaseOrder po) {
        return jdbcTemplate.update(SQL_INSERT_PURCHASE_ORDER, po.getOrderDate(), po.getCustomerName(), po.getShipAddress(), po.getNotes(), po.getTax()) > 0;
    }
    
}
