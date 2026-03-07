package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.QueryBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.QueryDAO;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
import lk.ijse.phoneshopmanagementsystem.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryBOImpl implements QueryBO {

    QueryDAO queryDAO = (QueryDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.QUERY);

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

    @Override
    public List<Map<String, Object>> getMonthlyOrders(int year, int month) throws SQLException, ClassNotFoundException {
        return queryDAO.getMonthlyOrders(year, month);
    }

    @Override
    public List<Map<String, Object>> getYearlyOrders(int year) throws SQLException, ClassNotFoundException {
        return queryDAO.getYearlyOrders(year);
    }

    @Override
    public List<Map<String, Object>> getTopCustomers(int year, int limit) throws SQLException, ClassNotFoundException {
        return queryDAO.getTopCustomers(year, limit);
    }


}
