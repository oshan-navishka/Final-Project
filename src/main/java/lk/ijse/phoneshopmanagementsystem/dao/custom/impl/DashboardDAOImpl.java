package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.DashboardDAO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardDAOImpl implements DashboardDAO {
    public double getTodaySales() throws SQLException, ClassNotFoundException {
        LocalDate today = LocalDate.now();

       ResultSet resultSet = CrudUtil.execute("SELECT COALESCE(SUM(Total_amount), 0) as today_sales FROM Orders " +
                "WHERE DATE(Order_date) = ?",
               Date.valueOf(today)
       );

        if (resultSet.next()) {
            return resultSet.getDouble("today_sales");
        }
        return 0.0;
    }

    public int getTotalOrdersCount() throws SQLException, ClassNotFoundException {
        LocalDate today = LocalDate.now();

        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) as today_orders FROM Orders " +
                        "WHERE DATE(Order_date) = ?",
                Date.valueOf(today)
        );

        if (resultSet.next()) {
            return resultSet.getInt("today_orders");
        }
        return 0;
    }

    public int getLowStockCount() throws SQLException, ClassNotFoundException {
       ResultSet rs =  CrudUtil.execute("SELECT COUNT(*) as low_stock_count FROM Item WHERE Quantity < 10");

        if (rs.next()) {
            return rs.getInt("low_stock_count");
        }
        return 0;
    }

    public List<Map<String, Object>> getRecentOrders() throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> orders = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("SELECT o.Order_ID, o.Order_date, o.Total_amount, c.Name as customer_name FROM Orders o " +
                "JOIN Customer c ON o.Customer_ID = c.Customer_ID ORDER BY o.Order_date DESC LIMIT 5");

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

    public List<Map<String, Object>> getLast7DaysSales() throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> salesData = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        ResultSet rs = CrudUtil.execute("SELECT DATE(Order_date) as order_date, SUM(Total_amount) as daily_sales FROM Orders " +
                        "WHERE Order_date >= ? AND Order_date <= ? GROUP BY DATE(Order_date) ORDER BY order_date",
                Date.valueOf(sevenDaysAgo),
                Date.valueOf(today)
        );

        while (rs.next()) {
            Map<String, Object> data = new HashMap<>();
            data.put("date", rs.getDate("order_date"));
            data.put("sales", rs.getDouble("daily_sales"));
            salesData.add(data);
        }

        return salesData;
    }

    public List<Map<String, Object>> getTopSellingItems() throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> items = new ArrayList<>();

        ResultSet rs =  CrudUtil.execute("SELECT i.Model, i.Brand, SUM(od.Quantity) as total_sold FROM Order_Details od " +
                "JOIN Item i ON od.Item_ID = i.Item_ID GROUP BY i.Item_ID, i.Model, i.Brand ORDER BY total_sold DESC LIMIT 5"
        );

        while (rs.next()) {
            Map<String, Object> item = new HashMap<>();
            item.put("model", rs.getString("Model"));
            item.put("brand", rs.getString("Brand"));
            item.put("totalSold", rs.getInt("total_sold"));
            items.add(item);
        }

        return items;
    }

    public int getPendingRepairsCount() throws SQLException, ClassNotFoundException {
       ResultSet rs = CrudUtil.execute("SELECT COUNT(*) as pending_repairs FROM Repair WHERE Status != 'Completed'");

        if (rs.next()) {
            return rs.getInt("pending_repairs");
        }
        return 0;
    }

    @Override
    public boolean save(DashboardDAO customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<DashboardDAO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(DashboardDAO customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public DashboardDAO search(String customerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextID() throws SQLException, ClassNotFoundException {
        return "";
    }
}
