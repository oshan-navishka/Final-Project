package lk.ijse.phoneshopmanagementsystem.dto;

public class OrderTableDTO {
    private String orderId;
    private String orderDate;
    private String customerId;
    private String customerName;
    private Double totalAmount;
    private String status;
    private Integer employeeId;

    public OrderTableDTO(String orderId, String orderDate, String customerId,
                         String customerName, Double totalAmount, String status, Integer employeeId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.status = status;
        this.employeeId = employeeId;
    }


    public String getOrderId() { return orderId; }
    public String getOrderDate() { return orderDate; }
    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public Double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public Integer getEmployeeId() { return employeeId; }

    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public void setStatus(String status) { this.status = status; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
}
