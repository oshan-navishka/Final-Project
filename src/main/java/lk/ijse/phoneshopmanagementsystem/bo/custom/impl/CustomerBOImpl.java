package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.CustomerBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.CustomerDAO;
import lk.ijse.phoneshopmanagementsystem.dto.CustomerDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Customer;
import net.sf.jasperreports.engine.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.CUSTOMER);

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(
                new Customer(
                        customerDTO.getCustomerId(),
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getContact(),
                        customerDTO.getEmail(),
                        customerDTO.getNic()
                )
        );
    }

    @Override
    public CustomerDTO searchCustomer(String customerId) throws SQLException, ClassNotFoundException {

        Customer customer = customerDAO.search(customerId);

        if (customer == null) return null;

        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getName(),
                customer.getAddress(),
                customer.getContact(),
                customer.getEmail(),
                customer.getNic()
        );
    }


    public ArrayList<CustomerDTO> searchCustomerByText(String text) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> result = customerDAO.searchByText(text);
        ArrayList<CustomerDTO> dtoList = new ArrayList<>();
        for (Customer customer : result) {
            dtoList.add(
                    new CustomerDTO(
                            customer.getCustomerId(),
                            customer.getName(),
                            customer.getAddress(),
                            customer.getContact(),
                            customer.getEmail(),
                            customer.getNic()
                    )
            );
        }

        return dtoList;

    }



    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(
                new Customer(
                        customerDTO.getCustomerId(),
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getContact(),
                        customerDTO.getEmail(),
                        customerDTO.getNic()

                )
        );
    }

    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerList = customerDAO.getAll();
        ArrayList<CustomerDTO> dtoList = new ArrayList<>();

        for (Customer customer : customerList) {
            dtoList.add(
                    new CustomerDTO(
                            customer.getCustomerId(),
                            customer.getName(),
                            customer.getAddress(),
                            customer.getContact(),
                            customer.getEmail(),
                            customer.getNic()
                    )
            );
        }

        return dtoList;
    }

    public String getNextCustomerID() throws SQLException, ClassNotFoundException {
       return customerDAO.getNextID();
    }

    public void printReports() throws SQLException, JRException, ClassNotFoundException {
        customerDAO.printReports();
    }

}
