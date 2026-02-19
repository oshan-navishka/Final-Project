package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.RepairBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.RepairDAO;
import lk.ijse.phoneshopmanagementsystem.dto.ItemDTO;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Item;
import lk.ijse.phoneshopmanagementsystem.entity.Repair;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepairBOImpl implements RepairBO {
    RepairDAO repairDAO = (RepairDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.REPAIR);

    public boolean saveRepair(RepairDTO repairDTO) throws SQLException, ClassNotFoundException {
        return repairDAO.save(
                new Repair(
                        repairDTO.getRepairId(),
                        repairDTO.getCustomerId(),
                        repairDTO.getDeviceType(),
                        repairDTO.getDeviceModel(),
                        repairDTO.getIssueType(),
                        repairDTO.getStatus(),
                        repairDTO.getRepairCost(),
                        repairDTO.getDateReceived()
                )
        );
    }

    public String getNextRepairID() throws SQLException, ClassNotFoundException {
        return repairDAO.getNextID();
    }

    public RepairDTO searchRepair(String repairId) throws SQLException, ClassNotFoundException {
        Repair repair = repairDAO.search(repairId);

        if (repair == null) return null;

        return new RepairDTO(
                repair.getRepairId(),
                repair.getCustomerId(),
                repair.getDeviceType(),
                repair.getDeviceModel(),
                repair.getIssueType(),
                repair.getStatus(),
                repair.getRepairCost(),
                repair.getDateReceived()

        );

    }

    public boolean updateRepair(RepairDTO repairDTO) throws SQLException, ClassNotFoundException {
        return repairDAO.update(
                new Repair(
                        repairDTO.getRepairId(),
                        repairDTO.getCustomerId(),
                        repairDTO.getDeviceType(),
                        repairDTO.getDeviceModel(),
                        repairDTO.getIssueType(),
                        repairDTO.getStatus(),
                        repairDTO.getRepairCost(),
                        repairDTO.getDateReceived()
                )
        );
    }

    public boolean deleteRepair(String repairId) throws SQLException, ClassNotFoundException {
        return repairDAO.delete(repairId);
    }

    public ArrayList<RepairDTO> getAllRepair() throws SQLException, ClassNotFoundException {
        ArrayList<Repair> repairList = repairDAO.getAll();
        ArrayList<RepairDTO> dtoList = new ArrayList<>();

        for (Repair repair : repairList) {
            dtoList.add(
                    new RepairDTO(
                            repair.getRepairId(),
                            repair.getCustomerId(),
                            repair.getDeviceType(),
                            repair.getDeviceModel(),
                            repair.getIssueType(),
                            repair.getStatus(),
                            repair.getRepairCost(),
                            repair.getDateReceived()
                    )
            );
        }

        return dtoList;
    }
}
