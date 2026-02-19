package lk.ijse.phoneshopmanagementsystem.entity;

public class OrderDetails {
    private String orderDetailId;
    private String orderId;
    private String itemId;
    private int quantity;
    private double unitPrice;
    private double subtotal;
    private String itemDescription;
    private String itemBrand;
    private String itemModel;

    public OrderDetails() {}

    public OrderDetails(String orderId, String itemId, int quantity,
                          double unitPrice, double subtotal) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public OrderDetails(String orderDetailId, String orderId, String itemId, int quantity, double unitPrice,
                        double subTotal, String description, String brand, String model) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subTotal;
        this.itemDescription = description;
        this.itemBrand = brand;
        this.itemModel = model;
    }

    public String getOrderDetailId() { return orderDetailId; }
    public String getOrderId() { return orderId; }
    public String getItemId() { return itemId; }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getOrderDetailIdAsInt() {
        if (orderDetailId != null && orderDetailId.startsWith("OD")) {
            try {
                return Integer.parseInt(orderDetailId.substring(2));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
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

    public void setOrderDetailId(int id) {
        this.orderDetailId = String.format("OD%03d", id);
    }

    public void setOrderId(int id) {
        this.orderId = String.format("O%03d", id);
    }

    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getSubtotal() { return subtotal; }
    public String getItemDescription() { return itemDescription; }
    public String getItemBrand() { return itemBrand; }
    public String getItemModel() { return itemModel; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderDetailId='" + orderDetailId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + subtotal +
                '}';
    }
}
