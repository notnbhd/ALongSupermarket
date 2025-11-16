<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.supermarket.model.Member" %>
<%
    // Check if management staff is logged in
    Member loggedInStaff = (Member) session.getAttribute("loggedInStaff");
    if (loggedInStaff == null) {
        response.sendRedirect(request.getContextPath() + "/views/ManagementStaffLoginView.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Select Statistics - Supermarket Management</title>
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

        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }

        .menu-item {
            background: linear-gradient(135deg, #fff5f7 0%, #ffe9ed 100%);
            border: 2px solid #ffc0cb;
            border-radius: 12px;
            padding: 30px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            color: inherit;
            display: block;
        }

        .menu-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(255, 182, 193, 0.3);
            border-color: #ffb6c1;
        }

        .menu-icon {
            font-size: 48px;
            margin-bottom: 15px;
        }

        .menu-title {
            font-size: 18px;
            font-weight: 600;
            color: #333;
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
    </style>
</head>
<body>
    <!-- Step 3: SelectStatView.jsp displays itself to the staff -->
    <div class="navbar">
        <div class="navbar-brand">🏪 Supermarket Management</div>
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
        <a href="<%= request.getContextPath() %>/views/ManagementStaff/ManagementStaffHomeView.jsp"
           class="btn-secondary">← Back to Home</a>
        
        <div class="card">
            <div class="card-header">Select Statistics Type</div>
            
            <div class="menu-grid">
                <!-- Step 4: The staff clicks on View customer statistics function -->
                <!-- Step 5: SelectStatView.jsp page calls the CustomerStatView.jsp page -->
                <a id="btnCustomerStat" href="<%= request.getContextPath() %>/views/ManagementStaff/CustomerStatView.jsp" class="menu-item">
                    <div class="menu-icon">👤</div>
                    <div class="menu-title">Customer Statistics</div>
                </a>
                
                <div id="btnItemStat" class="menu-item">
                    <div class="menu-icon">💰</div>
                    <div class="menu-title">Revenue Statistics</div>
                </div>
                
                <div class="menu-item">
                    <div class="menu-icon">📊</div>
                    <div class="menu-title">Product Statistics</div>
                </div>
                
                <div id="btnSalesStat" class="menu-item">
                    <div class="menu-icon">📈</div>
                    <div class="menu-title">Sales Statistics</div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
