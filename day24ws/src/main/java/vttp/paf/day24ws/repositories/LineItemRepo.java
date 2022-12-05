package vttp.paf.day24ws.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.paf.day24ws.models.LineItem;
import vttp.paf.day24ws.models.PurchaseOrder;

import static vttp.paf.day24ws.repositories.Queries.*;

import java.util.List;

@Repository
public class LineItemRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 

    // public void addLineItems(PurchaseOrder po) {
    //     addLineItems(po.getLineItems(), po.getOrderId());
    // }

    public void addLineItems(List<LineItem> lineItems, Integer orderId) {

        List<Object[]> data = lineItems.stream().map(li -> {
            Object[] l = new Object[5];
            l[0] = li.getProduct();
            l[1] = li.getUnitPrice(); 
            l[2] = li.getDiscount(); 
            l[3] = li.getQuantity(); 
            l[4] = orderId; 
            return l;            
        }).toList();

        jdbcTemplate.batchUpdate(SQL_INSERT_LINE_ITEM, data);
    }

}
