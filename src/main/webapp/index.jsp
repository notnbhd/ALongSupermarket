<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome - Supermarket Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('<%= request.getContextPath() %>/images/back.jpg') no-repeat center center fixed;
            background-size: cover;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0;
        }
        .container {
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            text-align: center;
        }
        h1 {
            color: #ff91a4;
            margin-bottom: 20px;
        }
        a {
            display: inline-block;
            margin: 10px;
            padding: 12px 24px;
            background: linear-gradient(135deg, #ffc0cb 0%, #ffb6c1 100%);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 600;
        }
        a:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(255, 182, 193, 0.4);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🛒 Welcome to Supermarket Management System</h1>
        <p>Your one-stop solution for supermarket operations</p>
        <div>
            <a href="<%= request.getContextPath() %>/views/Member/LoginView.jsp">Login</a>
            <a href="<%= request.getContextPath() %>/views/Customer/MemberRegistrationView.jsp">Register</a>
        </div>
    </div>
</body>
</html>
