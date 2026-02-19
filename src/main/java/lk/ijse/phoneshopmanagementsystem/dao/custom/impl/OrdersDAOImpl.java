package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.OrdersDAO;
import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
import lk.ijse.phoneshopmanagementsystem.dto.PlaceOrderDTO;
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

public class OrdersDAOImpl implements OrdersDAO {
    OrderDetailDAOImpl od = new OrderDetailDAOImpl();


    public ArrayList<PlaceOrder> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Orders ORDER BY Order_ID DESC");

        ArrayList<PlaceOrder> ordersList = new ArrayList<>();
        while (rs.next()) {
            PlaceOrder orderDTO = new PlaceOrder(
                    rs.getString("Order_ID"),
                    rs.getDate("Order_date"),
                    rs.getDouble("Total_amount"),
                    rs.getString("Customer_ID"),
                    rs.getObject("Employee_ID", Integer.class),
                    rs.getString("Order_status")
            );
            ordersList.add(orderDTO);
        }
        return ordersList;
    }

    @Override
    public boolean update(PlaceOrder customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    public String getNextID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT Order_ID FROM Orders ORDER BY Order_ID DESC LIMIT 1");

        if (rs.next()) {
            String lastId = rs.getString("Order_ID");
            if (lastId == null || lastId.trim().isEmpty() || !lastId.startsWith("O")) {
                return "O001";
            }
            try {
                int number = Integer.parseInt(lastId.substring(1));
                return String.format("O%03d", number + 1);
            } catch (NumberFormatException e) {
                return "O001";
            }
        }
        return "O001";
    }

    public boolean save(PlaceOrder orderDTO) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getDbConnection().getConnection();
        try {
            conn.setAutoCommit(false);


            String orderId = getNextID();

            boolean isOrderSaved = CrudUtil.execute(
                    "INSERT INTO Orders (Order_ID, Order_date, Total_amount, Customer_ID, Employee_ID, Order_status) VALUES (?, ?, ?, ?, ?, ?)",
                    orderId,
                    new java.sql.Date(orderDTO.getOrderDate().getTime()),
                    orderDTO.getTotalAmount(),
                    orderDTO.getCustomerId(),
                    orderDTO.getEmployeeId(),
                    orderDTO.getOrderStatus()
            );

            if (!isOrderSaved) {
                conn.rollback();
                return false;
            }

            for (OrderDetails detail : orderDTO.getDetails()) {
                String orderDetailId = od.generateOrderDetailId();

                boolean isDetailSaved = CrudUtil.execute(
                        "INSERT INTO Order_Details (Order_Detail_ID, Order_ID, Item_ID, Quantity, Unit_price, Sub_total) VALUES (?, ?, ?, ?, ?, ?)",
                        orderDetailId,
                        orderId,
                        detail.getItemId(),
                        detail.getQuantity(),
                        detail.getUnitPrice(),
                        detail.getSubtotal()
                );

                if (!isDetailSaved) {
                    conn.rollback();
                    return false;
                }

                boolean isQtyUpdated = CrudUtil.execute(
                        "UPDATE Item SET Quantity = Quantity - ? WHERE Item_ID = ?",
                        detail.getQuantity(),
                        detail.getItemId()
                );

                if (!isQtyUpdated) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public PlaceOrder search(String orderId)
            throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM Orders WHERE Order_ID = ?",
                orderId
        );

        if (rs.next()) {

            ArrayList<OrderDetails> details =
                    od.getOrderDetails(orderId);

            PlaceOrder order = new PlaceOrder(
                    rs.getString("Order_ID"),
                    rs.getDate("Order_date"),
                    rs.getDouble("Total_amount"),
                    rs.getString("Customer_ID"),
                    Integer.valueOf(rs.getString("Employee_ID")),
                    rs.getString("Order_status"),
                    details
            );

            return order;
        }

        return null;
    }


    public boolean update(String status, String orderId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Orders SET Order_status = ? WHERE Order_ID = ?",
                status, orderId
        );

    }

    public boolean cancelOrder(String orderId) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getDbConnection().getConnection();
        try {
            conn.setAutoCommit(false);

            ArrayList<OrderDetails> details = od.getOrderDetails(orderId);

            for (OrderDetails detail : details) {

                boolean isQtyRestored = CrudUtil.execute(
                        "UPDATE Item SET Quantity = Quantity + ? WHERE Item_ID = ?",
                        detail.getQuantity(),
                        detail.getItemId()
                );

                if (!isQtyRestored) {
                    conn.rollback();
                    return false;
                }
            }

            if (update("Cancelled", orderId)) {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public String getCustomerName(String customerId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT Name FROM Customer WHERE Customer_ID = ?", customerId);
        if (rs.next()) {
            return rs.getString("Name");
        }
        return "Unknown Customer";
    }

    public void printReports() throws SQLException, JRException, ClassNotFoundException {

        Connection conn = DBConnection.getDbConnection().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream("/reports/placeOrder_1.jrxml");

        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }
}
