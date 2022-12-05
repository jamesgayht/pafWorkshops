package vttp.paf.day24ws.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.paf.day24ws.models.LineItem;

@Controller
@RequestMapping(path="/cart")
public class CartController {

    @PostMapping
    public String postCart(@RequestBody MultiValueMap<String, String> form, Model model, HttpSession session) {

        @SuppressWarnings("unchecked")
        List<LineItem> lineItems = (List<LineItem>)session.getAttribute("cart");

        if(null == lineItems) {
            System.out.println("This is a new session.");
            System.out.printf("Session id = %s\n", session.getId());
            lineItems = new LinkedList<>(); 
            session.setAttribute("cart", lineItems);
        }

        String product = form.getFirst("product"); 
        Float unitPrice = Float.parseFloat(form.getFirst("unitPrice"));
        Float discount = Float.parseFloat(form.getFirst("discount"));
        Integer quantity = Integer.parseInt(form.getFirst("quantity"));
        lineItems.add(new LineItem(product, unitPrice, discount, quantity));

        // for(LineItem li:lineItems)
        //     System.out.printf("product: %s\nunitPrice: %f\ndiscount: %f\nquantity: %d\n", product, unitPrice, discount, quantity);

        model.addAttribute("lineItems", lineItems);

        return "cart";
    }
    
}
