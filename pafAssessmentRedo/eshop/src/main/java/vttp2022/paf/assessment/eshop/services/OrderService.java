package vttp2022.paf.assessment.eshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Transactional(rollbackFor = OrderException.class)
    public void createNewOrder(Order order, String orderId) throws OrderException {

        // create the order
        orderRepository.insertOrder(order);

        // create the associated lineitems
        orderRepository.insertLineItems(order.getLineItems(), orderId);
    }

}
