package com.supermarket.dao;

import com.supermarket.model.Member;
import com.supermarket.model.Staff;
import com.supermarket.util.DatabaseConnection;

import java.sql.*;

public class StaffDAO {

    public Staff findByUsernameAndPassword(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            String sql = "SELECT s.ID, s.role, m.username, m.password, m.fullname, m.phone, m.email, m.address, m.dob " +
                        "FROM tblStaff s " +
                        "INNER JOIN tblMember m ON s.ID = m.ID " +
                        "WHERE m.username = ? AND m.password = ?";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                Member member = new Member(
                    resultSet.getInt("ID"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("fullname"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("address"),
                    resultSet.getDate("dob")
                );
                
                Staff staff = new Staff(
                    resultSet.getInt("ID"),
                    resultSet.getString("role"),
                    member
                );
                
                return staff;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }

    public Staff findById(int staffId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            String sql = "SELECT s.ID, s.role, m.username, m.password, m.fullname, m.phone, m.email, m.address, m.dob " +
                        "FROM tblStaff s " +
                        "INNER JOIN tblMember m ON s.ID = m.ID " +
                        "WHERE s.ID = ?";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, staffId);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                Member member = new Member(
                    resultSet.getInt("ID"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("fullname"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("address"),
                    resultSet.getDate("dob")
                );
                
                Staff staff = new Staff(
                    resultSet.getInt("ID"),
                    resultSet.getString("role"),
                    member
                );
                
                return staff;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
}
