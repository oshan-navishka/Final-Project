package lk.ijse.phoneshopmanagementsystem.entity;

import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;

import java.util.ArrayList;
import java.util.Date;

public class PlaceOrder {
    private String orderId;
    private Date orderDate;
    private double totalAmount;
    private String customerId;
    private Integer employeeId;
    private String orderStatus;
    private ArrayList<OrderDetails> details;

    public PlaceOrder() {}

    public PlaceOrder(String orderId,  Date orderDate, double totalAmount, String customerId, Integer employeeId, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customerId = customerId;//maha gonek
        this.employeeId = employeeId;
        this.orderStatus = orderStatus;
    }

    public PlaceOrder(String orderId, Date orderDate, double totalAmount, String customerId, Integer employeeId, String orderStatus, ArrayList<OrderDetails> orderDetails) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.orderStatus = orderStatus;
        this.details = orderDetails;
    }

    public PlaceOrder(String orderId, java.sql.Date orderDate, double totalAmount, String customerId, Integer employeeId, String orderStatus, ArrayList<OrderDetails> details) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.orderStatus = orderStatus;
        this.details = details;

    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public int getOrderIdAsInt() {
        if (orderId != null && orderId.startsWith("O")) {
            try {
                return Integer.parseInt(orderId.substring(1));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public void setOrderId(int id) {
        this.orderId = String.format("O%03d", id);
    }

    public Date getOrderDate() { return orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public String getCustomerId() { return customerId; }
    public Integer getEmployeeId() { return employeeId; }
    public String getOrderStatus() { return orderStatus; }
    public ArrayList<OrderDetails> getDetails() { return details; }

    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public void setDetails(ArrayList<OrderDetails> details) { this.details = details; }
}
