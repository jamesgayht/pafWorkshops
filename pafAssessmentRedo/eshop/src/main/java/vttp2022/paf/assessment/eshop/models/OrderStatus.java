package vttp2022.paf.assessment.eshop.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

// DO NOT CHANGE THIS CLASS
public class OrderStatus {

	public enum Status {pending, dispatched}

	private String orderId;
	private String deliveryId;
	private Status status;

	public JsonObject toJson() {
		if(deliveryId != null) {
			return Json.createObjectBuilder().add("orderId", orderId)
						.add("deliveryId", deliveryId)
						.add("status", status.toString())
						.build();
		}
		else {
			return Json.createObjectBuilder().add("orderId", orderId)
						.add("status", status.toString())
						.build();
		}
	}

	@Override
	public String toString() {
		return "ORDER ID >>> %s, DELIVERY ID >>> %s, STATUS >>> %s".formatted(orderId, deliveryId, status.toString());
	}

	public String getOrderId() { return this.orderId; }
	public void setOrderId(String orderId) { this.orderId = orderId; }

	public String getDeliveryId() { return this.deliveryId; }
	public void setDeliveryId(String deliveryId) { this.deliveryId = deliveryId; }

	public Status getStatus() { return this.status; }
	public void setStatus(Status status) { this.status = status; }
	public void setStatus(String status) { this.status = Status.valueOf(status); }
}
