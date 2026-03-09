package lk.ijse.phoneshopmanagementsystem.entity;

import java.sql.Date;

public class MonthlyOrders {

    private String orderId;
    private Date orderDate;
    private String customerName;
    private double totalAmount;

    public MonthlyOrders(String orderId, Date orderDate, String customerName, double totalAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() { return orderId; }
    public Date getOrderDate() { return orderDate; }
    public String getCustomerName() { return customerName; }
    public double getTotalAmount() { return totalAmount; }
}