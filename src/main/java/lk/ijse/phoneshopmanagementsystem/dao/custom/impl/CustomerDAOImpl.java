package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.CustomerDAO;
import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.CustomerDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Customer;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    public boolean save(Customer customerDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)",customerDTO.getCustomerId(),
                null,
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getNic()

        );
    }

    public Customer search(String customerId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer WHERE Customer_ID=?", customerId);

        if (resultSet.next()){
            return new Customer(
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

    public ArrayList<Customer> searchByText(String text) throws SQLException, ClassNotFoundException {
        String search = "%" + text + "%";
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer WHERE Customer_ID LIKE ? OR Name LIKE ? OR " +
                "Contact LIKE ? OR NIC LIKE ?", search,search,search,search
        );
        ArrayList<Customer> customersList = new ArrayList<>();
        while (resultSet.next()){
            customersList.add(new Customer(
                    resultSet.getString("Customer_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Contact"),
                    resultSet.getString("Email"),
                    resultSet.getString("NIC")
            ));
        }
        return customersList;
    }

    public boolean update(Customer customerDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Customer SET Name=?, Address=?, Contact=?, Email=?, NIC=? WHERE Customer_ID = ?",
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getContact(),
                customerDTO.getEmail(),
                customerDTO.getNic(),
                customerDTO.getCustomerId()
        );
    }

    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Customer WHERE Customer_ID=?", customerId);
    }

    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer");
        ArrayList<Customer> customerList = new ArrayList<>();

        while (resultSet.next()){
            Customer customerDTO = new Customer(
                    resultSet.getString("Customer_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Contact"),
                    resultSet.getString("Email"),
                    resultSet.getString("NIC")
            );
            customerList.add(customerDTO);
        }
        return  customerList;
    }

    public String getNextID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT Customer_ID FROM Customer ORDER BY Customer_ID DESC LIMIT 1");

        if (resultSet.next()){
            String lastCode = resultSet.getString(1);

            int number = Integer.parseInt(lastCode.substring(1));
            number++;

            return "C" + String.format("%03d", number);
        }
        return "C001";
    }

    public void printReports() throws SQLException, JRException, ClassNotFoundException {

        Connection conn = DBConnection.getDbConnection().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream("/reports/allCustomers.jrxml");

        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }

}
