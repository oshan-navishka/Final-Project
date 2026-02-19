package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.ItemDAO;
import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.ItemDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Customer;
import lk.ijse.phoneshopmanagementsystem.entity.Item;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO{
    public boolean save(Item itemDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Item (Item_ID, Model, Brand, Category, Supplier_ID, Description, " +
                        "Unit_price, Quantity) VALUES(?,?,?,?,?,?,?,?)",
                itemDTO.getItemCode(),
                itemDTO.getModel(),
                itemDTO.getBrand(),
                itemDTO.getCategory(),
                itemDTO.getSupplierId(),
                itemDTO.getDescription(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand()
        );
    }

    public String getNextID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item ORDER BY Item_ID DESC LIMIT 1");

        if (resultSet.next()) {
            String lastCode = resultSet.getString("Item_ID");

            String prefix = lastCode.replaceAll("[0-9]", "");
            String numberPart = lastCode.replaceAll("[^0-9]", "");

            int number = Integer.parseInt(numberPart);
            number++;

            String newCode = prefix + String.format("%03d", number);
            return newCode;
        } else {
            return "ITEM001";
        }
    }

    public Item search(String itemCode) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item WHERE Item_ID = ?", itemCode);

        if(resultSet.next()) {
            return new Item(
                    resultSet.getString("Item_ID"),
                    resultSet.getString("Model"),
                    resultSet.getString("Brand"),
                    resultSet.getString("Category"),
                    resultSet.getString("Supplier_ID"),
                    resultSet.getString("Description"),
                    resultSet.getDouble("Unit_price"),
                    resultSet.getInt("Quantity")
            );

            //return itemDTO;
        }
        return null;
    }

    public boolean update(Item itemDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Item SET Model=?, Brand=?, Category=?, Supplier_ID=?, Description=?, " +
                        "Unit_price=?, Quantity=? WHERE Item_ID=?",
                itemDTO.getModel(),
                itemDTO.getBrand(),
                itemDTO.getCategory(),
                itemDTO.getSupplierId(),
                itemDTO.getDescription(),
                itemDTO.getUnitPrice(),
                itemDTO.getQtyOnHand(),
                itemDTO.getItemCode()
        );
    }

    public boolean delete(String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Item WHERE Item_ID = ?", itemCode);
    }

    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item");
        ArrayList<Item> itemSet = new ArrayList<>();
        while (resultSet.next()) {
            Item item = new Item(
                    resultSet.getString("Item_ID"),
                    resultSet.getString("Model"),
                    resultSet.getString("Brand"),
                    resultSet.getString("Category"),
                    resultSet.getString("Supplier_ID"),
                    resultSet.getString("Description"),
                    resultSet.getDouble("Unit_price"),
                    resultSet.getInt("Quantity")
            );
            itemSet.add(item);
        }
        return itemSet;
    }

    public void printReports() throws SQLException, JRException, ClassNotFoundException {

        Connection conn = DBConnection.getDbConnection().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream("/reports/all_Item.jrxml");

        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }
}
