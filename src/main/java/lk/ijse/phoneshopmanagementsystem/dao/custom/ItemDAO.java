package lk.ijse.phoneshopmanagementsystem.dao.custom;

import lk.ijse.phoneshopmanagementsystem.dao.CrudDAO;
import lk.ijse.phoneshopmanagementsystem.dto.ItemDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Item;
import net.sf.jasperreports.engine.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO <Item> {

    public void printReports() throws SQLException, JRException, ClassNotFoundException;
}
