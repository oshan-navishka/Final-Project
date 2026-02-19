package lk.ijse.phoneshopmanagementsystem.dto;

import lk.ijse.phoneshopmanagementsystem.entity.OrderDetails;

import java.util.ArrayList;
import java.util.Date;

public class PlaceOrderDTO {
    private String orderId;
    private Date orderDate;
    private double totalAmount;
    private String customerId;
    private Integer employeeId;
    private String orderStatus;
    private ArrayList<OrderDetailDTO> orderDetails;

    public PlaceOrderDTO() {}

    public PlaceOrderDTO(String orderId,  Date orderDate, double totalAmount, String customerId, Integer employeeId, String orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.orderStatus = orderStatus;
        this.orderDetails = new ArrayList<>();

    }

    public PlaceOrderDTO(String orderId, Date orderDate, double totalAmount, String customerId, Integer employeeId,
                         String orderStatus, ArrayList<OrderDetailDTO> orderDetails) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.orderStatus = orderStatus;
        this.orderDetails = orderDetails;
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
    public ArrayList<OrderDetailDTO> getOrderDetails() { return orderDetails; }

    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public void setOrderDetails(ArrayList<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}