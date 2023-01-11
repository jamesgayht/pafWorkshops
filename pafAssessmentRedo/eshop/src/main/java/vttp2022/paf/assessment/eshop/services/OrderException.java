package vttp2022.paf.assessment.eshop.services;

// checked exception
public class OrderException extends Exception{
    
    public OrderException() {
        super(); 
    }

    public OrderException(String message) {
        super(message);
    }
}
