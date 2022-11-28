package vttp.paf.day21ws.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Customer {

    private int id;
    private String company;
    private String firstName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public static Customer create(SqlRowSet rs) {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setCompany(rs.getString("company"));
        customer.setFirstName(rs.getString("first_name"));
        return customer;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
        .add("id", getId())
        .add("company", getCompany())
        .add("firstName", getFirstName())
        .build();
    }
}
