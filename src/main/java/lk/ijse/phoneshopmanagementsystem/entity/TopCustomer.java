package lk.ijse.phoneshopmanagementsystem.entity;

public class TopCustomer {
    private String customerId;
    private String name;
    private String contact;
    private int orderCount;
    private double totalSpent;

    public TopCustomer(String customerId, String name, String contact, int orderCount, double totalSpent) {
        this.customerId = customerId;
        this.name = name;
        this.contact = contact;
        this.orderCount = orderCount;
        this.totalSpent = totalSpent;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public int getOrderCount() { return orderCount; }
    public double getTotalSpent() { return totalSpent; }
}
