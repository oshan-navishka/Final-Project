package lk.ijse.phoneshopmanagementsystem.dto;

public class SaleReportDTO {
    private String orderId, date, customer, product, paymentMethod;
    private int quantity;
    private double amount;

    public SaleReportDTO(String orderId, String date, String customer, String product, int quantity, double amount) {
        this.orderId = orderId;
        this.date = date;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getOrderId() { return orderId; }
    public String getDate() { return date; }
    public String getCustomer() { return customer; }
    public String getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getAmount() { return amount; }
}