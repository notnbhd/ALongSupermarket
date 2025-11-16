package com.supermarket.dao;

import com.supermarket.model.Customer;
import com.supermarket.model.CustomerStat;
import com.supermarket.model.Member;
import com.supermarket.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CustomerStatDAO {

    public Map<Customer, CustomerStat> getCustomerStat(Date startDate, Date endDate) {
        Map<Customer, CustomerStat> customerStatMap = new LinkedHashMap<>();
        
        String sql = "SELECT " +
                    "    c.ID as customerId, " +
                    "    m.username, " +
                    "    m.password, " +
                    "    m.fullname, " +
                    "    m.phone, " +
                    "    m.email, " +
                    "    m.address, " +
                    "    m.dob, " +
                    "    COUNT(DISTINCT b.ID) as totalBill, " +
                    "    COALESCE(SUM(ci.price * ci.quantity), 0) as totalAmount " +
                    "FROM tblCustomer c " +
                    "INNER JOIN tblMember m ON c.ID = m.ID " +
                    "LEFT JOIN tblBill b ON b.tblCustomerID = c.ID " +
                    "    AND b.date BETWEEN ? AND ? " +
                    "LEFT JOIN tblOnlineBill ob ON ob.tblBillID = b.ID " +
                    "LEFT JOIN tblOnsiteBill osb ON osb.tblBillID = b.ID " +
                    "LEFT JOIN tblCart cart ON cart.ID = COALESCE(ob.tblCartID, osb.tblOnsiteCartID) " +
                    "LEFT JOIN tblCartItem ci ON ci.tblCartID = cart.ID " +
                    "GROUP BY c.ID, m.username, m.password, m.fullname, m.phone, m.email, m.address, m.dob " +
                    "HAVING totalBill > 0 " +
                    "ORDER BY totalAmount DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Member member = new Member(
                    rs.getInt("customerId"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getDate("dob")
                );
                
                Customer customer = new Customer(rs.getInt("customerId"), member);
                
                int totalBill = rs.getInt("totalBill");
                BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
                CustomerStat customerStat = new CustomerStat(totalBill, totalAmount);
                
                customerStatMap.put(customer, customerStat);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customerStatMap;
    }

    public Map<Customer, CustomerStat> getAllCustomerStat() {
        Map<Customer, CustomerStat> customerStatMap = new LinkedHashMap<>();
        
        String sql = "SELECT " +
                    "    c.ID as customerId, " +
                    "    m.username, " +
                    "    m.password, " +
                    "    m.fullname, " +
                    "    m.phone, " +
                    "    m.email, " +
                    "    m.address, " +
                    "    m.dob, " +
                    "    COUNT(DISTINCT b.ID) as totalBill, " +
                    "    COALESCE(SUM(ci.price * ci.quantity), 0) as totalAmount " +
                    "FROM tblCustomer c " +
                    "INNER JOIN tblMember m ON c.ID = m.ID " +
                    "LEFT JOIN tblBill b ON b.tblCustomerID = c.ID " +
                    "LEFT JOIN tblOnlineBill ob ON ob.tblBillID = b.ID " +
                    "LEFT JOIN tblOnsiteBill osb ON osb.tblBillID = b.ID " +
                    "LEFT JOIN tblCart cart ON cart.ID = COALESCE(ob.tblCartID, osb.tblOnsiteCartID) " +
                    "LEFT JOIN tblCartItem ci ON ci.tblCartID = cart.ID " +
                    "GROUP BY c.ID, m.username, m.password, m.fullname, m.phone, m.email, m.address, m.dob " +
                    "HAVING totalBill > 0 " +
                    "ORDER BY totalAmount DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Member member = new Member(
                    rs.getInt("customerId"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getDate("dob")
                );
                
                Customer customer = new Customer(rs.getInt("customerId"), member);
                
                int totalBill = rs.getInt("totalBill");
                BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
                CustomerStat customerStat = new CustomerStat(totalBill, totalAmount);
                
                customerStatMap.put(customer, customerStat);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customerStatMap;
    }
}
