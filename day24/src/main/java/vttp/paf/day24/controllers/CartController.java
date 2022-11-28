package vttp.paf.day24.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.paf.day24.models.OrderItem;

@Controller
@RequestMapping(path = "/cart")
public class CartController {
    
    @GetMapping
    public String getCart() {
        return "cart";
    } 

    @PostMapping
    public String postCart(@RequestBody MultiValueMap<String,String> form, Model model, HttpSession session) {

        @SuppressWarnings("unchecked")
        List<OrderItem> orderItems = (List<OrderItem>)session.getAttribute("cart");

        if(null == orderItems) {
            // this is a new session
            System.out.println("This is a new session");
            System.out.printf("session id = %s\n", session.getId());
            orderItems = new LinkedList<>(); 
            session.setAttribute("cart", orderItems);
        }

        
        String item = form.getFirst("item");
        Integer quantity = Integer.parseInt(form.getFirst("quantity"));
        
        orderItems.add(new OrderItem(item, quantity));

        for(OrderItem oi: orderItems) {
            System.out.printf("description = %s\nquantity = %d\n", item, quantity);
            model.addAttribute("orderItems", orderItems);
        }
        
        return "cart";
    }
}
