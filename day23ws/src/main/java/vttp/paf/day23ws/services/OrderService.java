package vttp.paf.day23ws.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day23ws.models.Order;
import vttp.paf.day23ws.repositories.OrderRepo;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepo orderRepo; 

    public List<Order> getOrders(Integer orderId) {
        return orderRepo.getOrdersById(orderId); 
    }
}
