package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.ItemBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.ItemDAO;
import lk.ijse.phoneshopmanagementsystem.dto.ItemDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Item;
import net.sf.jasperreports.engine.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.ITEM);

    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.save(
                new Item(
                        itemDTO.getItemCode(),
                        itemDTO.getDescription(),
                        itemDTO.getBrand(),
                        itemDTO.getModel(),
                        itemDTO.getUnitPrice(),
                        itemDTO.getQtyOnHand(),
                        itemDTO.getCategory(),
                        itemDTO.getSupplierId()
                )
        );
    }

    public String getNextItemID() throws SQLException, ClassNotFoundException {
        return itemDAO.getNextID();
    }

    public ItemDTO searchItem(String itemCode) throws SQLException, ClassNotFoundException {

        Item item = itemDAO.search(itemCode);

        if (item == null) return null;
        return new ItemDTO(
                item.getItemCode(),
                item.getDescription(),
                item.getBrand(),
                item.getModel(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                item.getCategory(),
                item.getSupplierId()
        );
    }


    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return itemDAO.update(
                new Item(
                        itemDTO.getItemCode(),
                        itemDTO.getDescription(),
                        itemDTO.getBrand(),
                        itemDTO.getModel(),
                        itemDTO.getUnitPrice(),
                        itemDTO.getQtyOnHand(),
                        itemDTO.getCategory(),
                        itemDTO.getSupplierId()
                )
        );
    }

    public boolean deleteItem(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemCode);
    }

    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {

        ArrayList<Item> itemList = itemDAO.getAll();
        ArrayList<ItemDTO> dtoList = new ArrayList<>();

        for (Item item : itemList) {
            dtoList.add(
                    new ItemDTO(
                            item.getItemCode(),
                            item.getDescription(),
                            item.getBrand(),
                            item.getModel(),
                            item.getUnitPrice(),
                            item.getQtyOnHand(),
                            item.getCategory(),
                            item.getSupplierId()
                    )
            );
        }

        return dtoList;
    }


    public void printItemReports() throws SQLException, JRException, ClassNotFoundException {

        itemDAO.printReports();
    }
}
