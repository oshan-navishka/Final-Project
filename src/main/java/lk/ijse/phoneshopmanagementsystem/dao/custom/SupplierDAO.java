package lk.ijse.phoneshopmanagementsystem.dao.custom;

import lk.ijse.phoneshopmanagementsystem.dao.CrudDAO;
import lk.ijse.phoneshopmanagementsystem.entity.Supplier;
import net.sf.jasperreports.engine.*;

import java.sql.SQLException;

public interface SupplierDAO extends CrudDAO<Supplier> {

    public void printReports() throws SQLException, JRException, ClassNotFoundException;
}
