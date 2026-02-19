package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.SupplierBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.CustomerDAO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.SupplierDAO;
import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.SupplierDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Item;
import lk.ijse.phoneshopmanagementsystem.entity.Supplier;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.SUPPLIER);


    public String getNextSupplierID() throws SQLException, ClassNotFoundException {
        return supplierDAO.getNextID();
    }

    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(
                new Supplier(
                        supplierDTO.getSupplierId(),
                        supplierDTO.getName(),
                        supplierDTO.getCompany(),
                        supplierDTO.getAddress(),
                        supplierDTO.getContact(),
                        supplierDTO.getEmail()
                )
        );
    }

    public boolean updateSupplier(SupplierDTO  supplierDTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(
                new Supplier(
                        supplierDTO.getSupplierId(),
                        supplierDTO.getName(),
                        supplierDTO.getCompany(),
                        supplierDTO.getAddress(),
                        supplierDTO.getContact(),
                        supplierDTO.getEmail()
                )
        );
    }

    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> supplierList = supplierDAO.getAll();
        ArrayList<SupplierDTO> supplierDTOList = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            supplierDTOList.add(
                    new SupplierDTO(
                        supplier.getSupplierId(),
                        supplier.getName(),
                        supplier.getCompany(),
                        supplier.getAddress(),
                        supplier.getContact(),
                        supplier.getEmail()
                    )
            );
        }
        return supplierDTOList;
    }

    public SupplierDTO searchSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.search(supplierId);
        if (supplier == null) return null;
        return new SupplierDTO(
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getCompany(),
                supplier.getAddress(),
                supplier.getContact(),
                supplier.getEmail()
        );
    }

    public void printSupplierReports() throws SQLException, JRException, ClassNotFoundException {
        supplierDAO.printReports();
    }
}
