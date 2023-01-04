package vttp.paf.Day25WsSHUXNG.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/order")
public class FormController {
    
    @PostMapping
    public String SendOrder(Model model){
        
        return "order";
    }
}
