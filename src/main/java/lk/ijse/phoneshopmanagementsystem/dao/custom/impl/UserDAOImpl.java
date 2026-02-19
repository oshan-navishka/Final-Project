package lk.ijse.phoneshopmanagementsystem.dao.custom.impl;

import lk.ijse.phoneshopmanagementsystem.dao.custom.UserDAO;
import lk.ijse.phoneshopmanagementsystem.dto.UserDTO;
import lk.ijse.phoneshopmanagementsystem.entity.User;
import lk.ijse.phoneshopmanagementsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    public String getNextID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT User_ID FROM User ORDER BY User_ID DESC LIMIT 1");

        if (rs.next()) {
            String lastId = rs.getString("User_ID");
            int num = Integer.parseInt(lastId.replace("U", ""));
            return String.format("U%03d", num + 1);
        }
        return "U001";
    }

    public boolean save(User userDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO User (User_ID, User_Name, Password, Email, Contact_no, Role) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getContactNo(),
                userDTO.getRole()
        );
    }

    public boolean update(User userDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE User SET User_Name = ?, Password = ?, Email = ?, Contact_no = ?, Role = ?" +
                " WHERE User_ID = ?",
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getContactNo(),
                userDTO.getRole(),
                userDTO.getUserId()
        );
    }

    public boolean delete(String userId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM User WHERE User_ID = ?", userId);
    }

    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM User ORDER BY User_ID");

        ArrayList<User> userList = new ArrayList<>();

        while (resultSet.next()){
            User user = new User(
                    resultSet.getString("User_ID"),
                    resultSet.getString("User_Name"),
                    resultSet.getString("Password"),
                    resultSet.getString("Email"),
                    resultSet.getString("Contact_no"),
                    resultSet.getString("Role")
            );
            userList.add(user);
        }
        return userList;
    }

    public User search(String userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM User WHERE User_ID=?", userId
        );

        if (resultSet.next()){
            return new User(
                    resultSet.getString("User_ID"),
                    resultSet.getString("User_Name"),
                    resultSet.getString("Password"),
                    resultSet.getString("Email"),
                    resultSet.getString("Contact_no"),
                    resultSet.getString("Role")
            );
        }
        return null;
    }

    public User validateLogin(String userName, String password) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM User WHERE User_Name = ? AND Password = ?",
                userName,
                password
        );

        if (rs.next()) {
            return new User(
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

    public boolean updateUserCredentials(String currentUsername, String newUsername, String newPassword) throws SQLException, ClassNotFoundException {
       return CrudUtil.execute("UPDATE User SET User_Name = ?, Password = ? WHERE User_Name = ?",
                newUsername,
                newPassword,
                currentUsername
        );
    }
}
