package vttp2022.paf.assessment.eshop.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

// DO NOT CHANGE THIS CLASS
public class Customer {
	
	private int customerId;

	private String name;
	private String address;
	private String email;
	
	public static Customer createCustomer(SqlRowSet rs) {
		final Customer customer = new Customer(); 
		customer.setCustomerId(rs.getInt("customer_id"));
		customer.setName(rs.getString("name"));
		customer.setAddress(rs.getString("address"));
		customer.setEmail(rs.getString("email"));
		return customer; 
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public String getAddress() { return this.address; }
	public void setAddress(String address) { this.address = address; }

	public String getEmail() { return this.email; }
	public void setEmail(String email) { this.email = email; }
}
