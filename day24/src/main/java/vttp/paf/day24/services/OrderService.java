package vttp.paf.day24.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.paf.day24.models.PurchaseOrder;
import vttp.paf.day24.repositories.OrderItemRepo;
import vttp.paf.day24.repositories.PurchaseOrderRepo;

@Service
public class OrderService {
    
    @Autowired 
    private OrderItemRepo oiRepo; 
    
    @Autowired 
    private PurchaseOrderRepo poRepo; 

    @Transactional
    public void createNewOrder(PurchaseOrder po) {

        // generate the orderId
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        System.out.println(">>>> orderId " + orderId);
        po.setOrderId(orderId);

        // create the purchase order 
        poRepo.insertPurchaseOrder(po);

        // create the associated orderItems
        oiRepo.addOrderItems(po.getOrderItemList(), orderId);

    }

}
