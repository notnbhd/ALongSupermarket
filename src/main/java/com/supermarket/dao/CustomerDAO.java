package com.supermarket.dao;

import com.supermarket.model.Customer;
import com.supermarket.model.Member;
import com.supermarket.util.DatabaseConnection;

import java.sql.*;

public class CustomerDAO {
    public boolean createCustomer(Customer customer) {
        Connection connection = null;
        PreparedStatement psMember = null;
        PreparedStatement psCustomer = null;
        ResultSet generatedKeys = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);
            
            String sqlMember = "INSERT INTO tblMember (username, password, fullname, phone, email, address, dob) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            psMember = connection.prepareStatement(sqlMember, Statement.RETURN_GENERATED_KEYS);
            psMember.setString(1, customer.getMember().getUsername());
            psMember.setString(2, customer.getMember().getPassword());
            psMember.setString(3, customer.getMember().getFullname());
            psMember.setString(4, customer.getMember().getPhone());
            psMember.setString(5, customer.getMember().getEmail());
            psMember.setString(6, customer.getMember().getAddress());
            psMember.setDate(7, customer.getMember().getDob());
            
            int memberRows = psMember.executeUpdate();
            
            if (memberRows == 0) {
                connection.rollback();
                return false;
            }
            
            generatedKeys = psMember.getGeneratedKeys();
            int memberId = 0;
            if (generatedKeys.next()) {
                memberId = generatedKeys.getInt(1);
            } else {
                connection.rollback();
                return false;
            }

            String sqlCustomer = "INSERT INTO tblCustomer (ID) VALUES (?)";
            
            psCustomer = connection.prepareStatement(sqlCustomer);
            psCustomer.setInt(1, memberId);
            
            int customerRows = psCustomer.executeUpdate();
            
            if (customerRows > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating customer: " + e.getMessage());
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (psCustomer != null) psCustomer.close();
                if (psMember != null) psMember.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Customer findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT c.ID, " +
                    "m.username, m.password, m.fullname, m.phone, " +
                    "m.email, m.address, m.dob " +
                    "FROM tblCustomer c " +
                    "INNER JOIN tblMember m ON c.ID = m.ID " +
                    "WHERE m.username = ? AND m.password = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); 
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                Member member = new Member();
                int customerId = resultSet.getInt("ID");
                member.setId(customerId);
                member.setUsername(resultSet.getString("username"));
                member.setPassword(resultSet.getString("password"));
                member.setFullname(resultSet.getString("fullname"));
                member.setPhone(resultSet.getString("phone"));
                member.setEmail(resultSet.getString("email"));
                member.setAddress(resultSet.getString("address"));
                member.setDob(resultSet.getDate("dob"));
                
                Customer customer = new Customer();
                customer.setId(customerId);  
                customer.setMember(member);
                
                return customer;
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding customer: " + e.getMessage());
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

    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM tblMember WHERE username = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
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
        
        return false;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM tblMember WHERE email = ?";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
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
        
        return false;
    }
    
    /**
     * Find customer by ID
     * Returns Customer object with Member information
     */
    public Customer findById(int customerId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            
            String sql = "SELECT c.ID, m.username, m.password, m.fullname, m.phone, m.email, m.address, m.dob " +
                        "FROM tblCustomer c " +
                        "INNER JOIN tblMember m ON c.ID = m.ID " +
                        "WHERE c.ID = ?";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            
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
                
                Customer customer = new Customer(resultSet.getInt("ID"), member);
                return customer;
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
