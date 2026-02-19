/*
package lk.ijse.phoneshopmanagementsystem.model;

import lk.ijse.phoneshopmanagementsystem.dbconnection.DBConnection;
import lk.ijse.phoneshopmanagementsystem.dto.ItemDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {

    public boolean saveItem(ItemDTO itemDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Item (Item_ID, Model, Brand, Category, Supplier_ID, Description, Unit_price, Quantity) VALUES(?,?,?,?,?,?,?,?)",
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

    public String getNextItemCode() throws SQLException {
        String sql = "SELECT Item_ID FROM Item ORDER BY Item_ID DESC LIMIT 1";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String lastCode = resultSet.getString("Item_ID");

            String prefix = lastCode.replaceAll("[0-9]", ""); // "ITEM"
            String numberPart = lastCode.replaceAll("[^0-9]", ""); // "001"

            int number = Integer.parseInt(numberPart);
            number++;

            String newCode = prefix + String.format("%03d", number);
            return newCode;
        } else {
            return "ITEM001";
        }
    }

    public ItemDTO searchItem(String itemCode) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Item WHERE Item_ID = ?",
                itemCode
        );

        if (resultSet.next()) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setItemCode(resultSet.getString("Item_ID"));
            itemDTO.setModel(resultSet.getString("Model"));
            itemDTO.setBrand(resultSet.getString("Brand"));
            itemDTO.setCategory(resultSet.getString("Category"));
            itemDTO.setSupplierId(resultSet.getString("Supplier_ID"));
            itemDTO.setDescription(resultSet.getString("Description"));
            itemDTO.setUnitPrice(resultSet.getDouble("Unit_price"));
            itemDTO.setQtyOnHand(resultSet.getInt("Quantity"));
            return itemDTO;
        }
        return null;
    }

    public boolean updateItem(ItemDTO itemDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Item SET Model=?, Brand=?, Category=?, Supplier_ID=?, Description=?, Unit_price=?, Quantity=? WHERE Item_ID=?",
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

    public boolean deleteItem(String itemCode) throws SQLException {
        return CrudUtil.execute(
                "DELETE FROM Item WHERE Item_ID = ?",
                itemCode
        );
    }

    public ArrayList<ItemDTO> getAll() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item");
        ArrayList<ItemDTO> itemSet = new ArrayList<>();

        while (resultSet.next()) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setItemCode(resultSet.getString("Item_ID"));
            itemDTO.setModel(resultSet.getString("Model"));
            itemDTO.setBrand(resultSet.getString("Brand"));
            itemDTO.setCategory(resultSet.getString("Category"));
            itemDTO.setSupplierId(resultSet.getString("Supplier_ID"));
            itemDTO.setDescription(resultSet.getString("Description"));
            itemDTO.setUnitPrice(resultSet.getDouble("Unit_price"));
            itemDTO.setQtyOnHand(resultSet.getInt("Quantity"));
            itemSet.add(itemDTO);
        }
        return itemSet;
    }

    public void printItemReports() throws SQLException, JRException {

        Connection conn = DBConnection.getInstance().getConnection();

        InputStream inputStream =
                getClass().getResourceAsStream("/reports/all_Item.jrxml");

        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

        JasperViewer jasperViewer = new JasperViewer(jp, false);
        jasperViewer.setVisible(true);
    }
}*/
