package vttp2022.paf.assessment.eshop.services;

import java.io.StringReader;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;

@Service
public class WarehouseService {

	public static final String URL = "http://paf.chuklee.com/dispatch/";

	@Autowired
	private OrderRepository orderRepository;

	// You cannot change the method's signature
	// You may add one or more checked exceptions
	public OrderStatus dispatch(Order order) {

		String requestUrl = UriComponentsBuilder.fromUriString(URL)
				.path(order.getOrderId())
				.toUriString();

		System.out.println("URL >>>>> " + requestUrl);

		JsonObject requestBody = order.toJson();

		// use the url to make a call to server
		RequestEntity<String> req = RequestEntity.post(requestUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(requestBody.toString());

		System.out.println("REQUEST BODY >>> " + requestBody.toString());

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> resp = null;

		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderId(order.getOrderId());

		try {
			resp = restTemplate.exchange(req, String.class);
		} 
		catch (Exception e) {
			System.out.println("ERROR >>> " + e.getMessage());
			orderRepository.insertOrderStatusPending(orderStatus);
			orderStatus.setStatus("pending");
			return orderStatus;
		}

		System.out.println("RESPONSE >>> " + resp.getBody());

		String payload = resp.getBody();

		JsonReader jsonReader = Json.createReader(new StringReader(payload));
		JsonObject jsonObject = jsonReader.readObject();
		String deliveryId = jsonObject.getString("deliveryId");

		orderStatus.setDeliveryId(deliveryId);
		orderStatus.setStatus("dispatched");

		orderRepository.insertOrderStatusDispatched(orderStatus);

		return orderStatus;

	}
}
