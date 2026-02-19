package lk.ijse.phoneshopmanagementsystem.bo.custom;

import lk.ijse.phoneshopmanagementsystem.bo.SuperBO;
import lk.ijse.phoneshopmanagementsystem.dto.UserDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public String getNextUserID() throws SQLException, ClassNotFoundException;

    public boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;

    public boolean updateUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;

    public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException;

    public ArrayList<UserDTO> getAllUser() throws SQLException, ClassNotFoundException;

    public UserDTO searchUser(String userId) throws SQLException, ClassNotFoundException;

    public UserDTO validateLogin(String userName, String password) throws SQLException, ClassNotFoundException;

    public boolean updateUserCredentials(String currentUsername, String newUsername, String newPassword) throws SQLException, ClassNotFoundException;
}
