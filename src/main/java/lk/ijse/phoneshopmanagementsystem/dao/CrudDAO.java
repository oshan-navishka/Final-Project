package lk.ijse.phoneshopmanagementsystem.dao;

import lk.ijse.phoneshopmanagementsystem.dto.CustomerDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO {

    public boolean save(T customerDTO) throws SQLException, ClassNotFoundException;

    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    public boolean update(T customerDTO) throws SQLException, ClassNotFoundException;

    public boolean delete(String customerId) throws SQLException, ClassNotFoundException;

    public T search(String customerId) throws SQLException, ClassNotFoundException;

    public String getNextID() throws SQLException, ClassNotFoundException;

}
