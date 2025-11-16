package com.supermarket.controller;

import com.supermarket.dao.CustomerDAO;
import com.supermarket.model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("register".equals(action)) {
            handleRegistration(request, response);
        } else if ("login".equals(action)) {
            request.getRequestDispatcher("/login").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/views/Member/LoginView.jsp");
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String dob = request.getParameter("dob");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (fullname == null || email == null || phone == null || 
            username == null || password == null || confirmPassword == null ||
            fullname.trim().isEmpty() || email.trim().isEmpty() || 
            username.trim().isEmpty() || password.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "All required fields must be filled!");
            request.getRequestDispatcher("/views/Customer/MemberRegistrationView.jsp").forward(request, response);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match!");
            request.getRequestDispatcher("/views/Customer/MemberRegistrationView.jsp").forward(request, response);
            return;
        }
        
        if (customerDAO.isUsernameExists(username)) {
            request.setAttribute("errorMessage", "Username already exists!");
            request.getRequestDispatcher("/views/Customer/MemberRegistrationView.jsp").forward(request, response);
            return;
        }
        
        if (customerDAO.isEmailExists(email)) {
            request.setAttribute("errorMessage", "Email already registered!");
            request.getRequestDispatcher("/views/Customer/MemberRegistrationView.jsp").forward(request, response);
            return;
        }

        java.sql.Date sqlDob = null;
        if (dob != null && !dob.trim().isEmpty()) {
            try {
                sqlDob = java.sql.Date.valueOf(dob);
            } catch (IllegalArgumentException e) {
                request.setAttribute("errorMessage", "Invalid date format!");
                request.getRequestDispatcher("/views/Customer/MemberRegistrationView.jsp").forward(request, response);
                return;
            }
        }
        
        Customer customer = new Customer(username, password, fullname, phone, email, address, sqlDob);

        boolean isCreated = customerDAO.createCustomer(customer);
        
        if (isCreated) {
            request.setAttribute("successMessage", "Registration successful! Please login.");
            request.getRequestDispatcher("/views/Member/LoginView.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("/views/Customer/MemberRegistrationView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/views/Member/LoginView.jsp?success=logout");
        } else {
            response.sendRedirect(request.getContextPath() + "/views/Member/LoginView.jsp");
        }
    }
}
