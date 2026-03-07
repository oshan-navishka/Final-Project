package lk.ijse.phoneshopmanagementsystem.dao.custom;

import lk.ijse.phoneshopmanagementsystem.dao.CrudDAO;
import lk.ijse.phoneshopmanagementsystem.entity.PlaceOrder;
import net.sf.jasperreports.engine.*;

import java.sql.SQLException;

public interface OrdersDAO extends CrudDAO<PlaceOrder> {

    public boolean update(String status, String orderId) throws SQLException, ClassNotFoundException;

    public boolean cancelOrder(String orderId) throws SQLException, ClassNotFoundException;

    public String getCustomerName(String customerId) throws SQLException, ClassNotFoundException;

    public void printReports() throws SQLException, JRException, ClassNotFoundException;
}
