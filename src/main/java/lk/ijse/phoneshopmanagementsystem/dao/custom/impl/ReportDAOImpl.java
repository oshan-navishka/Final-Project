package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.ReportDAO;
import lk.ijse.phoneshopmanagementsystem.dto.RepairDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDAOImpl implements ReportDAO {

    @Override
    public Map<String, Object> getMonthlySummary(int year, int month) throws SQLException, ClassNotFoundException {
        Map<String, Object> summary = new HashMap<>();

        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) as total_orders, SUM(Total_amount) as total_revenue, " +
                "AVG(Total_amount) as average_order_value, COUNT(DISTINCT Customer_ID) as unique_customers " +
                "FROM Orders WHERE YEAR(Order_date) = ? AND MONTH(Order_date) = ?",
                year, month
        );

        if (rs.next()) {
            summary.put("totalOrders", rs.getInt("total_orders"));
            summary.put("totalRevenue", rs.getDouble("total_revenue"));
            summary.put("averageOrderValue", rs.getDouble("average_order_value"));
            summary.put("uniqueCustomers", rs.getInt("unique_customers"));
        }

        return summary;
    }

    @Override
    public Map<String, Object> getYearlySummary(int year) throws SQLException, ClassNotFoundException {
        Map<String, Object> summary = new HashMap<>();

       ResultSet rs = CrudUtil.execute("SELECT " +
                "COUNT(*) as total_orders, " +
                "SUM(Total_amount) as total_revenue, " +
                "AVG(Total_amount) as average_order_value, " +
                "COUNT(DISTINCT Customer_ID) as unique_customers " +
                "FROM Orders " +
                "WHERE YEAR(Order_date) = ?",
                year
       );

        if (rs.next()) {
            summary.put("totalOrders", rs.getInt("total_orders"));
            summary.put("totalRevenue", rs.getDouble("total_revenue"));
            summary.put("averageOrderValue", rs.getDouble("average_order_value"));
            summary.put("uniqueCustomers", rs.getInt("unique_customers"));
        }

        return summary;
    }

    @Override
    public Map<String, Integer> getOrderStatusBreakdown(int year) throws SQLException, ClassNotFoundException {
        Map<String, Integer> statusCount = new HashMap<>();

       ResultSet rs = CrudUtil.execute("SELECT Order_status, COUNT(*) as count " +
                "FROM Orders " +
                "WHERE YEAR(Order_date) = ? " +
                "GROUP BY Order_status",
                year
       );

        while (rs.next()) {
            statusCount.put(rs.getString("Order_status"), rs.getInt("count"));
        }

        return statusCount;
    }

    @Override
    public List<Integer> getAvailableYears() throws SQLException, ClassNotFoundException {
        List<Integer> years = new ArrayList<>();

       ResultSet rs = CrudUtil.execute("SELECT DISTINCT YEAR(Order_date) as year " +
                "FROM Orders " +
                "ORDER BY year DESC"
        );

        while (rs.next()) {
            years.add(rs.getInt("year"));
        }

        return years;
    }

    @Override
    public boolean save(RepairDTO customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<RepairDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(RepairDTO customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public RepairDTO search(String customerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextID() throws SQLException, ClassNotFoundException {
        return "";
    }
}
