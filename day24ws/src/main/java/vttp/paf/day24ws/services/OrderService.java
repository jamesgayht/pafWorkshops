package vttp.paf.day24ws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.paf.day24ws.models.PurchaseOrder;
import vttp.paf.day24ws.repositories.LineItemRepo;
import vttp.paf.day24ws.repositories.PurchaseOrderRepo;

@Service
public class OrderService {
    
    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    private LineItemRepo lineItemRepo; 

    @Transactional
    public void createNewOrder(PurchaseOrder po) {
        // create the purchase order
        purchaseOrderRepo.insertPurchaseOrder(po);

        // create the associated line items
        lineItemRepo.addLineItems(po.getLineItems(), po.getOrderId());
    }
}
