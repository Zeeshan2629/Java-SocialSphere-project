package com.socialsphere.dao;

import java.sql.SQLException;

public interface UserDAO {
    boolean isUsernameTaken(String username) throws SQLException;
    boolean registerUser(String username, String password, String email) throws SQLException;
    boolean validateUser(String username, String password) throws SQLException;
    int getUserId(String username) throws SQLException;
}
