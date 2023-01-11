package vttp2022.paf.assessment.eshop.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Status {

    private String orderStatus;
    private Integer count;

    public static Status createStatus (SqlRowSet rs) {
        Status status = new Status(); 
        status.setOrderStatus(rs.getString("status"));
        status.setCount(rs.getInt("count"));
        return status;
    }

    @Override
    public String toString () {
        return "ORDER STATUS: %s, COUNT: %d\n".formatted(orderStatus, count);
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
