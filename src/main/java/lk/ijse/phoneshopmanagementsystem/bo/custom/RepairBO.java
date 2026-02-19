package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairBO extends SuperBO {
    public boolean saveRepair(RepairDTO repairDTO) throws SQLException, ClassNotFoundException;

    public String getNextRepairID() throws SQLException, ClassNotFoundException;

    public RepairDTO searchRepair(String repairId) throws SQLException, ClassNotFoundException;

    public boolean updateRepair(RepairDTO repairDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteRepair(String repairId) throws SQLException, ClassNotFoundException;

    public ArrayList<RepairDTO> getAllRepair() throws SQLException, ClassNotFoundException;
}
