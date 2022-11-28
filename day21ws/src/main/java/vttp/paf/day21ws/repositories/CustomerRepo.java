package vttp.paf.day21ws.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.paf.day21ws.models.Customer;
import static vttp.paf.day21ws.repositories.Queries.*;

@Repository
public class CustomerRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate; 

    // get customer orders by ID 
    // public List<Customer> getCustomerOrdersById(Integer id) {
    //     final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_ORDERS_BY_CUSTOMER_ID, id); 
    //     List<Customer> customers = new LinkedList<>(); 
    //     while(rs.next())
    //         customers.add(Customer.create(rs));
    //     return customers; 
    // }

    // get customers by ID 
    public List<Customer> getCustomerById(Integer id) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_CUSTOMER_BY_ID, id); 
        List<Customer> customers = new LinkedList<>(); 
        while(rs.next())
            customers.add(Customer.create(rs));
        return customers; 
    }

    // get customers for limit and offset - overload 
    public List<Customer> getCustomers() {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_CUSTOMERS, 2, 0);
        List<Customer> customers = new LinkedList<>();

        while(rs.next())
            customers.add(Customer.create(rs));

        return customers;
    }

    public List<Customer> getCustomers(Integer limit) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_CUSTOMERS, limit, 0); 
        List<Customer> customers = new LinkedList<>(); 

        while(rs.next()) 
            customers.add(Customer.create(rs));

        return customers;
    }

    public List<Customer> getCustomers(int limit, int offset) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_CUSTOMERS, limit, offset); 
        List<Customer> customers = new LinkedList<>();
        while(rs.next()) {
            customers.add(Customer.create(rs));
        }
        return customers; 
    }
}