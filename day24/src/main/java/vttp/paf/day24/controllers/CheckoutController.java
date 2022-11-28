package vttp.paf.day24.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import vttp.paf.day24.models.OrderItem;

@Controller
public class CheckoutController {
    
    @PostMapping
    public String postCheckout(Model model, HttpSession session) {

        @SuppressWarnings("unchecked")
        List<OrderItem> orderItems = (List<OrderItem>)session.getAttribute("cart"); 

        // destroy the session
        session.invalidate();

        // 2.5 is just a random price 
        model.addAttribute("total", orderItems.size() * 2.5);

        return "checkout";
    }
}
