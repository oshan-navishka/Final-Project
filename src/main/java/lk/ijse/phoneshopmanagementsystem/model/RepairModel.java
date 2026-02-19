/*
package lk.ijse.phoneshopmanagementsystem.model;

import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairModel {

    public boolean saveRepair(RepairDTO repairDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Repair (Repair_ID, Customer_ID, Device_Type, Device_Model, Issue_Type, Status, Repair_Cost, Date_Received, Issue_Description) VALUES(?,?,?,?,?,?,?,?,?)",
                repairDTO.getRepairId(),
                repairDTO.getCustomerId(),
                repairDTO.getDeviceType(),
                repairDTO.getDeviceModel(),
                repairDTO.getIssueType(),
                repairDTO.getStatus(),
                repairDTO.getRepairCost(),
                repairDTO.getDateReceived(),
                repairDTO.getIssueDescription()
        );
    }

    public String getNextRepairCode() throws SQLException {
        String sql = "SELECT Repair_ID FROM Repair ORDER BY Repair_ID DESC LIMIT 1";
        ResultSet rst = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        if (rst.next()) {
            String lastCode = rst.getString(1);

            int number = Integer.parseInt(lastCode.substring(1));
            number++;

            return "R" + String.format("%03d", number);
        }
        return "R001";
    }


    public RepairDTO searchRepair(String repairId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Repair WHERE Repair_ID = ?",
                repairId
        );

        if (resultSet.next()) {
            RepairDTO repairDTO = new RepairDTO();
            repairDTO.setRepairId(resultSet.getString("Repair_ID"));
            repairDTO.setCustomerId(resultSet.getString("Customer_ID"));
            repairDTO.setDeviceType(resultSet.getString("Device_Type"));
            repairDTO.setDeviceModel(resultSet.getString("Device_Model"));
            repairDTO.setIssueType(resultSet.getString("Issue_Type"));
            repairDTO.setStatus(resultSet.getString("Status"));
            repairDTO.setRepairCost(resultSet.getDouble("Repair_Cost"));
            repairDTO.setDateReceived(resultSet.getString("Date_Received"));
            repairDTO.setIssueDescription(resultSet.getString("Issue_Description"));
            return repairDTO;
        }
        return null;
    }

    public boolean updateRepair(RepairDTO repairDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Repair SET Customer_ID=?, Device_Type=?, Device_Model=?, Issue_Type=?, Status=?, Repair_Cost=?, Date_Received=?, Issue_Description=? WHERE Repair_ID=?",
                repairDTO.getCustomerId(),
                repairDTO.getDeviceType(),
                repairDTO.getDeviceModel(),
                repairDTO.getIssueType(),
                repairDTO.getStatus(),
                repairDTO.getRepairCost(),
                repairDTO.getDateReceived(),
                repairDTO.getIssueDescription(),
                repairDTO.getRepairId()
        );
    }

    public boolean deleteRepair(String repairId) throws SQLException {
        return CrudUtil.execute(
                "DELETE FROM Repair WHERE Repair_ID = ?",
                repairId
        );
    }

    public ArrayList<RepairDTO> getAll() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Repair");
        ArrayList<RepairDTO> repairSet = new ArrayList<>();

        while (resultSet.next()) {
            RepairDTO repairDTO = new RepairDTO();
            repairDTO.setRepairId(resultSet.getString("Repair_ID"));
            repairDTO.setCustomerId(resultSet.getString("Customer_ID"));
            repairDTO.setDeviceType(resultSet.getString("Device_Type"));
            repairDTO.setDeviceModel(resultSet.getString("Device_Model"));
            repairDTO.setIssueType(resultSet.getString("Issue_Type"));
            repairDTO.setStatus(resultSet.getString("Status"));
            repairDTO.setRepairCost(resultSet.getDouble("Repair_Cost"));
            repairDTO.setDateReceived(resultSet.getString("Date_Received"));
            repairDTO.setIssueDescription(resultSet.getString("Issue_Description"));
            repairSet.add(repairDTO);
        }
        return repairSet;
    }

    public ArrayList<RepairDTO> searchByCustomerId(String customerId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Repair WHERE Customer_ID = ?",
                customerId
        );
        ArrayList<RepairDTO> repairSet = new ArrayList<>();

        while (resultSet.next()) {
            RepairDTO repairDTO = new RepairDTO();
            repairDTO.setRepairId(resultSet.getString("Repair_ID"));
            repairDTO.setCustomerId(resultSet.getString("Customer_ID"));
            repairDTO.setDeviceType(resultSet.getString("Device_Type"));
            repairDTO.setDeviceModel(resultSet.getString("Device_Model"));
            repairDTO.setIssueType(resultSet.getString("Issue_Type"));
            repairDTO.setStatus(resultSet.getString("Status"));
            repairDTO.setRepairCost(resultSet.getDouble("Repair_Cost"));
            repairDTO.setDateReceived(resultSet.getString("Date_Received"));
            repairDTO.setIssueDescription(resultSet.getString("Issue_Description"));
            repairSet.add(repairDTO);
        }
        return repairSet;
    }

    public ArrayList<RepairDTO> searchByStatus(String status) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Repair WHERE Status = ?",
                status
        );
        ArrayList<RepairDTO> repairSet = new ArrayList<>();

        while (resultSet.next()) {
            RepairDTO repairDTO = new RepairDTO();
            repairDTO.setRepairId(resultSet.getString("Repair_ID"));
            repairDTO.setCustomerId(resultSet.getString("Customer_ID"));
            repairDTO.setDeviceType(resultSet.getString("Device_Type"));
            repairDTO.setDeviceModel(resultSet.getString("Device_Model"));
            repairDTO.setIssueType(resultSet.getString("Issue_Type"));
            repairDTO.setStatus(resultSet.getString("Status"));
            repairDTO.setRepairCost(resultSet.getDouble("Repair_Cost"));
            repairDTO.setDateReceived(resultSet.getString("Date_Received"));
            repairDTO.setIssueDescription(resultSet.getString("Issue_Description"));
            repairSet.add(repairDTO);
        }
        return repairSet;
    }
}*/
