<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Member Registration - Supermarket Management</title>
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
        input[type="email"],
        input[type="password"],
        input[type="tel"] {
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

        .password-strength {
            height: 4px;
            background: #e0e0e0;
            border-radius: 2px;
            margin-top: 8px;
            overflow: hidden;
        }

        .password-strength-bar {
            height: 100%;
            width: 0%;
            transition: all 0.3s;
        }

        .password-strength-bar.weak {
            width: 33%;
            background: #f44336;
        }

        .password-strength-bar.medium {
            width: 66%;
            background: #ff9800;
        }

        .password-strength-bar.strong {
            width: 100%;
            background: #ffb6c1;
        }

        .alert {
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <!-- Step 3: Member Registration View displays itself -->
    <div class="container">
        <div class="header">
            <h1>Create Account</h1>
            <p>Join us today</p>
        </div>
        <div class="content">
            <!-- Step 14: Display error message if registration fails -->
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
                <div class="alert alert-error">
                    <%= errorMessage %>
                </div>
            <% } %>
            
            <!-- Step 4-5: Customer enters information and submits to CustomerController -->
            <form action="<%= request.getContextPath() %>/CustomerController" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="register">
                
                <div class="form-group">
                    <label for="inFullname">Full Name *</label>
                    <input type="text" id="inFullname" name="fullname" placeholder="Enter your full name" required>
                </div>
                <div class="form-group">
                    <label for="inEmail">Email Address *</label>
                    <input type="email" id="inEmail" name="email" placeholder="Enter your email" required>
                </div>
                <div class="form-group">
                    <label for="inPhone">Phone Number *</label>
                    <input type="tel" id="inPhone" name="phone" placeholder="Enter your phone number" maxlength="10" required>
                </div>
                <div class="form-group">
                    <label for="inAddress">Address</label>
                    <input type="text" id="inAddress" name="address" placeholder="Enter your address">
                </div>
                <div class="form-group">
                    <label for="inDoB">Date of Birth</label>
                    <input type="date" id="inDoB" name="dob">
                </div>
                <div class="form-group">
                    <label for="inUsername">Username *</label>
                    <input type="text" id="inUsername" name="username" placeholder="Choose a username" required>
                </div>
                <div class="form-group">
                    <label for="inPassword">Password *</label>
                    <input type="password" id="inPassword" name="password" placeholder="Create a password" required>
                    <div class="password-strength">
                        <div class="password-strength-bar" id="strengthBar"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password *</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm your password" required>
                </div>
                <button type="submit" id="btnSubmit" class="btn btn-primary">Register</button>
            </form>
            
            <form action="<%= request.getContextPath() %>/views/Member/LoginView.jsp" method="get">
                <button type="submit" class="btn btn-secondary">Back to Login</button>
            </form>
        </div>
    </div>

    <script>
        const passwordInput = document.getElementById('inPassword');
        const strengthBar = document.getElementById('strengthBar');

        // Password strength indicator
        passwordInput.addEventListener('input', (e) => {
            const password = e.target.value;
            const strength = calculatePasswordStrength(password);
            
            strengthBar.className = 'password-strength-bar';
            if (strength > 0) {
                if (strength < 3) {
                    strengthBar.classList.add('weak');
                } else if (strength < 5) {
                    strengthBar.classList.add('medium');
                } else {
                    strengthBar.classList.add('strong');
                }
            }
        });

        function calculatePasswordStrength(password) {
            let strength = 0;
            if (password.length >= 8) strength++;
            if (password.length >= 12) strength++;
            if (/[a-z]/.test(password) && /[A-Z]/.test(password)) strength++;
            if (/\d/.test(password)) strength++;
            if (/[^a-zA-Z\d]/.test(password)) strength++;
            return strength;
        }

        // Client-side validation
        function validateForm() {
            const password = document.getElementById('inPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (password !== confirmPassword) {
                alert('Passwords do not match!');
                return false;
            }

            if (password.length < 6) {
                alert('Password must be at least 6 characters long!');
                return false;
            }

            return true;
        }
    </script>
</body>
</html>
