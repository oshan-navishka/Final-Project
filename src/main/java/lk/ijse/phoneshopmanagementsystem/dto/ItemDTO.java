package lk.ijse.phoneshopmanagementsystem.dto;

import java.util.ArrayList;
import java.util.Date;

public class ItemDTO {
    private String itemCode;
    private String description;
    private String brand;
    private String model;
    private double unitPrice;
    private int qtyOnHand;
    private String category;
    private String supplierId;

    public ItemDTO() {
    }

    public ItemDTO(String itemCode, String description, String brand, String model,double unitPrice, int qtyOnHand, String category, String supplierId) {
        this.itemCode = itemCode;
        this.description = description;
        this.brand = brand;
        this.model = model;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.category = category;
        this.supplierId = supplierId;
    }

    public ItemDTO(String orderId, Date orderDate, double totalAmount, String customerId, Integer employeeId, String orderStatus, ArrayList<OrderDetailDTO> orderDetails) {
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", category='" + category + '\'' +
                ", supplierId='" + supplierId + '\'' +
                '}';
    }
}