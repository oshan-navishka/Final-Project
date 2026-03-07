package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.DashboardBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.DashboardDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DashBoardBOImpl implements DashboardBO {

    DashboardDAO dashboardDAO = (DashboardDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.DASHBOARD);

    @Override
    public double getTodaySales() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getTodaySales();
    }

    @Override
    public int getTotalOrdersCount() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getTotalOrdersCount();
    }

    @Override
    public int getLowStockCount() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getLowStockCount();
    }

    @Override
    public List<Map<String, Object>> getRecentOrders() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getRecentOrders();
    }

    @Override
    public List<Map<String, Object>> getLast7DaysSales() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getLast7DaysSales();
    }

    @Override
    public List<Map<String, Object>> getTopSellingItems() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getTopSellingItems();
    }

    @Override
    public int getPendingRepairsCount() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getPendingRepairsCount();
    }
}
