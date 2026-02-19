package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.RepairDAO;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Repair;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairDAOImpl implements RepairDAO {
    public boolean save(Repair repairDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO repair (Repair_ID, Customer_ID, Device_Type, Device_Model, Issue_Type, Status, Repair_Cost, Date_Received) VALUES(?,?,?,?,?,?,?,?)",
                repairDTO.getRepairId(),
                repairDTO.getCustomerId(),
                repairDTO.getDeviceType(),
                repairDTO.getDeviceModel(),
                repairDTO.getIssueType(),
                repairDTO.getStatus(),
                repairDTO.getRepairCost(),
                repairDTO.getDateReceived()
        );
    }

    public String getNextID() throws SQLException, ClassNotFoundException {
       ResultSet rst = CrudUtil.execute("SELECT Repair_ID FROM Repair ORDER BY Repair_ID DESC LIMIT 1");

        if (rst.next()) {
            String lastCode = rst.getString(1);

            int number = Integer.parseInt(lastCode.substring(1));
            number++;

            return "R" + String.format("%03d", number);
        }
        return "R001";
    }

    public Repair search(String repairId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Repair WHERE Repair_ID = ?",
                repairId
        );

        if (resultSet.next()) {
            new Repair(
                    resultSet.getString("Repair_ID"),
                    resultSet.getString("Customer_ID"),
                    resultSet.getString("Device_Type"),
                    resultSet.getString("Device_Model"),
                    resultSet.getString("Issue_Type"),
                    resultSet.getString("Status"),
                    resultSet.getDouble("Repair_Cost"),
                    resultSet.getString("Date_Received")
            );

        }
        return null;
    }

    public boolean update(Repair repairDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Repair SET Customer_ID=?, Device_Type=?, Device_Model=?, Issue_Type=?, Status=?, Repair_Cost=?, Date_Received=? WHERE Repair_ID=?",
                repairDTO.getCustomerId(),
                repairDTO.getDeviceType(),
                repairDTO.getDeviceModel(),
                repairDTO.getIssueType(),
                repairDTO.getStatus(),
                repairDTO.getRepairCost(),
                repairDTO.getDateReceived(),
                repairDTO.getRepairId()
        );
    }

    public boolean delete(String repairId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM Repair WHERE Repair_ID = ?",
                repairId
        );
    }

    public ArrayList<Repair> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Repair");

        ArrayList<Repair> repairSet = new ArrayList<>();

        while (resultSet.next()) {
            Repair repair = new Repair(
                resultSet.getString("Repair_ID"),
                resultSet.getString("Customer_ID"),
                resultSet.getString("Device_Type"),
                resultSet.getString("Device_Model"),
                resultSet.getString("Issue_Type"),
                resultSet.getString("Status"),
                resultSet.getDouble("Repair_Cost"),
                resultSet.getString("Date_Received")
            );
            repairSet.add(repair);
        }
        return repairSet;
    }
}
