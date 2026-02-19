package lk.ijse.phoneshopmanagementsystem.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private String orderId;
    private LocalDate orderDate;
    private String customerId;
    private double total;
    private double discount;
    private double netTotal;
    private String paymentMethod;
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO() {
        this.orderDetails = new ArrayList<>();
    }

    public OrderDTO(String orderId, LocalDate orderDate, String customerId,
                    double total, double discount, double netTotal, String paymentMethod) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.total = total;
        this.discount = discount;
        this.netTotal = netTotal;
        this.paymentMethod = paymentMethod;
        this.orderDetails = new ArrayList<>();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", customerId='" + customerId + '\'' +
                ", total=" + total +
                ", discount=" + discount +
                ", netTotal=" + netTotal +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}