/*
package lk.ijse.phoneshopmanagementsystem.model;

import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
import lk.ijse.phoneshopmanagementsystem.dto.PlaceOrderDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class PlaceOrderModel {

    private String generateOrderId() throws SQLException {
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

    private String generateOrderDetailId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT Order_Detail_ID FROM Order_Details ORDER BY Order_Detail_ID DESC LIMIT 1");
        if (rs.next()) {
            String lastId = rs.getString("Order_Detail_ID");
            if (lastId == null || lastId.trim().isEmpty() || !lastId.startsWith("OD")) {
                return "OD001";
            }
            try {
                int number = Integer.parseInt(lastId.substring(2));
                return String.format("OD%03d", number + 1);
            } catch (NumberFormatException e) {
                return "OD001";
            }
        }
        return "OD001";
    }

    public boolean placeOrder(PlaceOrderDTO orderDTO) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);

            String orderId = generateOrderId();

            String orderSql = "INSERT INTO Orders (Order_ID, Order_date, Total_amount, Customer_ID, Employee_ID, Order_status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmOrder = conn.prepareStatement(orderSql);
            pstmOrder.setString(1, orderId);
            pstmOrder.setDate(2, new java.sql.Date(orderDTO.getOrderDate().getTime()));
            pstmOrder.setDouble(3, orderDTO.getTotalAmount());
            pstmOrder.setString(4, orderDTO.getCustomerId());
            if (orderDTO.getEmployeeId() != null) {
                pstmOrder.setInt(5, orderDTO.getEmployeeId());
            } else {
                pstmOrder.setNull(5, Types.INTEGER);
            }
            pstmOrder.setString(6, orderDTO.getOrderStatus());

            if (pstmOrder.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            for (OrderDetailDTO detail : orderDTO.getOrderDetails()) {
                String orderDetailId = generateOrderDetailId();

                String detailSql = "INSERT INTO Order_Details (Order_Detail_ID, Order_ID, Item_ID, Quantity, Unit_price, Sub_total) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmDetail = conn.prepareStatement(detailSql);
                pstmDetail.setString(1, orderDetailId);
                pstmDetail.setString(2, orderId);
                pstmDetail.setString(3, detail.getItemId());
                pstmDetail.setInt(4, detail.getQuantity());
                pstmDetail.setDouble(5, detail.getUnitPrice());
                pstmDetail.setDouble(6, detail.getSubtotal());

                if (pstmDetail.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }

                String updateQty = "UPDATE Item SET Quantity = Quantity - ? WHERE Item_ID = ?";
                PreparedStatement pstmQty = conn.prepareStatement(updateQty);
                pstmQty.setInt(1, detail.getQuantity());
                pstmQty.setString(2, detail.getItemId());

                if (pstmQty.executeUpdate() == 0) {
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

    public PlaceOrderDTO searchOrder(String orderId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Orders WHERE Order_ID = ?", orderId);
        if (rs.next()) {
            PlaceOrderDTO orderDTO = new PlaceOrderDTO();
            orderDTO.setOrderId(rs.getString("Order_ID"));
            orderDTO.setOrderDate(rs.getDate("Order_date"));
            orderDTO.setTotalAmount(rs.getDouble("Total_amount"));
            orderDTO.setCustomerId(rs.getString("Customer_ID"));
            orderDTO.setEmployeeId(rs.getObject("Employee_ID", Integer.class));
            orderDTO.setOrderStatus(rs.getString("Order_status"));
            orderDTO.setOrderDetails(getOrderDetails(orderId));
            return orderDTO;
        }
        return null;
    }

    public ArrayList<OrderDetailDTO> getOrderDetails(String orderId) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT od.*, i.Description, i.Brand, i.Model " +
                        "FROM Order_Details od " +
                        "JOIN Item i ON od.Item_ID = i.Item_ID " +
                        "WHERE od.Order_ID = ?", orderId);

        ArrayList<OrderDetailDTO> details = new ArrayList<>();
        while (rs.next()) {
            OrderDetailDTO detail = new OrderDetailDTO();
            detail.setOrderDetailId(rs.getString("Order_Detail_ID"));
            detail.setOrderId(rs.getString("Order_ID"));
            detail.setItemId(rs.getString("Item_ID"));
            detail.setQuantity(rs.getInt("Quantity"));
            detail.setUnitPrice(rs.getDouble("Unit_price"));
            detail.setSubtotal(rs.getDouble("Sub_total"));
            detail.setItemDescription(rs.getString("Description"));
            detail.setItemBrand(rs.getString("Brand"));
            detail.setItemModel(rs.getString("Model"));
            details.add(detail);
        }
        return details;
    }

    public ArrayList<PlaceOrderDTO> getAllOrders() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Orders ORDER BY Order_ID DESC");
        ArrayList<PlaceOrderDTO> orders = new ArrayList<>();
        while (rs.next()) {
            PlaceOrderDTO orderDTO = new PlaceOrderDTO();
            orderDTO.setOrderId(rs.getString("Order_ID"));
            orderDTO.setOrderDate(rs.getDate("Order_date"));
            orderDTO.setTotalAmount(rs.getDouble("Total_amount"));
            orderDTO.setCustomerId(rs.getString("Customer_ID"));
            orderDTO.setEmployeeId(rs.getObject("Employee_ID", Integer.class));
            orderDTO.setOrderStatus(rs.getString("Order_status"));
            orders.add(orderDTO);
        }
        return orders;
    }

    public boolean updateOrderStatus(String orderId, String status) throws SQLException {
        String sql = "UPDATE Orders SET Order_status = ? WHERE Order_ID = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, status);
        pstm.setString(2, orderId);
        return pstm.executeUpdate() > 0;
    }

    // FIXED: int → String
    public String getCustomerName(String customerId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT Name FROM Customer WHERE Customer_ID = ?", customerId);
        if (rs.next()) {
            return rs.getString("Name");
        }
        return "Unknown Customer";
    }

    // FIXED: int → String
    public ArrayList<PlaceOrderDTO> getOrdersByCustomer(String customerId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Orders WHERE Customer_ID = ? ORDER BY Order_ID DESC", customerId);
        ArrayList<PlaceOrderDTO> orders = new ArrayList<>();
        while (rs.next()) {
            PlaceOrderDTO orderDTO = new PlaceOrderDTO();
            orderDTO.setOrderId(rs.getString("Order_ID"));
            orderDTO.setOrderDate(rs.getDate("Order_date"));
            orderDTO.setTotalAmount(rs.getDouble("Total_amount"));
            orderDTO.setCustomerId(rs.getString("Customer_ID"));
            orderDTO.setEmployeeId(rs.getObject("Employee_ID", Integer.class));
            orderDTO.setOrderStatus(rs.getString("Order_status"));
            orders.add(orderDTO);
        }
        return orders;
    }

    public boolean cancelOrder(String orderId) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);

            ArrayList<OrderDetailDTO> details = getOrderDetails(orderId);
            for (OrderDetailDTO detail : details) {
                String updateQty = "UPDATE Item SET Quantity = Quantity + ? WHERE Item_ID = ?";
                PreparedStatement pstm = conn.prepareStatement(updateQty);
                pstm.setInt(1, detail.getQuantity());
                pstm.setString(2, detail.getItemId());
                if (pstm.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            if (updateOrderStatus(orderId, "Cancelled")) {
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

    public int getTotalOrdersCount() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT COUNT(*) as count FROM Orders");
        if (rs.next()) {
            return rs.getInt("count");
        }
        return 0;
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
}*/
