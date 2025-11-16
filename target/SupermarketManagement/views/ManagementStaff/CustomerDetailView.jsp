<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.supermarket.model.Member, com.supermarket.model.Customer, com.supermarket.model.Bill" %>
<%@ page import="com.supermarket.dao.BillDAO" %>
<%@ page import="java.util.List, java.text.SimpleDateFormat, java.math.BigDecimal" %>
<%
    // Check if management staff is logged in
    Member loggedInStaff = (Member) session.getAttribute("loggedInStaff");
    if (loggedInStaff == null) {
        response.sendRedirect(request.getContextPath() + "/views/ManagementStaffLoginView.jsp");
        return;
    }
    
    // Get customer and bills from request
    Customer customer = (Customer) request.getAttribute("customer");
    @SuppressWarnings("unchecked")
    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
    BillDAO billDAO = (BillDAO) request.getAttribute("billDAO");
    String error = (String) request.getAttribute("error");
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
    // Calculate totals
    int totalBills = 0;
    BigDecimal totalSpent = BigDecimal.ZERO;
    if (bills != null) {
        totalBills = bills.size();
        for (Bill bill : bills) {
            totalSpent = totalSpent.add(bill.getTotalAmount());
        }
    }
    BigDecimal avgBill = totalBills > 0 ? totalSpent.divide(new BigDecimal(totalBills), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Details - ALong Supermarket</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: url('<%= request.getContextPath() %>/images/back.jpg') no-repeat center center fixed;
            background-size: cover;
            min-height: 100vh;
            padding: 20px;
        }

        .navbar {
            background: white;
            padding: 15px 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar-brand {
            font-size: 20px;
            font-weight: 700;
            color: #ffb6c1;
        }

        .navbar-user {
            display: flex;
            align-items: center;
            gap: 15px;
            color: #666;
        }
        
        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .btn-logout {
            padding: 8px 20px;
            background: linear-gradient(135deg, #ffc0cb 0%, #ffb6c1 100%);
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            font-size: 14px;
            transition: all 0.3s;
            border: none;
            cursor: pointer;
        }

        .btn-logout:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 10px rgba(255, 182, 193, 0.4);
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: linear-gradient(135deg, #ffc0cb 0%, #ffb6c1 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 600;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin-bottom: 20px;
        }

        .card-header {
            font-size: 24px;
            font-weight: 700;
            color: #333;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #ffb6c1;
        }

        .btn-secondary {
            padding: 12px 30px;
            background: #f5f5f5;
            color: #666;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 600;
            display: inline-block;
            margin-bottom: 20px;
            transition: all 0.3s;
        }

        .btn-secondary:hover {
            background: #e0e0e0;
        }

        .customer-header {
            background: linear-gradient(135deg, #fff5f7 0%, #ffe9ed 100%);
            border: 2px solid #ffc0cb;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 25px;
        }

        .customer-info {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }

        .info-item {
            display: flex;
            flex-direction: column;
        }

        .info-label {
            font-size: 12px;
            color: #666;
            margin-bottom: 5px;
        }

        .info-value {
            font-size: 16px;
            font-weight: 600;
            color: #333;
        }

        .summary-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .summary-card {
            background: linear-gradient(135deg, #fff5f7 0%, #ffe9ed 100%);
            border: 2px solid #ffc0cb;
            border-radius: 12px;
            padding: 20px;
            text-align: center;
        }

        .summary-number {
            font-size: 32px;
            font-weight: 700;
            color: #ffb6c1;
            margin-bottom: 5px;
        }

        .summary-label {
            font-size: 14px;
            color: #666;
        }

        .stats-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .stats-table th,
        .stats-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }

        .stats-table th {
            background: linear-gradient(135deg, #fff5f7 0%, #ffe9ed 100%);
            color: #333;
            font-weight: 600;
        }

        .alert {
            padding: 12px 16px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        .alert-error {
            background: #ffe9e9;
            border: 1px solid #ffcccc;
            color: #cc0000;
        }

        .status-badge {
            padding: 4px 12px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 600;
        }

        .status-complete {
            background: #e6f7e6;
            color: #2d8a2d;
        }

        .status-pending {
            background: #fff3cd;
            color: #997404;
        }

        .no-data {
            text-align: center;
            padding: 40px;
            color: #999;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <div class="navbar-brand">ALong Supermarket</div>
        <div class="navbar-user">
            <div class="user-info">
                <div class="user-avatar">
                    <%= loggedInStaff.getFullname().substring(0, 1).toUpperCase() %>
                </div>
                <span><strong><%= loggedInStaff.getFullname() %></strong> - Management Staff</span>
            </div>
            <a href="<%= request.getContextPath() %>/LogoutController" class="btn-logout">Logout</a>
        </div>
    </div>

    <div class="container">
        <a id="btnBack" href="javascript:history.back()" class="btn-secondary">← Back to Statistics</a>
        
        <div class="card">
            <div class="card-header">
                Customer Details
                <% if (startDate != null && endDate != null) { %>
                    <span style="font-size: 16px; font-weight: normal; color: #666; margin-left: 10px;">
                        (Bills from <%= startDate %> to <%= endDate %>)
                    </span>
                <% } %>
            </div>
            
            <% if (error != null && !error.isEmpty()) { %>
                <div class="alert alert-error">
                    <%= error %>
                </div>
            <% } %>
            
            <% if (customer != null) { %>
                <div class="customer-header">
                    <div class="customer-info">
                        <div class="info-item">
                            <div class="info-label">Customer Name</div>
                            <div class="info-value"><%= customer.getMember().getFullname() %></div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Email</div>
                            <div class="info-value"><%= customer.getMember().getEmail() %></div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Phone</div>
                            <div class="info-value"><%= customer.getMember().getPhone() %></div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Address</div>
                            <div class="info-value"><%= customer.getMember().getAddress() != null ? customer.getMember().getAddress() : "N/A" %></div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Date of Birth</div>
                            <div class="info-value"><%= customer.getMember().getDob() != null ? dateFormat.format(customer.getMember().getDob()) : "N/A" %></div>
                        </div>
                    </div>
                </div>

                <div class="summary-cards">
                    <div class="summary-card">
                        <div class="summary-number"><%= totalBills %></div>
                        <div class="summary-label">Total Bills</div>
                    </div>
                    <div class="summary-card">
                        <div class="summary-number"><%= String.format("%,.0f", totalSpent) %>đ</div>
                        <div class="summary-label">Total Spent</div>
                    </div>
                    <div class="summary-card">
                        <div class="summary-number"><%= String.format("%,.0f", avgBill) %>đ</div>
                        <div class="summary-label">Average Bill</div>
                    </div>
                </div>

                <h3 style="margin-bottom: 15px; color: #333;">Bill History</h3>
                
                <% if (bills != null && !bills.isEmpty()) { %>
                    <table id="tblBill" class="stats-table">
                        <thead>
                            <tr>
                                <th>Bill ID</th>
                                <th>Date</th>
                                <th style="text-align: right;">Total Amount</th>
                                <th style="text-align: right;">Items Count</th>
                                <th>Payment Method</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Bill bill : bills) { 
                                int itemCount = billDAO != null ? billDAO.getItemCountForBill(bill.getId()) : 0;
                            %>
                            <tr>
                                <td>#<%= bill.getId() %></td>
                                <td><%= dateFormat.format(bill.getDate()) %></td>
                                <td style="text-align: right;"><%= String.format("%,.0f", bill.getTotalAmount()) %>đ</td>
                                <td style="text-align: right;"><%= itemCount %></td>
                                <td><%= bill.getPaymentMethod() != null ? bill.getPaymentMethod() : "N/A" %></td>
                                <td>
                                    <span class="status-badge <%= bill.getStatus() != null && bill.getStatus().equalsIgnoreCase("Complete") ? "status-complete" : "status-pending" %>">
                                        <%= bill.getStatus() != null ? bill.getStatus() : "Pending" %>
                                    </span>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="no-data">
                        No bill history found for this customer.
                    </div>
                <% } %>
            <% } %>
        </div>
    </div>
</body>
</html>
