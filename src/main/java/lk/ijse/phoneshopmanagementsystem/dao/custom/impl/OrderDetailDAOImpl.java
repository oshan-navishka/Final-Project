package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.OrderDetailDAO;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
import lk.ijse.phoneshopmanagementsystem.entity.OrderDetails;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    String generateOrderDetailId() throws SQLException, ClassNotFoundException {
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

    @Override
    public boolean save(OrderDetails customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(OrderDetails customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(String customerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getNextID() throws SQLException, ClassNotFoundException {
        return "";
    }

    public ArrayList<OrderDetails> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT od.*, i.Description, i.Brand, i.Model " +
                        "FROM Order_Details od " +
                        "JOIN Item i ON od.Item_ID = i.Item_ID " +
                        "WHERE od.Order_ID = ?", orderId);

        ArrayList<OrderDetails> detailList = new ArrayList<>();
        while (rs.next()) {
            OrderDetails detail = new OrderDetails(
                    rs.getString("Order_Detail_ID"),
                    rs.getString("Order_ID"),
                    rs.getString("Item_ID"),
                    rs.getInt("Quantity"),
                    rs.getDouble("Unit_price"),
                    rs.getDouble("Sub_total"),
                    rs.getString("Description"),
                    rs.getString("Brand"),
                    rs.getString("Model")
            );
            detailList.add(detail);
        }
        return detailList;
    }
}
