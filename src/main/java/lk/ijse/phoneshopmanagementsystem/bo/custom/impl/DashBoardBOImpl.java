package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.DashboardBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.DashboardDAO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashBoardBOImpl implements DashboardBO {

    DashboardDAO dashboardDAO = (DashboardDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.DASHBOARD);

    public double getTodaySales() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getTodaySales();
    }

    public int getTotalOrdersCount() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getTotalOrdersCount();
    }

    public int getLowStockCount() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getLowStockCount();
    }

    public List<Map<String, Object>> getRecentOrders() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getRecentOrders();
    }

    public List<Map<String, Object>> getLast7DaysSales() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getLast7DaysSales();
    }

    public List<Map<String, Object>> getTopSellingItems() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getTopSellingItems();
    }

    public int getPendingRepairsCount() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getPendingRepairsCount();
    }
}
