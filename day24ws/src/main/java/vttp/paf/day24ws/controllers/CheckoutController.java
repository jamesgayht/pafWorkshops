package vttp.paf.day24ws.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.paf.day24ws.models.LineItem;
import vttp.paf.day24ws.models.PurchaseOrder;
import vttp.paf.day24ws.services.OrderService;

@Controller
@RequestMapping(path="/checkout")
public class CheckoutController {

    @Autowired 
    private OrderService orderService; 

    @PostMapping
    public String postCheckout(@RequestBody MultiValueMap<String, String> form, Model model, HttpSession httpSession) {
        
        @SuppressWarnings("unchecked")
        List<LineItem> lineItems = (List<LineItem>)httpSession.getAttribute("cart");

        System.out.println(">>>> RUNNING INTO ERRORS HERE <<<< ");

        String orderDate = form.getFirst("orderDate");
        String customerName = form.getFirst("customerName");
        String shipAddress = form.getFirst("shipAddress");
        String notes = form.getFirst("notes");
        Float tax = Float.parseFloat(form.getFirst("tax"));

        PurchaseOrder purchaseOrder = new PurchaseOrder(orderDate, customerName, shipAddress, notes, tax, lineItems); 

        orderService.createNewOrder(purchaseOrder);
        System.out.println(">>>> CREATED PURCHASE ORDER <<<<");

        // destroy the session
        httpSession.invalidate();

        model.addAttribute("total", lineItems.size() * 2.5);

        return "checkout";
    }

}
