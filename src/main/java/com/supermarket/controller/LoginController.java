package com.supermarket.controller;

import com.supermarket.dao.CustomerDAO;
import com.supermarket.dao.StaffDAO;
import com.supermarket.model.Customer;
import com.supermarket.model.Staff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;
    private StaffDAO staffDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        customerDAO = new CustomerDAO();
        staffDAO = new StaffDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("login".equals(action)) {
            handleLogin(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/views/Member/LoginView.jsp");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username == null || password == null || 
            username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and password are required!");
            request.getRequestDispatcher("/views/Member/LoginView.jsp").forward(request, response);
            return;
        }
        
        Staff staff = staffDAO.findByUsernameAndPassword(username, password);
        
        if (staff != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", staff.getMember());
            session.setAttribute("loggedInStaff", staff.getMember());
            session.setAttribute("userType", "staff");
            session.setAttribute("staffRole", staff.getRole());
            session.setMaxInactiveInterval(30 * 60); // 30 minutes
            
            if ("Management".equalsIgnoreCase(staff.getRole())) {
                response.sendRedirect(request.getContextPath() + "/views/ManagementStaff/ManagementStaffHomeView.jsp");
            } else if ("Sales".equalsIgnoreCase(staff.getRole())) {
                response.sendRedirect(request.getContextPath() + "/views/SalesStaffDashboard.jsp");
            } else if ("Delivery".equalsIgnoreCase(staff.getRole())) {
                response.sendRedirect(request.getContextPath() + "/views/DeliveryStaffDashboard.jsp");
            } else if ("Warehouse".equalsIgnoreCase(staff.getRole())) {
                response.sendRedirect(request.getContextPath() + "/views/WarehouseStaffDashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/StaffDashboard.jsp");
            }
            return;
        }
        
        Customer customer = customerDAO.findByUsernameAndPassword(username, password);
        
        if (customer != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", customer.getMember());
            session.setAttribute("customer", customer);
            session.setAttribute("userType", "customer");
            session.setAttribute("customerId", customer.getId());
            session.setAttribute("customerName", customer.getFullname());
            session.setMaxInactiveInterval(30 * 60); // 30 minutes
            
            response.sendRedirect(request.getContextPath() + "/views/Customer/Dashboard.jsp");
            return;
        }
        
        request.setAttribute("errorMessage", "Invalid username or password!");
        request.getRequestDispatcher("/views/Member/LoginView.jsp").forward(request, response);
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
