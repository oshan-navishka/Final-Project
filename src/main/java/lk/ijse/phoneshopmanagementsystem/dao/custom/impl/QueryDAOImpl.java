package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.QueryDAO;
import lk.ijse.phoneshopmanagementsystem.entity.MonthlyOrders;
import lk.ijse.phoneshopmanagementsystem.entity.OrderDetails;
import lk.ijse.phoneshopmanagementsystem.entity.TopCustomer;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<OrderDetails> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT od.*, i.Description, i.Brand, i.Model " +
                        "FROM Order_Details od " +
                        "JOIN Item i ON od.Item_ID = i.Item_ID " +
                        "WHERE od.Order_ID = ?", orderId);

        ArrayList<OrderDetails> detailList = new ArrayList<>();
        while (rs.next()) {
            OrderDetails detail = new OrderDetails(
                    rs.getString("Order_Detail_ID"),
                    rs.getString("Order_ID"),
                    rs.getString("Item_ID"),
                    rs.getInt("Quantity"),
                    rs.getDouble("Unit_price"),
                    rs.getDouble("Sub_total"),
                    rs.getString("Description"),
                    rs.getString("Brand"),
                    rs.getString("Model")
            );
            detailList.add(detail);
        }
        return detailList;
    }

    @Override
    public List<MonthlyOrders> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException {
        List<MonthlyOrders> orders = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
                "SELECT o.Order_ID, o.Order_date, o.Total_amount, c.Name AS customer_name " +
                        "FROM Orders o " +
                        "JOIN Customer c ON o.Customer_ID = c.Customer_ID " +
                        "WHERE YEAR(o.Order_date) = ? AND MONTH(o.Order_date) = ? " +
                        "ORDER BY o.Order_date DESC",
                year, month
        );

        while (rs.next()) {
            orders.add(new MonthlyOrders(
                    rs.getString("Order_ID"),
                    rs.getDate("Order_date"),
                    rs.getString("customer_name"),
                    rs.getDouble("Total_amount")
            ));
        }

        return orders;
    }

    @Override
    public List<MonthlyOrders> getYearlyOrders(int year) throws SQLException, ClassNotFoundException {
        List<MonthlyOrders> orders = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
                "SELECT o.Order_ID, o.Order_date, o.Total_amount, c.Name AS customer_name " +
                        "FROM Orders o " +
                        "JOIN Customer c ON o.Customer_ID = c.Customer_ID " +
                        "WHERE YEAR(o.Order_date) = ? " +
                        "ORDER BY o.Order_date DESC",
                year
        );

        while (rs.next()) {
            orders.add(new MonthlyOrders(
                    rs.getString("Order_ID"),
                    rs.getDate("Order_date"),
                    rs.getString("customer_name"),
                    rs.getDouble("Total_amount")
            ));
        }

        return orders;
    }

    @Override
    public List<TopCustomer> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException {
        List<TopCustomer> topCustomers = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
                "SELECT c.Customer_ID, c.Name, c.Contact, " +
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
            topCustomers.add(new TopCustomer(
                    rs.getString("Customer_ID"),
                    rs.getString("Name"),
                    rs.getString("Contact"),
                    rs.getInt("order_count"),
                    rs.getDouble("total_spent")
            ));
        }

        return topCustomers;
    }
}
