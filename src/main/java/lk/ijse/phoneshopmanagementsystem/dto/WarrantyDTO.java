package lk.ijse.phoneshopmanagementsystem.dto;

import java.time.LocalDate;

public class WarrantyDTO {
    private String warrantyId;
    private String orderId;
    private String itemCode;
    private String customerId;
    private LocalDate purchaseDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int warrantyPeriod;
    private String warrantyType;
    private String status;
    private String coverageDetails;
    private String claimId;

    public WarrantyDTO() {
    }

    public WarrantyDTO(String warrantyId, String orderId, String itemCode, String customerId,
                       LocalDate purchaseDate, LocalDate startDate, LocalDate endDate,
                       int warrantyPeriod, String warrantyType, String status,
                       String coverageDetails, String claimId) {
        this.warrantyId = warrantyId;
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.customerId = customerId;
        this.purchaseDate = purchaseDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.warrantyPeriod = warrantyPeriod;
        this.warrantyType = warrantyType;
        this.status = status;
        this.coverageDetails = coverageDetails;
        this.claimId = claimId;
    }

    public String getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(String warrantyId) {
        this.warrantyId = warrantyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoverageDetails() {
        return coverageDetails;
    }

    public void setCoverageDetails(String coverageDetails) {
        this.coverageDetails = coverageDetails;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    @Override
    public String toString() {
        return "WarrantyDTO{" +
                "warrantyId='" + warrantyId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", customerId='" + customerId + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", warrantyPeriod=" + warrantyPeriod +
                ", warrantyType='" + warrantyType + '\'' +
                ", status='" + status + '\'' +
                ", coverageDetails='" + coverageDetails + '\'' +
                ", claimId='" + claimId + '\'' +
                '}';
    }
}