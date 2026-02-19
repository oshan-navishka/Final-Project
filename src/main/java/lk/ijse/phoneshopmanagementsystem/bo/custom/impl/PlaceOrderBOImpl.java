package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.PlaceOrderBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.ItemDAO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.OrderDetailDAO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.OrdersDAO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.ItemDTO;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
import lk.ijse.phoneshopmanagementsystem.dto.PlaceOrderDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Item;
import lk.ijse.phoneshopmanagementsystem.entity.OrderDetails;
import lk.ijse.phoneshopmanagementsystem.entity.PlaceOrder;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDetailDAOImpl od = new OrderDetailDAOImpl();

    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.ORDER_DETAIL);
    OrdersDAO ordersDAO = (OrdersDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.ORDERS);

    public boolean saveOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.save(
                new OrderDetails(
                        orderDetailDTO.getOrderDetailId(),
                        orderDetailDTO.getOrderId(),
                        orderDetailDTO.getItemId(),
                        orderDetailDTO.getQuantity(),
                        orderDetailDTO.getUnitPrice(),
                        orderDetailDTO.getSubtotal(),
                        orderDetailDTO.getItemDescription(),
                        orderDetailDTO.getItemBrand(),
                        orderDetailDTO.getItemModel()
                )
        );
    }

    public ArrayList<OrderDetailDTO> getAllOrderDetail() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
        ArrayList<OrderDetailDTO> dtoList = new ArrayList<>();

        for (OrderDetails orderDetail : orderDetailsList) {
            dtoList.add(
                    new OrderDetailDTO(
                            orderDetail.getOrderDetailId(),
                            orderDetail.getOrderId(),
                            orderDetail.getItemId(),
                            orderDetail.getQuantity(),
                            orderDetail.getUnitPrice(),
                            orderDetail.getSubtotal(),
                            orderDetail.getItemDescription(),
                            orderDetail.getItemBrand(),
                            orderDetail.getItemModel()
                    )
            );
        }
        return dtoList;
    }

    public boolean updateOrderDetail(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.update(
                new OrderDetails(
                        orderDetailDTO.getOrderDetailId(),
                        orderDetailDTO.getOrderId(),
                        orderDetailDTO.getItemId(),
                        orderDetailDTO.getQuantity(),
                        orderDetailDTO.getUnitPrice(),
                        orderDetailDTO.getSubtotal(),
                        orderDetailDTO.getItemDescription(),
                        orderDetailDTO.getItemBrand(),
                        orderDetailDTO.getItemModel()
                )
        );
    }

    public boolean deleteOrderDetail(String customerId) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.delete(customerId);
    }

    public OrderDetailDTO searchOrderDetail(String customerId) throws SQLException, ClassNotFoundException {
        OrderDetails orderDetails = orderDetailDAO.search(customerId);
        if (orderDetails == null) return null;
        return new OrderDetailDTO(
                orderDetails.getOrderDetailId(),
                orderDetails.getOrderId(),
                orderDetails.getItemId(),
                orderDetails.getQuantity(),
                orderDetails.getUnitPrice(),
                orderDetails.getSubtotal(),
                orderDetails.getItemDescription(),
                orderDetails.getItemBrand(),
                orderDetails.getItemModel()
        );
    }

    public String getNextOrderDetailID() throws SQLException, ClassNotFoundException {
        return orderDetailDAO.getNextID();
    }

    @Override
    public ArrayList<OrderDetailDTO> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> orderDetailsList = new ArrayList<>();
        ArrayList<OrderDetailDTO> dtoList = new ArrayList<>();

        for (OrderDetails orderDetail : orderDetailsList) {
            dtoList.add(new OrderDetailDTO(
                    orderDetail.getOrderDetailId(),
                    orderDetail.getOrderId(),
                    orderDetail.getItemId(),
                    orderDetail.getQuantity(),
                    orderDetail.getUnitPrice(),
                    orderDetail.getSubtotal(),
                    orderDetail.getItemDescription(),
                    orderDetail.getItemBrand(),
                    orderDetail.getItemModel()
            ));
        }
        return dtoList;
    }



    public ArrayList<PlaceOrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<PlaceOrder> orderList = ordersDAO.getAll();
        ArrayList<PlaceOrderDTO> dtoList = new ArrayList<>();

        for (PlaceOrder placeOrder : orderList) {

            ArrayList<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
            if (placeOrder.getDetails() != null) {
                for (OrderDetails detail : placeOrder.getDetails()) {
                    orderDetailDTOs.add(new OrderDetailDTO(
                            detail.getOrderDetailId(),
                            detail.getOrderId(),
                            detail.getItemId(),
                            detail.getQuantity(),
                            detail.getUnitPrice(),
                            detail.getSubtotal(),
                            detail.getItemDescription(),
                            detail.getItemBrand(),
                            detail.getItemModel()
                    ));
                }
            }

            dtoList.add(
                    new PlaceOrderDTO(
                            placeOrder.getOrderId(),
                            placeOrder.getOrderDate(),
                            placeOrder.getTotalAmount(),
                            placeOrder.getCustomerId(),
                            placeOrder.getEmployeeId(),
                            placeOrder.getOrderStatus(),
                            orderDetailDTOs
                    )
            );
        }

        return dtoList;
    }


    public boolean updateOrders(PlaceOrderDTO placeOrderDTO) throws SQLException, ClassNotFoundException {

        ArrayList<OrderDetails> entityDetails = new ArrayList<>();

        if (placeOrderDTO.getOrderDetails() != null) {
            for (OrderDetailDTO dto : placeOrderDTO.getOrderDetails()) {
                entityDetails.add(new OrderDetails(
                        dto.getOrderDetailId(),
                        dto.getOrderId(),
                        dto.getItemId(),
                        dto.getQuantity(),
                        dto.getUnitPrice(),
                        dto.getSubtotal(),
                        dto.getItemDescription(),
                        dto.getItemBrand(),
                        dto.getItemModel()
                ));
            }
        }

        return ordersDAO.update(
                new PlaceOrder(
                        placeOrderDTO.getOrderId(),
                        placeOrderDTO.getOrderDate(),
                        placeOrderDTO.getTotalAmount(),
                        placeOrderDTO.getCustomerId(),
                        placeOrderDTO.getEmployeeId(),
                        placeOrderDTO.getOrderStatus(),
                        entityDetails
                )
        );
    }


    public boolean deleteOrders(String customerId) throws SQLException, ClassNotFoundException {
        return ordersDAO.delete(customerId);
    }

    public String getNextOrderID() throws SQLException, ClassNotFoundException {
        return ordersDAO.getNextID();
    }

    public boolean saveOrders(PlaceOrderDTO placeOrderDTO)
            throws SQLException, ClassNotFoundException {

        ArrayList<OrderDetails> entityList = new ArrayList<>();

        for (OrderDetailDTO dto : placeOrderDTO.getOrderDetails()) {
            entityList.add(
                    new OrderDetails(
                            dto.getOrderDetailId(),
                            dto.getOrderId(),
                            dto.getItemId(),
                            dto.getQuantity(),
                            dto.getUnitPrice(),
                            dto.getSubtotal(),
                            dto.getItemDescription(),
                            dto.getItemBrand(),
                            dto.getItemModel()
                    )
            );
        }

        PlaceOrder order = new PlaceOrder(
                placeOrderDTO.getOrderId(),
                placeOrderDTO.getOrderDate(),
                placeOrderDTO.getTotalAmount(),
                placeOrderDTO.getCustomerId(),
                placeOrderDTO.getEmployeeId(),
                placeOrderDTO.getOrderStatus(),
                entityList
        );

        return ordersDAO.save(order);
    }


    public PlaceOrderDTO searchOrders(String orderId)
            throws SQLException, ClassNotFoundException {

        PlaceOrder placeOrder = ordersDAO.search(orderId);

        if (placeOrder == null) return null;

        ArrayList<OrderDetailDTO> dtoList = new ArrayList<>();

        if (placeOrder.getDetails() != null) {
            for (OrderDetails detail : placeOrder.getDetails()) {
                dtoList.add(new OrderDetailDTO(
                        detail.getOrderDetailId(),
                        detail.getOrderId(),
                        detail.getItemId(),
                        detail.getQuantity(),
                        detail.getUnitPrice(),
                        detail.getSubtotal(),
                        detail.getItemDescription(),
                        detail.getItemBrand(),
                        detail.getItemModel()
                ));
            }
        }

        return new PlaceOrderDTO(
                placeOrder.getOrderId(),
                placeOrder.getOrderDate(),
                placeOrder.getTotalAmount(),
                placeOrder.getCustomerId(),
                placeOrder.getEmployeeId(),
                placeOrder.getOrderStatus(),
                dtoList
        );
    }


    public boolean updateOrders(String status, String orderId) throws SQLException, ClassNotFoundException {
        return ordersDAO.update(status, orderId);
    }

    public boolean cancelOrder(String orderId) throws SQLException, ClassNotFoundException {
        return ordersDAO.cancelOrder(orderId);
    }

    public String getCustomerName(String customerId) throws SQLException, ClassNotFoundException {
        return ordersDAO.getCustomerName(customerId);
    }

    public void printOrdersReports() throws SQLException, JRException, ClassNotFoundException {
        ordersDAO.printReports();
    }
}
