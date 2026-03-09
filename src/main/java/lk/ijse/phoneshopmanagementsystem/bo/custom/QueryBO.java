package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
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

public interface QueryBO extends SuperBO {

    public ArrayList<OrderDetailDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;

    public List<MonthlyOrders> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException;

    public List<MonthlyOrders> getYearlyOrders(int year) throws SQLException, ClassNotFoundException;

    public List<TopCustomer> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException;


}
