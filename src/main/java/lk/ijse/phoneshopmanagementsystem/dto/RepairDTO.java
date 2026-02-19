package lk.ijse.phoneshopmanagementsystem.dto;

public class RepairDTO {
    private String repairId;
    private String customerId;
    private String deviceType;
    private String deviceModel;
    private String issueType;
    private String status;
    private double repairCost;
    private String dateReceived;

    public RepairDTO() {
    }

    public RepairDTO(String repairId, String customerId, String deviceType, String deviceModel, String issueType, String status, double repairCost, String dateReceived) {
        this.repairId = repairId;
        this.customerId = customerId;
        this.deviceType = deviceType;
        this.deviceModel = deviceModel;
        this.issueType = issueType;
        this.status = status;
        this.repairCost = repairCost;
        this.dateReceived = dateReceived;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }


    @Override
    public String toString() {
        return "RepairDTO{" +
                "repairId='" + repairId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", issueType='" + issueType + '\'' +
                ", status='" + status + '\'' +
                ", repairCost=" + repairCost +
                ", dateReceived='" + dateReceived + '\'' +
                '}';
    }
}