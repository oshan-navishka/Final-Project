package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.dao.CrudDAO;
import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.SupplierDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    public String getNextSupplierID() throws SQLException, ClassNotFoundException;

    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException;

    public boolean updateSupplier(SupplierDTO  supplierDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException;

    public SupplierDTO searchSupplier(String supplierId) throws SQLException, ClassNotFoundException;

    public void printSupplierReports() throws SQLException, JRException, ClassNotFoundException;
}
