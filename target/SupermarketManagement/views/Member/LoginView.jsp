<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ALong Supermarket</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: url('<%= request.getContextPath() %>/images/back1.png') no-repeat center center fixed;
            background-size: cover;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            background: white;
            border-radius: 12px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            overflow: hidden;
            max-width: 450px;
            width: 100%;
        }

        .header {
            background: linear-gradient(135deg, #ffc0cb 0%, #ffb6c1 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }

        .header h1 {
            font-size: 28px;
            margin-bottom: 5px;
        }

        .header p {
            font-size: 14px;
            opacity: 0.9;
        }

        .content {
            padding: 40px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }

        input[type="text"],
        input[type="password"] {
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

        .btn {
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            margin-top: 10px;
        }

        .btn-primary {
            background: linear-gradient(135deg, #ffc0cb 0%, #ffb6c1 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(255, 182, 193, 0.4);
        }

        .btn-secondary {
            background: #f5f5f5;
            color: #666;
        }

        .btn-secondary:hover {
            background: #e0e0e0;
        }

        .link-text {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
            color: #666;
        }

        .link-text a {
            color: #ff91a4;
            text-decoration: none;
            font-weight: 600;
        }

        .link-text a:hover {
            text-decoration: underline;
        }

        .alert {
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .alert-error {
            background-color: #fee;
            border: 1px solid #fcc;
            color: #c33;
        }

        .alert-success {
            background-color: #efe;
            border: 1px solid #cfc;
            color: #3c3;
        }

        .alert-icon {
            font-weight: bold;
            font-size: 18px;
        }

        .help-text {
            font-size: 12px;
            color: #999;
            margin-top: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Welcome Back</h1>
            <p>Sign in to your account</p>
        </div>
        <div class="content">
            <!-- Error/Success Messages -->
            <% 
                String errorMessage = (String) request.getAttribute("errorMessage");
                String successMessage = (String) request.getAttribute("successMessage");
                String error = request.getParameter("error");
                String success = request.getParameter("success");
                
                if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
                <div class="alert alert-error">
                    <span class="alert-icon">⚠</span>
                    <span><%= errorMessage %></span>
                </div>
            <% 
                } else if (error != null) {
                    if ("invalid".equals(error)) {
            %>
                <div class="alert alert-error">
                    <span class="alert-icon">⚠</span>
                    <span>Invalid username or password!</span>
                </div>
            <% 
                    } else if ("required".equals(error)) {
            %>
                <div class="alert alert-error">
                    <span class="alert-icon">⚠</span>
                    <span>Username and password are required!</span>
                </div>
            <% 
                    }
                }
                
                if (successMessage != null && !successMessage.isEmpty()) {
            %>
                <div class="alert alert-success">
                    <span class="alert-icon">✓</span>
                    <span><%= successMessage %></span>
                </div>
            <% 
                } else if ("logout".equals(success)) {
            %>
                <div class="alert alert-success">
                    <span class="alert-icon">✓</span>
                    <span>You have been successfully logged out.</span>
                </div>
            <% 
                }
            %>

            <!-- Unified Login Form -->
            <form id="loginForm" action="<%= request.getContextPath() %>/login" method="post">
                <input type="hidden" name="action" value="login">
                
                <div class="form-group">
                    <label for="inUsername">Username</label>
                    <input type="text" id="inUsername" name="username" placeholder="Enter your username" required autofocus>
                    <div class="help-text">Enter your username (customer or staff)</div>
                </div>
                
                <div class="form-group">
                    <label for="inPassword">Password</label>
                    <input type="password" id="inPassword" name="password" placeholder="Enter your password" required>
                </div>
                
                                <button type="submit" id="btnLogin" class="btn btn-primary">Login</button>
                
                <div class="link-text">
                    Don't have an account? <a id="btnRegister" href="<%= request.getContextPath() %>/views/Customer/MemberRegistrationView.jsp">Register as Customer</a>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Auto-hide success/error messages after 5 seconds
        window.addEventListener('DOMContentLoaded', function() {
            const alerts = document.querySelectorAll('.alert');
            if (alerts.length > 0) {
                setTimeout(function() {
                    alerts.forEach(function(alert) {
                        alert.style.transition = 'opacity 0.5s';
                        alert.style.opacity = '0';
                        setTimeout(function() {
                            alert.style.display = 'none';
                        }, 500);
                    });
                }, 5000);
            }
        });

        // Form validation
        document.getElementById('loginForm').addEventListener('submit', function(e) {
            const username = document.getElementById('inUsername').value.trim();
            const password = document.getElementById('inPassword').value.trim();
            
            if (!username || !password) {
                e.preventDefault();
                alert('Please enter both username and password.');
                return false;
            }
        });
    </script>
</body>
</html>
