package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.QueryBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.QueryDAO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBOImpl implements QueryBO {

    QueryDAO queryDAO = (QueryDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.QUERY);

    public List<Map<String, Object>> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException {
        return queryDAO.getMonthlyOrders(year, month);
    }

    public List<Map<String, Object>> getYearlyOrders(int year) throws SQLException, ClassNotFoundException {
        return queryDAO.getYearlyOrders(year);
    }

    public List<Map<String, Object>> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException {
        return queryDAO.getTopCustomers(year, limit);
    }


}
