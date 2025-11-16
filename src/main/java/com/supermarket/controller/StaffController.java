package com.supermarket.controller;

import com.supermarket.dao.StaffDAO;
import com.supermarket.model.Staff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/StaffController")
public class StaffController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffDAO staffDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        staffDAO = new StaffDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("login".equals(action)) {
            request.getRequestDispatcher("/login").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/views/Member/LoginView.jsp");
        }
    }
    
    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/login").forward(request, response);
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
