package vttp.paf.day24ws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.paf.day24ws.models.PurchaseOrder;
import vttp.paf.day24ws.repositories.LineItemRepo;
import vttp.paf.day24ws.repositories.PurchaseOrderRepo;
import static vttp.paf.day24ws.repositories.Queries.*;

@Service
public class OrderService {
    
    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    private LineItemRepo lineItemRepo; 

    @Autowired JdbcTemplate jdbcTemplate; 

    @Transactional
    public void createNewOrder(PurchaseOrder po) {
        // create the purchase order
        purchaseOrderRepo.insertPurchaseOrder(po);
        
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_ORDER_ID_FROM_LAST_INSERTED_ROW); 
        Integer orderId = null; 

        if(rs.next()) {
            orderId = rs.getInt("order_id");
        }

        // create the associated line items
        lineItemRepo.addLineItems(po.getLineItems(), orderId);
    }
}
