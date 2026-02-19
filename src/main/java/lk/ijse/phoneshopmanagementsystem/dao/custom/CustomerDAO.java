package lk.ijse.phoneshopmanagementsystem.dao.custom;

import lk.ijse.phoneshopmanagementsystem.dao.CrudDAO;
import lk.ijse.phoneshopmanagementsystem.dto.CustomerDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Customer;
import net.sf.jasperreports.engine.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO <Customer> {

    public ArrayList<Customer> searchByText(String text) throws SQLException, ClassNotFoundException;

    public void printReports() throws SQLException, JRException, ClassNotFoundException;
}
