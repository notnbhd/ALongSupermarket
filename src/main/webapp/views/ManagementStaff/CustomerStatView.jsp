<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.supermarket.model.Member, com.supermarket.model.Customer, com.supermarket.model.CustomerStat" %>
<%@ page import="java.util.Map, java.text.NumberFormat, java.util.Locale" %>
<%
    // Check if management staff is logged in
    Member loggedInStaff = (Member) session.getAttribute("loggedInStaff");
    if (loggedInStaff == null) {
        response.sendRedirect(request.getContextPath() + "/views/ManagementStaffLoginView.jsp");
        return;
    }
    
    // Get customer statistics from request
    @SuppressWarnings("unchecked")
    Map<Customer, CustomerStat> customerStatMap = (Map<Customer, CustomerStat>) request.getAttribute("customerStatMap");
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");
    String error = (String) request.getAttribute("error");
    
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Statistics - ALong Supermarket</title>
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
            border: none;
            cursor: pointer;
        }

        .btn-secondary:hover {
            background: #e0e0e0;
        }

        .btn-primary {
            padding: 12px 30px;
            background: linear-gradient(135deg, #ffc0cb 0%, #ffb6c1 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 15px rgba(255, 182, 193, 0.4);
        }

        .date-range {
            display: flex;
            gap: 20px;
            align-items: flex-end;
            margin-bottom: 30px;
        }

        .form-group {
            flex: 1;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }

        input[type="date"] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 14px;
            transition: all 0.3s;
        }

        input:focus {
            outline: none;
            border-color: #ffb6c1;
            box-shadow: 0 0 0 3px rgba(255, 182, 193, 0.2);
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

        .stats-table tbody tr {
            cursor: pointer;
            transition: all 0.2s;
        }

        .stats-table tbody tr:hover {
            background: #fff5f7;
        }

        .stats-table tfoot tr {
            cursor: default;
        }

        .stats-table tfoot td {
            font-size: 16px;
            color: #333;
            border-bottom: none;
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
        <div class="navbar-brand">ALONG Supermarket</div>
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
        <a href="<%= request.getContextPath() %>/views/ManagementStaff/SelectStatView.jsp"
           class="btn-secondary">← Back to Statistics</a>
        
        <div class="card">
            <div class="card-header">Customer Statistics</div>
            
            <% if (error != null && !error.isEmpty()) { %>
                <div class="alert alert-error">
                    <%= error %>
                </div>
            <% } %>

            <form action="<%= request.getContextPath() %>/CustomerStatController" method="get">
                <div class="date-range">
                    <div class="form-group">
                        <label for="inStartDate">Start Date</label>
                        <input type="date" id="inStartDate" name="startDate" 
                               value="<%= startDate != null ? startDate : "2025-01-01" %>">
                    </div>
                    <div class="form-group">
                        <label for="inEndDate">End Date</label>
                        <input type="date" id="inEndDate" name="endDate" 
                               value="<%= endDate != null ? endDate : java.time.LocalDate.now() %>">
                    </div>
                    <button type="submit" id="btnView" class="btn-primary">View</button>
                </div>
            </form>

            <% if (customerStatMap != null && !customerStatMap.isEmpty()) {
                // Calculate totals
                int totalBillsSum = 0;
                java.math.BigDecimal totalAmountSum = java.math.BigDecimal.ZERO;
                for (Map.Entry<Customer, CustomerStat> entry : customerStatMap.entrySet()) {
                    CustomerStat stat = entry.getValue();
                    totalBillsSum += stat.getTotalBill();
                    totalAmountSum = totalAmountSum.add(stat.getTotalAmount());
                }
            %>
                <table id="tblCustomerStat" class="stats-table">
                    <thead>
                        <tr>
                            <th>Customer Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th style="text-align: right;">Total Bills</th>
                            <th style="text-align: right;">Total Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Map.Entry<Customer, CustomerStat> entry : customerStatMap.entrySet()) {
                            Customer customer = entry.getKey();
                            CustomerStat stat = entry.getValue();
                            String dateParams = "";
                            if (startDate != null && endDate != null) {
                                dateParams = "&startDate=" + startDate + "&endDate=" + endDate;
                            }
                        %>

                        <tr onclick="window.location.href='<%= request.getContextPath() %>/BillController?customerId=<%= customer.getId() %><%= dateParams %>'">
                            <td><%= customer.getMember().getFullname() %></td>
                            <td><%= customer.getMember().getEmail() %></td>
                            <td><%= customer.getMember().getPhone() %></td>
                            <td style="text-align: right;"><%= stat.getTotalBill() %></td>
                            <td style="text-align: right;"><%= String.format("%,.0f đ", stat.getTotalAmount()) %></td>
                        </tr>
                        <% } %>
                    </tbody>
                    <tfoot>
                        <tr style="background: linear-gradient(135deg, #fff5f7 0%, #ffe9ed 100%); font-weight: 700; border-top: 2px solid #ffb6c1;">
                            <td colspan="3" style="text-align: right; padding-right: 20px;">TOTAL:</td>
                            <td style="text-align: right;"><%= totalBillsSum %></td>
                            <td style="text-align: right;"><%= String.format("%,.0f đ", totalAmountSum) %></td>
                        </tr>
                    </tfoot>
                </table>
            <% } else if (customerStatMap != null) { %>
                <div class="no-data">
                    No customer data found for the selected date range.
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>
