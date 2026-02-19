package lk.ijse.phoneshopmanagementsystem.dao.custom;

import lk.ijse.phoneshopmanagementsystem.dao.CrudDAO;
import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDTO;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
import lk.ijse.phoneshopmanagementsystem.dto.PlaceOrderDTO;
import lk.ijse.phoneshopmanagementsystem.entity.PlaceOrder;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersDAO extends CrudDAO<PlaceOrder> {

    public boolean update(String status, String orderId) throws SQLException, ClassNotFoundException;

    public boolean cancelOrder(String orderId) throws SQLException, ClassNotFoundException;

    public String getCustomerName(String customerId) throws SQLException, ClassNotFoundException;

    public void printReports() throws SQLException, JRException, ClassNotFoundException;
}
