package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.dto.CustomerDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Customer;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {


    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    public CustomerDTO searchCustomer(String customerId) throws SQLException, ClassNotFoundException;

    public ArrayList<CustomerDTO> searchCustomerByText(String text) throws SQLException, ClassNotFoundException;

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException;

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;

    public String getNextCustomerID() throws SQLException, ClassNotFoundException;

    public void printReports() throws SQLException, JRException, ClassNotFoundException;
}
