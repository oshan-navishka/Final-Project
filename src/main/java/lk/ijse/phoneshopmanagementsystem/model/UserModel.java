/*
package lk.ijse.phoneshopmanagementsystem.model;

import lk.ijse.phoneshopmanagementsystem.dto.UserDTO;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public boolean saveUser(UserDTO userDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO User (User_ID, User_Name, Password, Email, Contact_no, Role) VALUES (?, ?, ?, ?, ?, ?)",
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getContactNo(),
                userDTO.getRole()
        );
    }

    public boolean updateUser(UserDTO userDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE User SET User_Name = ?, Password = ?, Email = ?, Contact_no = ?, Role = ? WHERE User_ID = ?",
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getContactNo(),
                userDTO.getRole(),
                userDTO.getUserId()
        );
    }

    public boolean deleteUser(String userId) throws SQLException {
        return CrudUtil.execute("DELETE FROM User WHERE User_ID = ?", userId);
    }

    public List<UserDTO> getAllUsers() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM User ORDER BY User_ID");

        List<UserDTO> userList = new ArrayList<>();
        while (rs.next()) {
            userList.add(new UserDTO(
                    rs.getString("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password"),
                    rs.getString("Email"),
                    rs.getString("Contact_no"),
                    rs.getString("Role")
            ));
        }
        return userList;
    }

    public List<UserDTO> searchUsers(String searchText) throws SQLException {
        String query = "SELECT * FROM User WHERE User_Name LIKE ? OR Email LIKE ? OR User_ID LIKE ?";
        ResultSet rs = CrudUtil.execute(query, "%" + searchText + "%", "%" + searchText + "%", "%" + searchText + "%");

        List<UserDTO> userList = new ArrayList<>();
        while (rs.next()) {
            userList.add(new UserDTO(
                    rs.getString("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password"),
                    rs.getString("Email"),
                    rs.getString("Contact_no"),
                    rs.getString("Role")
            ));
        }
        return userList;
    }

    // Get user by ID
    public UserDTO getUserById(String userId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM User WHERE User_ID = ?", userId);

        if (rs.next()) {
            return new UserDTO(
                    rs.getString("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password"),
                    rs.getString("Email"),
                    rs.getString("Contact_no"),
                    rs.getString("Role")
            );
        }
        return null;
    }

    // Generate next User ID (U001, U002, ...)
    public String generateNextUserId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT User_ID FROM User ORDER BY User_ID DESC LIMIT 1");

        if (rs.next()) {
            String lastId = rs.getString("User_ID");
            int num = Integer.parseInt(lastId.replace("U", ""));
            return String.format("U%03d", num + 1);
        }
        return "U001";
    }

    // Login validation
    public UserDTO validateLogin(String userName, String password) throws SQLException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM User WHERE User_Name = ? AND Password = ?",
                userName,
                password
        );

        if (rs.next()) {
            return new UserDTO(
                    rs.getString("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password"),
                    rs.getString("Email"),
                    rs.getString("Contact_no"),
                    rs.getString("Role")
            );
        }
        return null;
    }

    // FIXED: Use CrudUtil instead of direct connection
    public boolean updateUserCredentials(String currentUsername, String newUsername, String newPassword) throws SQLException {
        String sql = "UPDATE User SET User_Name = ?, Password = ? WHERE User_Name = ?";
        return CrudUtil.execute(sql, newUsername, newPassword, currentUsername);
    }
}*/
