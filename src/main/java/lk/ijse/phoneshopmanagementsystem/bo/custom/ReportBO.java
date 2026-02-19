package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.ReportDAO;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ReportBO extends SuperBO{

    public Map<String, Object> getMonthlySummary(int year, int month) throws SQLException, ClassNotFoundException;

    public Map<String, Object> getYearlySummary(int year) throws SQLException, ClassNotFoundException;

//    public List<Map<String, Object>> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException;

//    public List<Map<String, Object>> getYearlyOrders(int year) throws SQLException, ClassNotFoundException;

//    public List<Map<String, Object>> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException;

    public Map<String, Integer> getOrderStatusBreakdown(int year) throws SQLException, ClassNotFoundException;

    public List<Integer> getAvailableYears() throws SQLException, ClassNotFoundException;

    public boolean save(RepairDTO customerDTO) throws SQLException, ClassNotFoundException;

    public ArrayList<RepairDTO> getAll() throws SQLException, ClassNotFoundException;

    public boolean update(RepairDTO customerDTO) throws SQLException, ClassNotFoundException;

    public boolean delete(String customerId) throws SQLException, ClassNotFoundException;

    public RepairDTO search(String customerId) throws SQLException, ClassNotFoundException;

    public String getNextID() throws SQLException, ClassNotFoundException;
}
