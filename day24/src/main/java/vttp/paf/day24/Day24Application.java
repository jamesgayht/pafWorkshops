package vttp.paf.day24;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.paf.day24.models.OrderItem;
import vttp.paf.day24.models.PurchaseOrder;
import vttp.paf.day24.services.OrderService;

@SpringBootApplication
public class Day24Application implements CommandLineRunner {

	@Autowired 
	// check whether private keyword will affect this 
	private OrderService orderService; 

	public static String[] DESC = {"apple", "orange", "pear", "grapes"};
	public static Integer[] QTY = {10, 5, 3, 5};

	public static void main(String[] args) {
		SpringApplication.run(Day24Application.class, args);
	}

	@Override
	public void run(String... args) {

		PurchaseOrder po = new PurchaseOrder();
		po.setName("jack");
		po.setOrderDate(new Date());

		for(int i=0; i<DESC.length; i++) {
			System.out.println(">>> DESC = " + DESC[i]);
			System.out.println(">>> QTY = " + QTY[i]);
			po.addOrderItem(new OrderItem(DESC[i], QTY[i]));
		}

		System.out.println(">>>> creating new order <<<<<");
		orderService.createNewOrder(po);
	}
}
