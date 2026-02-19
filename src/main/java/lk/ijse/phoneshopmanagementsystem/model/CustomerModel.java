/*
package lk.ijse.phoneshopmanagementsystem.model;

import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.CustomerDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Customer VALUES (?,?,?,?,?,?,?)",
                customerDTO.getCustomerId(),
                null,
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getNic()
        );
    }



    public CustomerDTO searchCustomer(String customerId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Customer WHERE Customer_ID = ?",
                customerId
        );

        if (resultSet.next()) {
            return new CustomerDTO(
                    resultSet.getString("Customer_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Contact"),
                    resultSet.getString("Email"),
                    resultSet.getString("NIC")
            );
        }
        return null;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Customer SET Name=?, Address=?, Contact=?, Email=?, NIC=? WHERE Customer_ID=?",
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getNic(),
                customerDTO.getCustomerId()
        );
    }

    public boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute(
                "DELETE FROM Customer WHERE Customer_ID = ?",
                customerId
        );
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer");
        ArrayList<CustomerDTO> customerList = new ArrayList<>();

        while (resultSet.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
                    resultSet.getString("Customer_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Contact"),
                    resultSet.getString("Email"),
                    resultSet.getString("NIC")
            );
            customerList.add(customerDTO);
        }
        return customerList;
    }

    public ArrayList<CustomerDTO> searchCustomersByText(String text) throws SQLException {
        String search = "%" + text + "%";
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Customer WHERE Customer_ID LIKE ? OR Name LIKE ? OR Contact LIKE ? OR NIC LIKE ?",
                search, search, search, search
        );
        ArrayList<CustomerDTO> customerList = new ArrayList<>();
        while (resultSet.next()) {
            customerList.add(new CustomerDTO(
                    resultSet.getString("Customer_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Contact"),
                    resultSet.getString("Email"),
                    resultSet.getString("NIC")
            ));
        }
        return customerList;
    }


    public String getNextCustomeID() throws SQLException {
        String sql = "SELECT Customer_ID FROM Customer ORDER BY Customer_ID DESC LIMIT 1";
        ResultSet rst = DBConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        if (rst.next()) {
            String lastCode = rst.getString(1);

            int number = Integer.parseInt(lastCode.substring(1));
            number++;

            return "C" + String.format("%03d", number);
        }
        return "C001";
    }

    public void printCustomerReports() throws SQLException, JRException {

        Connection conn = DBConnection.getInstance().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream("/reports/allCustomers.jrxml");

        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }

}*/
