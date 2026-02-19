package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface QueryBO extends SuperBO {
    public List<Map<String, Object>> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException;

    public List<Map<String, Object>> getYearlyOrders(int year) throws SQLException, ClassNotFoundException;

    public List<Map<String, Object>> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException;


}
