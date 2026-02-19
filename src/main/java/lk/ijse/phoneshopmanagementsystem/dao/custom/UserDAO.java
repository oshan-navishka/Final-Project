package lk.ijse.phoneshopmanagementsystem.dao.custom;

import lk.ijse.phoneshopmanagementsystem.dao.CrudDAO;
import lk.ijse.phoneshopmanagementsystem.dto.UserDTO;
import lk.ijse.phoneshopmanagementsystem.entity.User;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {

    public User validateLogin(String userName, String password) throws SQLException, ClassNotFoundException;

    public boolean updateUserCredentials(String currentUsername, String newUsername, String newPassword) throws SQLException, ClassNotFoundException;
}
