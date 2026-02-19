/*
package lk.ijse.phoneshopmanagementsystem.model;

import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardModel {

    public double getTodaySales() throws SQLException {
        LocalDate today = LocalDate.now();

        String sql = "SELECT COALESCE(SUM(Total_amount), 0) as today_sales FROM Orders WHERE DATE(Order_date) = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(today));
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return rs.getDouble("today_sales");
        }
        return 0.0;
    }

    public int getTotalOrdersCount() throws SQLException {
        String sql = "SELECT COUNT(*) as total_orders FROM Orders";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();1

        if (rs.next()) {
            return rs.getInt("total_orders");
        }
        return 0;
    }

    public int getTodayOrdersCount() throws SQLException {
        LocalDate today = LocalDate.now();

        String sql = "SELECT COUNT(*) as today_orders FROM Orders WHERE DATE(Order_date) = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(today));

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return rs.getInt("today_orders");
        }
        return 0;
    }

    public int getLowStockCount() throws SQLException {
        String sql = "SELECT COUNT(*) as low_stock_count FROM Item WHERE Quantity < 10";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return rs.getInt("low_stock_count");
        }
        return 0;
    }

    public int getPendingRepairsCount() throws SQLException {
        String sql = "SELECT COUNT(*) as pending_repairs FROM Repair WHERE Status != 'Completed'";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return rs.getInt("pending_repairs");
        }
        return 0;
    }

    public List<Map<String, Object>> getRecentOrders() throws SQLException {
        List<Map<String, Object>> orders = new ArrayList<>();

        String sql = "SELECT o.Order_ID, o.Order_date, o.Total_amount, " +
                "c.Name as customer_name " +
                "FROM Orders o " +
                "JOIN Customer c ON o.Customer_ID = c.Customer_ID " +
                "ORDER BY o.Order_date DESC " +
                "LIMIT 5";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            Map<String, Object> order = new HashMap<>();
            order.put("orderId", rs.getString("Order_ID"));
            order.put("orderDate", rs.getDate("Order_date"));
            order.put("totalAmount", rs.getDouble("Total_amount"));
            order.put("customerName", rs.getString("customer_name"));
            orders.add(order);
        }

        return orders;
    }

    public List<Map<String, Object>> getLast7DaysSales() throws SQLException {
        List<Map<String, Object>> salesData = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        String sql = "SELECT DATE(Order_date) as order_date, SUM(Total_amount) as daily_sales FROM Orders WHERE Order_date >= ? AND Order_date <= ? GROUP BY DATE(Order_date) ORDER BY order_date";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(sevenDaysAgo));
        pstm.setDate(2, Date.valueOf(today));

        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            Map<String, Object> data = new HashMap<>();
            data.put("date", rs.getDate("order_date"));
            data.put("sales", rs.getDouble("daily_sales"));
            salesData.add(data);
        }

        return salesData;
    }

    public List<Map<String, Object>> getTopSellingItems() throws SQLException {
        List<Map<String, Object>> items = new ArrayList<>();

        String sql = "SELECT i.Model, i.Brand, SUM(od.Quantity) as total_sold FROM Order_Details od JOIN Item i ON od.Item_ID = i.Item_ID GROUP BY i.Item_ID, i.Model, i.Brand ORDER BY total_sold DESC LIMIT 5";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            Map<String, Object> item = new HashMap<>();
            item.put("model", rs.getString("Model"));
            item.put("brand", rs.getString("Brand"));
            item.put("totalSold", rs.getInt("total_sold"));
            items.add(item);
        }

        return items;
    }

    public double getMonthlyRevenue() throws SQLException {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        String sql = "SELECT COALESCE(SUM(Total_amount), 0) as monthly_revenue " +
                "FROM Orders " +
                "WHERE YEAR(Order_date) = ? AND MONTH(Order_date) = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, year);
        pstm.setInt(2, month);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return rs.getDouble("monthly_revenue");
        }
        return 0.0;
    }

    public int getTotalCustomersCount() throws SQLException {
        String sql = "SELECT COUNT(*) as total_customers FROM Customer";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return rs.getInt("total_customers");
        }
        return 0;
    }
}*/
