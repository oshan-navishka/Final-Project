package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
import lk.ijse.phoneshopmanagementsystem.dto.PlaceOrderDTO;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    public boolean saveOrderDetail(OrderDetailDTO customerDTO) throws SQLException, ClassNotFoundException;

    public ArrayList<OrderDetailDTO> getAllOrderDetail() throws SQLException, ClassNotFoundException;

    public boolean updateOrderDetail(OrderDetailDTO customerDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteOrderDetail(String customerId) throws SQLException, ClassNotFoundException;

    public OrderDetailDTO searchOrderDetail(String customerId) throws SQLException, ClassNotFoundException;

    public String getNextOrderDetailID() throws SQLException, ClassNotFoundException;

    public ArrayList<OrderDetailDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;

    public ArrayList<PlaceOrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;

    public boolean updateOrders(PlaceOrderDTO customerDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteOrders(String customerId) throws SQLException, ClassNotFoundException;

    public String getNextOrderID() throws SQLException, ClassNotFoundException;

    public boolean saveOrders(PlaceOrderDTO orderDTO) throws SQLException, ClassNotFoundException;

    public PlaceOrderDTO searchOrders(String orderId) throws SQLException, ClassNotFoundException;

    public boolean updateOrders(String status, String orderId) throws SQLException, ClassNotFoundException;

    public boolean cancelOrder(String orderId) throws SQLException, ClassNotFoundException;

    public String getCustomerName(String customerId) throws SQLException, ClassNotFoundException;

    public void printOrdersReports() throws SQLException, JRException, ClassNotFoundException;
}
