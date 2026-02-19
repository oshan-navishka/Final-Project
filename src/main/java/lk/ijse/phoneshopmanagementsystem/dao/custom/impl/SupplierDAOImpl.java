package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.SupplierDAO;
import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
import lk.ijse.phoneshopmanagementsystem.dto.SupplierDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Supplier;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    public String getNextID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1");

        if (rst.next()){
            String lastID = rst.getString(1);
            if(lastID != null && lastID.startsWith("S")){
                int num = Integer.parseInt(lastID.substring(1));
                return String.format("S%03d", num + 1);
            }
        }
        return "S001";
    }

    public boolean save(Supplier  supplierDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO supplier VALUES (?,?,?,?,?,?)",
                supplierDTO.getSupplierId(),
                supplierDTO.getName(),
                supplierDTO.getCompany(),
                supplierDTO.getAddress(),
                supplierDTO.getContact(),
                supplierDTO.getEmail()
        );
    }

    public boolean update(Supplier  supplierDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE supplier SET name=?, company=?, address=?, contact=?, email=? WHERE supplier_id=?",
                supplierDTO.getName(),
                supplierDTO.getCompany(),
                supplierDTO.getAddress(),
                supplierDTO.getContact(),
                supplierDTO.getEmail(),
                supplierDTO.getSupplierId()
        );
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM supplier WHERE supplier_id=?", id);
    }

    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier");
        ArrayList<Supplier> supplierList = new ArrayList<>();

        while (resultSet.next()){
            Supplier supplier = new Supplier(
                    resultSet.getString("Supplier_ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Company"),
                    resultSet.getString("Address"),
                    resultSet.getString("Contact"),
                    resultSet.getString("Email")
            );
            supplierList.add(supplier);
        }
        return supplierList;
    }

    public Supplier search(String supplierId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Supplier WHERE supplier_id=?", supplierId
        );

        if (resultSet.next()){
            return new Supplier(
                    resultSet.getString("supplier_id"),
                    resultSet.getString("name"),
                    resultSet.getString("company"),
                    resultSet.getString("address"),
                    resultSet.getString("contact"),
                    resultSet.getString("email")
            );
        }
        return null;
    }

    public void printReports() throws SQLException, JRException, ClassNotFoundException {

        Connection conn = DBConnection.getDbConnection().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream("/reports/allSupllier.jrxml");

        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }

}
