package vttp.paf.day23ws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.paf.day23ws.models.Order;
import vttp.paf.day23ws.services.OrderService;

@Controller
@RequestMapping(path = "/order/total")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/{orderId}")
    public String getOrders(@PathVariable Integer orderId, Model model) {
    // public String getOrders(@RequestParam Integer orderId, Model model) {
        
        // model.addAttribute("orderId", orderId);

        List<Order> orders = orderService.getOrders(orderId);

        model.addAttribute("orders", orders);
        
        return "orders";
    }
}
