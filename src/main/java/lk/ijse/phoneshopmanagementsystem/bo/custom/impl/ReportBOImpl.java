package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.ReportBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.ReportDAO;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportBOImpl implements ReportBO {

    ReportDAO reportDAO = (ReportDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.REPORT);

    public Map<String, Object> getMonthlySummary(int year, int month) throws SQLException, ClassNotFoundException {
       return reportDAO.getMonthlySummary(year, month);
    }

    public Map<String, Object> getYearlySummary(int year) throws SQLException, ClassNotFoundException {
        return reportDAO.getYearlySummary(year);
    }

   /* public List<Map<String, Object>> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException {
        return reportDAO.getMonthlyOrders(year, month);
    }*/

    /*public List<Map<String, Object>> getYearlyOrders(int year) throws SQLException, ClassNotFoundException {
        return reportDAO.getYearlyOrders(year);
    }*/

    /*public List<Map<String, Object>> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException {
        return reportDAO.getTopCustomers(year, limit);
    }*/

    public Map<String, Integer> getOrderStatusBreakdown(int year) throws SQLException, ClassNotFoundException {
        return reportDAO.getOrderStatusBreakdown(year);
    }

    public List<Integer> getAvailableYears() throws SQLException, ClassNotFoundException {
        return reportDAO.getAvailableYears();
    }

    public boolean save(RepairDTO customerDTO) throws SQLException, ClassNotFoundException {
        return reportDAO.save(customerDTO);
    }

    public ArrayList<RepairDTO> getAll() throws SQLException, ClassNotFoundException {
        return reportDAO.getAll();
    }

    public boolean update(RepairDTO customerDTO) throws SQLException, ClassNotFoundException {
        return reportDAO.update(customerDTO);
    }

    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return reportDAO.delete(customerId);
    }

    public RepairDTO search(String customerId) throws SQLException, ClassNotFoundException {
        return reportDAO.search(customerId);
    }

    public String getNextID() throws SQLException, ClassNotFoundException {
        return reportDAO.getNextID();
    }
}
