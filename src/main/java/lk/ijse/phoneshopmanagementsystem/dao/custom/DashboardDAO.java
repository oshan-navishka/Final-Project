package lk.ijse.phoneshopmanagementsystem.dao.custom;

import lk.ijse.phoneshopmanagementsystem.dao.CrudDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DashboardDAO extends CrudDAO<DashboardDAO> {
    public double getTodaySales() throws SQLException, ClassNotFoundException;

    public int getTotalOrdersCount() throws SQLException, ClassNotFoundException;

    public int getLowStockCount() throws SQLException, ClassNotFoundException;

    public List<Map<String, Object>> getRecentOrders() throws SQLException, ClassNotFoundException;

    public List<Map<String, Object>> getLast7DaysSales() throws SQLException, ClassNotFoundException;

    public List<Map<String, Object>> getTopSellingItems() throws SQLException, ClassNotFoundException;

    public int getPendingRepairsCount() throws SQLException, ClassNotFoundException;
}
