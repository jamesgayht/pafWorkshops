package vttp2022.paf.assessment.eshop.models;

import javax.sound.sampled.Line;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonObject;

// DO NOT CHANGE THIS CLASS
public class LineItem {

	private String item;
	private Integer quantity;

	public static LineItem createLineItem (SqlRowSet rs) {
		LineItem lineItem = new LineItem();
		lineItem.setItem(rs.getString("item"));
		lineItem.setQuantity(rs.getInt("quantity"));
		return lineItem;
	}

	public static LineItem fromPayload(JsonObject doc) {
		final LineItem lineItem = new LineItem(); 
		lineItem.setItem(doc.getString("item"));
		lineItem.setQuantity(doc.getInt("quantity"));
		return lineItem;
	}

	public String getItem() { return this.item; }
	public void setItem(String item) { this.item = item; }

	public Integer getQuantity() { return this.quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
