package lk.ijse.phoneshopmanagementsystem.dao.custom;

import lk.ijse.phoneshopmanagementsystem.dao.CrudDAO;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ReportDAO extends CrudDAO<RepairDTO> {
    public Map<String, Object> getMonthlySummary(int year, int month) throws SQLException, ClassNotFoundException;

    public Map<String, Object> getYearlySummary(int year) throws SQLException, ClassNotFoundException;

//    public List<Map<String, Object>> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException;

/*
    public List<Map<String, Object>> getYearlyOrders(int year) throws SQLException, ClassNotFoundException;
*/

//    public List<Map<String, Object>> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException;

    public Map<String, Integer> getOrderStatusBreakdown(int year) throws SQLException, ClassNotFoundException;

    public List<Integer> getAvailableYears() throws SQLException, ClassNotFoundException;
}
