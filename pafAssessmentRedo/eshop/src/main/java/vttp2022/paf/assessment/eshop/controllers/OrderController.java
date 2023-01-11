package vttp2022.paf.assessment.eshop.controllers;

import java.io.StringReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.models.Status;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;
import vttp2022.paf.assessment.eshop.services.OrderService;
import vttp2022.paf.assessment.eshop.services.WarehouseService;

@Controller
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository; 

	@Autowired
	private WarehouseService warehouseService;

	@PostMapping(path = "/order")
	public ResponseEntity<String> postOrderId(@RequestBody String body) {

		JsonReader jsonReader = Json.createReader(new StringReader(body));
		JsonObject jsonObject = jsonReader.readObject();
		String name = jsonObject.getString("name");
		JsonArray lineItemsJson = jsonObject.getJsonArray("lineItems");
		List<LineItem> lineItems = new LinkedList<>();

		Customer customer = new Customer();
		Optional<Customer> opt = customerRepository.findCustomerByName(name);

		String customerNotFound = "{\"error\": \"Customer %s not found\"}".formatted(name);

		if (opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body(customerNotFound);
		} else {
			customer = opt.get();
			System.out.printf("Customer Name >>>> %s, Input Name >>>>> %s\n", customer.getName(), name);

			lineItems = lineItemsJson.stream()
					.map(v -> (JsonObject) v)
					.map(v -> LineItem.fromPayload(v))
					.toList();

			Order order = new Order();

			String orderId = UUID.randomUUID().toString().substring(0, 8);
			order.setOrderId(orderId);
			order.setCustomerId(customer.getCustomerId());
			order.setCustomer(customer);
			order.setOrderDate(new Date());
			order.setLineItems(lineItems);

			try {
				orderService.createNewOrder(order, orderId);

			} catch (Exception e) {

				String orderSaveError = "{\"error\": \"%s\"}".formatted(e.getMessage());

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
						.body(orderSaveError);
			}

			// String orderIdJson = "{\"orderId\": \"%s\"}".formatted(orderId);

			// warehouseService.dispatch(order);
			OrderStatus orderStatus = warehouseService.dispatch(order);
			System.out.println("ORDER STATUS >>>> " + orderStatus.toString());
			
			JsonObject results = orderStatus.toJson(); 
			System.out.println("RESULTS >>>> " + results.toString());

			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.body(results.toString());

		}
	}

	@GetMapping(path = "/order/{name}/status")
	public ResponseEntity<String> getDispatchedStatus(@PathVariable String name) {
		
		System.out.println("NAME >>>> " + name);

		Optional<List<Status>> opt = orderRepository.findOrderStatusByName(name); 

		String customerNotFound = "{\"error\": \"Customer %s not found\"}".formatted(name);

		if(opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.contentType(MediaType.APPLICATION_JSON)
			.body(customerNotFound);
		}
		else {
			
			List<Status> statuses = opt.get();
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder(); 
			objectBuilder.add("name", name);
			
			for(Status s:statuses) {
				System.out.println("Status >>> " + s.toString());
				objectBuilder.add(s.getOrderStatus(), s.getCount());
			}

			JsonObject results = objectBuilder.build();
			
			System.out.println("TASK 6 RESULTS >>>> " + results.toString());

			return ResponseEntity.ok(results.toString()); 
		}
	}

}
