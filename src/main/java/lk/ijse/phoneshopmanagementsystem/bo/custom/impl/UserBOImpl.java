package lk.ijse.phoneshopmanagementsystem.bo.custom.impl;

import lk.ijse.phoneshopmanagementsystem.bo.custom.UserBO;
import lk.ijse.phoneshopmanagementsystem.dao.DaoFactory;
import lk.ijse.phoneshopmanagementsystem.dao.custom.UserDAO;
import lk.ijse.phoneshopmanagementsystem.dto.UserDTO;
import lk.ijse.phoneshopmanagementsystem.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DaoFactory.getInstance().getDAO(DaoFactory.DAOType.USER);

    @Override
    public String getNextUserID() throws SQLException, ClassNotFoundException {
        return userDAO.getNextID();
    }

    @Override
    public boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDAO.save(
                new User(
                        userDTO.getUserId(),
                        userDTO.getUserName(),
                        userDTO.getPassword(),
                        userDTO.getEmail(),
                        userDTO.getContactNo(),
                        userDTO.getRole()
                )
        );
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDAO.update(
                new User(
                        userDTO.getUserId(),
                        userDTO.getUserName(),
                        userDTO.getPassword(),
                        userDTO.getEmail(),
                        userDTO.getContactNo(),
                        userDTO.getRole()
                )
        );
    }

    @Override
    public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.delete(userId);
    }

    @Override
    public ArrayList<UserDTO> getAllUser() throws SQLException, ClassNotFoundException {
        ArrayList<User> userList = userDAO.getAll();
        ArrayList<UserDTO> dtoList = new ArrayList<>();

        for (User user : userList) {
            dtoList.add(
                    new UserDTO(
                            user.getUserId(),
                            user.getUserName(),
                            user.getPassword(),
                            user.getEmail(),
                            user.getContactNo(),
                            user.getRole()
                    )
            );
        }

        return dtoList;
    }

    @Override
    public UserDTO searchUser(String userId) throws SQLException, ClassNotFoundException {
        User user = userDAO.search(userId);

        if (user == null) return null;

        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getContactNo(),
                user.getRole()
        );
    }

    @Override
    public UserDTO validateLogin(String userName, String password)
            throws SQLException, ClassNotFoundException {

        User user = userDAO.validateLogin(userName, password);

        if (user == null) return null;

        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getContactNo(),
                user.getRole()
        );
    }

    @Override
    public boolean updateUserCredentials(String currentUsername, String newUsername, String newPassword) throws SQLException, ClassNotFoundException {
        return userDAO.updateUserCredentials(currentUsername, newUsername, newPassword);
    }
}
