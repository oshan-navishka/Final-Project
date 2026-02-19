package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DashboardBO extends SuperBO {
    public double getTodaySales() throws SQLException, ClassNotFoundException;

    public int getTotalOrdersCount() throws SQLException, ClassNotFoundException;

    public int getLowStockCount() throws SQLException, ClassNotFoundException;

    public List<Map<String, Object>> getRecentOrders() throws SQLException, ClassNotFoundException;

    public List<Map<String, Object>> getLast7DaysSales() throws SQLException, ClassNotFoundException;

    public List<Map<String, Object>> getTopSellingItems() throws SQLException, ClassNotFoundException;

    public int getPendingRepairsCount() throws SQLException, ClassNotFoundException;
}
