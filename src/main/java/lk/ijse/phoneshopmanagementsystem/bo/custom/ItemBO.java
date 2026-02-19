package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.dto.ItemDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Item;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    public String getNextItemID() throws SQLException, ClassNotFoundException;

    public ItemDTO searchItem(String itemCode) throws SQLException, ClassNotFoundException;

    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException;

    public void printItemReports() throws SQLException, JRException, ClassNotFoundException;
}
