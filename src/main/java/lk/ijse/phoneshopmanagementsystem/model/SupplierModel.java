/*
package lk.ijse.phoneshopmanagementsystem.model;

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
import java.util.List;

public class SupplierModel {

    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            if (lastId != null && lastId.startsWith("S")) {
                int num = Integer.parseInt(lastId.substring(1));
                return String.format("S%03d", num + 1);
            }
        }
        return "S001";
    }

    public boolean saveSupplier(SupplierDTO dto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Supplier VALUES (?,?,?,?,?,?)",
                dto.getSupplierId(),
                dto.getName(),
                dto.getCompany(),
                dto.getAddress(),
                dto.getContact(),
                dto.getEmail()
        );
    }

    public boolean updateSupplier(SupplierDTO dto) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Supplier SET name=?, company=?, address=?, contact=?, email=? WHERE supplier_id=?",
                dto.getName(), dto.getCompany(), dto.getAddress(),
                dto.getContact(), dto.getEmail(), dto.getSupplierId()
        );
    }

    public boolean deleteSupplier(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM Supplier WHERE supplier_id=?", id);
    }

    public SupplierDTO searchSupplier(String id) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier WHERE supplier_id=?", id);
        return rst.next() ? new SupplierDTO(
                rst.getString(1), rst.getString(2), rst.getString(3),
                rst.getString(4), rst.getString(5), rst.getString(6)
        ) : null;
    }

    public List<SupplierDTO> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Supplier");
        ArrayList<SupplierDTO> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new SupplierDTO(
                    rst.getString(1), rst.getString(2), rst.getString(3),
                    rst.getString(4), rst.getString(5), rst.getString(6)
            ));
        }
        return list;
    }

    public List<SupplierDTO> searchSuppliers(String text) throws SQLException {
        String search = "%" + text + "%";
        ResultSet rst = CrudUtil.execute(
                "SELECT * FROM Supplier WHERE supplier_id LIKE ? OR name LIKE ? OR company LIKE ? OR contact LIKE ? OR email LIKE ?",
                search, search, search, search, search
        );
        ArrayList<SupplierDTO> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new SupplierDTO(
                    rst.getString(1), rst.getString(2), rst.getString(3),
                    rst.getString(4), rst.getString(5), rst.getString(6)
            ));
        }
        return list;
    }

    public void printSupplierReports() throws SQLException, JRException {

        Connection conn = DBConnection.getInstance().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream("/reports/allSupllier.jrxml");

        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }
}*/
