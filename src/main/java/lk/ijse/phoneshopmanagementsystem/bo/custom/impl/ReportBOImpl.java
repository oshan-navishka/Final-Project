package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.ReportBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.ReportDAO;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportBOImpl implements ReportBO {

    ReportDAO reportDAO = (ReportDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.REPORT);

    @Override
    public Map<String, Object> getMonthlySummary(int year, int month) throws SQLException, ClassNotFoundException {
       return reportDAO.getMonthlySummary(year, month);
    }

    @Override
    public Map<String, Object> getYearlySummary(int year) throws SQLException, ClassNotFoundException {
        return reportDAO.getYearlySummary(year);
    }

    @Override
    public Map<String, Integer> getOrderStatusBreakdown(int year) throws SQLException, ClassNotFoundException {
        return reportDAO.getOrderStatusBreakdown(year);
    }

    @Override
    public List<Integer> getAvailableYears() throws SQLException, ClassNotFoundException {
        return reportDAO.getAvailableYears();
    }

    @Override
    public boolean save(RepairDTO customerDTO) throws SQLException, ClassNotFoundException {
        return reportDAO.save(customerDTO);
    }

    @Override
    public ArrayList<RepairDTO> getAll() throws SQLException, ClassNotFoundException {
        return reportDAO.getAll();
    }

    @Override
    public boolean update(RepairDTO customerDTO) throws SQLException, ClassNotFoundException {
        return reportDAO.update(customerDTO);
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return reportDAO.delete(customerId);
    }

    @Override
    public RepairDTO search(String customerId) throws SQLException, ClassNotFoundException {
        return reportDAO.search(customerId);
    }

    @Override
    public String getNextID() throws SQLException, ClassNotFoundException {
        return reportDAO.getNextID();
    }
}
