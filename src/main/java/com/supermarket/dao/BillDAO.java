package com.supermarket.dao;

import com.supermarket.model.Bill;
import com.supermarket.model.Customer;
import com.supermarket.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private CustomerDAO customerDAO;
    
    public BillDAO() {
        this.customerDAO = new CustomerDAO();
    }

    public List<Bill> getBill(int customerId, Date startDate, Date endDate) {
        List<Bill> bills = new ArrayList<>();
        
        String sql = "SELECT " +
                    "    b.ID as billId, " +
                    "    b.paymentMethod, " +
                    "    b.status, " +
                    "    b.date, " +
                    "    b.tblCustomerID, " +
                    "    COALESCE(SUM(ci.price * ci.quantity), 0) as totalAmount " +
                    "FROM tblBill b " +
                    "LEFT JOIN tblOnlineBill ob ON ob.tblBillID = b.ID " +
                    "LEFT JOIN tblOnsiteBill osb ON osb.tblBillID = b.ID " +
                    "LEFT JOIN tblCartItem ci ON (ci.tblCartID = ob.tblCartID OR ci.tblCartID = osb.tblOnsiteCartID) " +
                    "WHERE b.tblCustomerID = ? " +
                    "  AND b.date BETWEEN ? AND ? " +
                    "GROUP BY b.ID, b.paymentMethod, b.status, b.date, b.tblCustomerID " +
                    "ORDER BY b.date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, customerId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            
            ResultSet rs = ps.executeQuery();
            
            Customer customer = customerDAO.findById(customerId);
            
            while (rs.next()) {
                // Create Bill object with Customer reference
                Bill bill = new Bill(
                    rs.getInt("billId"),
                    rs.getString("paymentMethod"),
                    rs.getString("status"),
                    rs.getTimestamp("date"),
                    customer,
                    rs.getBigDecimal("totalAmount")
                );
                
                bills.add(bill);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return bills;
    }
    
    public List<Bill> getBill(int customerId) {
        List<Bill> bills = new ArrayList<>();
        
        String sql = "SELECT " +
                    "    b.ID as billId, " +
                    "    b.paymentMethod, " +
                    "    b.status, " +
                    "    b.date, " +
                    "    b.tblCustomerID, " +
                    "    COALESCE(SUM(ci.price * ci.quantity), 0) as totalAmount " +
                    "FROM tblBill b " +
                    "LEFT JOIN tblOnlineBill ob ON ob.tblBillID = b.ID " +
                    "LEFT JOIN tblOnsiteBill osb ON osb.tblBillID = b.ID " +
                    "LEFT JOIN tblCartItem ci ON (ci.tblCartID = ob.tblCartID OR ci.tblCartID = osb.tblOnsiteCartID) " +
                    "WHERE b.tblCustomerID = ? " +
                    "GROUP BY b.ID, b.paymentMethod, b.status, b.date, b.tblCustomerID " +
                    "ORDER BY b.date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, customerId);
            
            ResultSet rs = ps.executeQuery();
            
            Customer customer = customerDAO.findById(customerId);
            
            while (rs.next()) {
                // Create Bill object with Customer reference
                Bill bill = new Bill(
                    rs.getInt("billId"),
                    rs.getString("paymentMethod"),
                    rs.getString("status"),
                    rs.getTimestamp("date"),
                    customer,
                    rs.getBigDecimal("totalAmount")
                );
                
                bills.add(bill);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return bills;
    }

    public int getItemCountForBill(int billId) {
        String sql = "SELECT COALESCE(SUM(ci.quantity), 0) as itemCount " +
                    "FROM tblCartItem ci " +
                    "LEFT JOIN tblOnlineBill ob ON ob.tblCartID = ci.tblCartID " +
                    "LEFT JOIN tblOnsiteBill osb ON osb.tblOnsiteCartID = ci.tblCartID " +
                    "WHERE ob.tblBillID = ? OR osb.tblBillID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, billId);
            ps.setInt(2, billId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("itemCount");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    public List<Bill> getBillByDateRange(int customerId, Date startDate, Date endDate) {
        List<Bill> bills = new ArrayList<>();
        
        String sql = "SELECT " +
                    "    b.ID as billId, " +
                    "    b.paymentMethod, " +
                    "    b.status, " +
                    "    b.date, " +
                    "    b.tblCustomerID, " +
                    "    COALESCE(SUM(ci.price * ci.quantity), 0) as totalAmount " +
                    "FROM tblBill b " +
                    "LEFT JOIN tblOnlineBill ob ON ob.tblBillID = b.ID " +
                    "LEFT JOIN tblOnsiteBill osb ON osb.tblBillID = b.ID " +
                    "LEFT JOIN tblCartItem ci ON (ci.tblCartID = ob.tblCartID OR ci.tblCartID = osb.tblOnsiteCartID) " +
                    "WHERE b.tblCustomerID = ? " +
                    "  AND b.date BETWEEN ? AND ? " +
                    "GROUP BY b.ID, b.paymentMethod, b.status, b.date, b.tblCustomerID " +
                    "ORDER BY b.date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, customerId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            
            ResultSet rs = ps.executeQuery();
            
            Customer customer = customerDAO.findById(customerId);
            
            while (rs.next()) {
                Bill bill = new Bill(
                    rs.getInt("billId"),
                    rs.getString("paymentMethod"),
                    rs.getString("status"),
                    rs.getTimestamp("date"),
                    customer,
                    rs.getBigDecimal("totalAmount")
                );
                
                bills.add(bill);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return bills;
    }
}
