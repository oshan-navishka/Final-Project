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

    /*public List<Map<String, Object>> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> orders = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT " +
                "o.Order_ID, o.Order_date, o.Total_amount, " +
                "c.Name as customer_name " +
                "FROM Orders o " +
                "JOIN Customer c ON o.Customer_ID = c.Customer_ID " +
                "WHERE YEAR(o.Order_date) = ? AND MONTH(o.Order_date) = ? " +
                "ORDER BY o.Order_date DESC",
                year, month
        );

        while (rs.next()) {
            Map<String, Object> order = new HashMap<>();
            order.put("orderId", rs.getString("Order_ID"));
            order.put("orderDate", rs.getDate("Order_date"));
            order.put("customerName", rs.getString("customer_name"));
            order.put("totalAmount", rs.getDouble("Total_amount"));
            orders.add(order);
        }

        return orders;
    }*/

   /* public List<Map<String, Object>> getYearlyOrders(int year) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> orders = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT " +
                "o.Order_ID, o.Order_date, o.Total_amount, " +
                "c.Name as customer_name " +
                "FROM Orders o " +
                "JOIN Customer c ON o.Customer_ID = c.Customer_ID " +
                "WHERE YEAR(o.Order_date) = ? " +
                "ORDER BY o.Order_date DESC",
                year
        );

        while (rs.next()) {
            Map<String, Object> order = new HashMap<>();
            order.put("orderId", rs.getString("Order_ID"));
            order.put("orderDate", rs.getDate("Order_date"));
            order.put("customerName", rs.getString("customer_name"));
            order.put("totalAmount", rs.getDouble("Total_amount"));
            orders.add(order);
        }

        return orders;
    }*/

    /*public List<Map<String, Object>> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> topCustomers = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT " +
                "c.Customer_ID, c.Name, c.Contact, " +
                "COUNT(o.Order_ID) as order_count, " +
                "SUM(o.Total_amount) as total_spent " +
                "FROM Customer c " +
                "JOIN Orders o ON c.Customer_ID = o.Customer_ID " +
                "WHERE YEAR(o.Order_date) = ? " +
                "GROUP BY c.Customer_ID, c.Name, c.Contact " +
                "ORDER BY total_spent DESC " +
                "LIMIT ?",
                year, limit
        );

        while (rs.next()) {
            Map<String, Object> customer = new HashMap<>();
            customer.put("customerId", rs.getString("Customer_ID"));
            customer.put("name", rs.getString("Name"));
            customer.put("contact", rs.getString("Contact"));
            customer.put("orderCount", rs.getInt("order_count"));
            customer.put("totalSpent", rs.getDouble("total_spent"));
            topCustomers.add(customer);
        }

        return topCustomers;
    }*/

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
