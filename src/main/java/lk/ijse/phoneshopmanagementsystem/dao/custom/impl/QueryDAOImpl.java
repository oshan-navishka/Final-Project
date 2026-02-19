package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.QueryDAO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDAOImpl implements QueryDAO {
    public List<Map<String, Object>> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException {
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
    }

    public List<Map<String, Object>> getYearlyOrders(int year) throws SQLException, ClassNotFoundException {
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
    }

    public List<Map<String, Object>> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException {
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
    }
}
